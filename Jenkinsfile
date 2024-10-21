pipeline {
    agent any

    environment {
        SONARQUBE_ENV = 'SonarQube'
        SONAR_TOKEN = credentials('sonarToken')
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: 'SamaaliMedAchref-G1-StationSKI',
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git'
            }
        }

        stage('Compile') {
            steps {
                script {
                    sh 'mvn clean compile '
                    //sh 'mvn test'
                    //sh 'mvn package'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco/jacoco.xml
                        """
                    }
                }
            }
        }
        stage('Unit Tests') {
                    steps {
                        script {
                            sh 'mvn clean package '
                            //sh 'mvn test'
                            //sh 'mvn package'
                        }
                    }
                }

        stage('Publish to Nexus Repository Manager') {
            agent { label 'agent1' }
            steps {
                script {
                    echo "Deploying to Nexus..."

                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.33.11:8081", // Updated Nexus URL based on previous info
                        groupId: 'tn.esprit.spring',
                        artifactId: 'gestion-station-ski',
                        version: '1.0',
                        repository: "maven-releases", // Based on previous Nexus repo
                        credentialsId: "NEXUS", // Using your stored Nexus credentials
                        artifacts: [
                            [
                                artifactId: 'gestion-station-ski',
                                classifier: '',
                                file: '/home/vagrant/workspace/5Arctic-G1-bakend/target/5Arctic-G1-StationSKI.jar', // Relative path from workspace
                                type: 'jar'
                            ]
                        ]
                    )

                    echo "Deployment to Nexus completed!"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dir('factures') {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            sh 'docker build -t arctic-g1-stationski:latest /home/vagrant/workspace/5Arctic-G1-bakend/' // Assumes Dockerfile is present
                        }
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Login to Docker Hub
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'

                        // Tag the Docker image
                        sh 'docker tag arctic-g1-stationski:latest $DOCKER_USERNAME/arctic-g1-stationski:latest'

                        // Push the Docker image
                        sh 'docker push $DOCKER_USERNAME/arctic-g1-stationski:latest'
                    }
                }
            }
        }
    }
    stage('Deploy to AKS') {
        steps {
            script {
                // Vérifie si le cluster AKS est accessible
                def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                if (clusterExists) {
                    echo "Le cluster AKS existe et est accessible."
                    // Déployer l'application
                    sh 'kubectl apply -f deploy.yml'
                } else {
                    echo "Le cluster AKS n'existe pas. Création du cluster avec Terraform."
                    // Créer le cluster AKS avec Terraform
                    sh '''

                        cd /home/vagrant/cluster
                        terraform init
                        terraform apply -auto-approve
                    '''
                    // Attendre quelques instants pour que le cluster soit prêt
                    sleep 60
                    //acceder au cluster
                    sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'
                    // Déployer l'application après la création du cluster
                    sh '''
                         kubectl apply -f deploy.yml
                    '''
                }
            }
        }
    }



    post {
        success {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} !")
            }
        }
        failure {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }
}
