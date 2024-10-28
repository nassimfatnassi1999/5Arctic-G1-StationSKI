pipeline {
    agent any // Agent par défaut pour le pipeline

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the GitHub repository'
                // Récupérer le code depuis le dépôt GitHub
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git',
                    branch: 'HannachiNoursine_G1_StationSKI'
                )
            }
        }

        stage('Clean and Compile') {
            agent { label 'agent_1' } // Utiliser agent_1 pour cette étape
            steps {
                echo 'Building the project with Maven'
                // Exécuter Maven pour nettoyer et compiler
                sh 'mvn clean install'
            }
        }

        stage('Generate JaCoCo Report') {
            agent { label 'agent_1' }
            steps {
                echo 'Generating JaCoCo report'
                // Exécuter Maven pour générer le rapport JaCoCo
                sh 'mvn jacoco:report'
            }
        }

        stage('Code Quality with SonarQube') {
            agent { label 'agent_1' }
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    echo 'Running SonarQube analysis'
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.token=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Deploy to Nexus') {
            agent { label 'agent_1' }
            steps {
                echo 'Deploying to Nexus'
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh 'mvn deploy -DskipTests=true -Dnexus.username=$NEXUS_USER -Dnexus.password=$NEXUS_PASS'
                }
            }
        }

        stage('Build Docker Image') {
            agent { label 'agent_1' }
            steps {
                echo 'Building Docker Image'
                script {
                    sh 'docker build -t arctic-g1-stationski:latest .'
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            agent { label 'agent_1' }
            steps {
                echo 'Pushing Docker Image to Docker Hub'
                script {
                    withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh 'docker tag arctic-g1-stationski:latest $DOCKER_USERNAME/arctic-g1-stationski:latest'
                        sh 'docker push $DOCKER_USERNAME/arctic-g1-stationski:latest'
                    }
                }
            }
        }

        stage('Deploy to AKS') {
            agent { label 'agent_1' }
            steps {
                echo 'Deploying to AKS'
                script {
                    def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                    if (clusterExists) {
                        echo "The AKS cluster exists and is accessible."
                        sh 'kubectl apply -f deploy_backandDB.yml'
                    } else {
                        echo "The AKS cluster does not exist. Creating the cluster with Terraform."
                        sh '''
                            cd /home/vagrant/cluster
                            terraform init
                            terraform apply -auto-approve
                        '''
                        sleep 60
                        sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'
                        sh 'kubectl apply -f deploy_backandDB.yml'
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                slackSend(
                    channel: '#jenkins_noursine',
                    message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} ! Image pushed: arctic-g1-stationski:latest successfully."
                )
            }
        }
        failure {
            script {
                slackSend(
                    channel: '#jenkins_noursine',
                    message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}."
                )
            }
        }
    }
}
