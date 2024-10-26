pipeline {
    agent any

    environment {
        SONARQUBE_ENV = 'SonarQube'
        SONAR_TOKEN = credentials('sonarToken')
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    }

    stages {
        stage('Checkout GIT') {
            agent { label 'master' }
            steps {
                echo 'Pulling from Git...'
                git branch: 'SamaaliMedAchref-G1-StationSKI',
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git'
            }
        }

        stage('clean build && Unit Tests ') {
            agent { label 'master' }
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            agent { label 'master' }
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


         stage('Deploy to Nexus') {
                    agent { label 'agent1' } // Utiliser agent1 pour cette étape
                    steps {
                        echo 'Deploying to Nexus'
                        // Using Nexus credentials
                        withCredentials([usernamePassword(credentialsId: 'NEXUS', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                            sh 'mvn deploy -DskipTests=true -Dnexus.username=$NEXUS_USER -Dnexus.password=$NEXUS_PASS'
                        }
                    }
                }

        stage('Build Docker Image') {
            agent { label 'agent1' }
            steps {
                script {
                    sh 'docker build -t arctic-g1-stationski:latest /home/vagrant/workspace/5Arctic-G1-bakend/'
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            agent { label 'agent1' }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh 'docker tag arctic-g1-stationski:latest $DOCKER_USERNAME/arctic-g1-stationski:latest'
                        sh 'docker push $DOCKER_USERNAME/arctic-g1-stationski:latest'
                    }
                }
            }
        }
        stage('Trivy Security Scan') {
                    agent { label 'agent1' }
                    steps {
                        script {
                            // Run Trivy scan
                            sh "trivy mohammedachref/arctic-g1-stationski:latest >trivyimage.text"
                        }
                    }
                }

        stage('Deploy to AKS') {
            agent { label 'agent1' }
            steps {
                script {
                    def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                    if (clusterExists) {
                        echo "The AKS cluster exists and is accessible."
                        sh 'kubectl apply -f deploy.yml'
                    } else {
                        echo "The AKS cluster does not exist. Creating the cluster with Terraform."
                        sh '''
                            cd /home/vagrant/cluster
                             terraform init
                             terraform apply -auto-approve
                        '''
                        sleep 60
                        sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'
                        sh 'kubectl apply -f deploy.yml'
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Build succeeded: ${env.JOB_NAME} #${env.BUILD_NUMBER}!")
            }
        }
        failure {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }
}
