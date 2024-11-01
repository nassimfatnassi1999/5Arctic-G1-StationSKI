pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = 'nassimfatnassi-g1-stationski'  
        IMAGE_TAG = '1.1'  
    }
    stages {
        stage('Checkout') {
            steps {
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git', 
                    branch: 'nassimFatnassi-G1-SKI',
                    credentialsId: 'github-credentials'
                )
            }
        }

        stage('Clean, Build & Test') {
            agent { label 'agent1' }
            steps {
                sh '''
                    mvn clean install
                    mvn jacoco:report
                '''
            }
        }
        
        stage('Static Analysis SonarCloud') {
            agent { label 'agent1' }
            environment {
                SONAR_URL = "https://sonarcloud.io" // URL de SonarCloud
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-cloud-credentials', variable: 'SONAR_TOKEN')]) {
                    sh '''
                         mvn sonar:sonar \
                        -Dsonar.login=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.projectKey=5Arctic-G1-StationSKI \
                        -Dsonar.organization=nassimfatnassi1999 \
                        -Dsonar.inclusions=/src/main/java/tn/esprit/spring/services/PisteServicesImpl.java,/src/main/java/tn/esprit/spring/services/SubscriptionServicesImpl.java \
                        -Dsonar.test.inclusions=/src/test/java/tn/esprit/spring/services/PisteServicesImplTest.java,/src/main/java/tn/esprit/spring/services/SubscriptionServicesImplTest.java
                    '''
                }
            }
        }

        stage('Upload to Nexus') {
            agent { label 'agent1' }
            steps {
                script {
                    echo "Deploying to Nexus..."
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.33.11:9001",
                        groupId: 'tn.esprit.spring',
                        artifactId: '5Arctic-G1-StationSKI',
                        version: '1.1',
                        repository: "maven-central-repository",
                        credentialsId: "nexus-credentials",
                        artifacts: [
                            [
                                artifactId: '5Arctic-G1-StationSKI',
                                classifier: '',
                                file: 'target/5Arctic-G1-StationSKI.jar', 
                                type: 'jar'
                            ]
                        ]
                    )
                    echo "Deployment to Nexus completed!"
                }
            }
        }

        stage('Build Docker Image') {
            agent { label 'agent1' }
            steps {
                script {
                    def nexusUrl = "http://192.168.33.11:9001"
                    def groupId = "tn.esprit.spring"
                    def artifactId = "5Arctic-G1-StationSKI"
                    def version = "1.1"

                    sh """
                        docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} \
                        --build-arg NEXUS_URL=${nexusUrl} \
                        --build-arg GROUP_ID=${groupId} \
                        --build-arg ARTIFACT_ID=${artifactId} \
                        --build-arg VERSION=${version} .
                    """
                }
            }
        }

        stage('Scan with Trivy') {
             agent { label 'agent1' }
            steps {
                script {
                    // Lancer le scan Trivy et générer le rapport JSON
                    sh 'trivy image --scanners vuln --timeout 3600s -f json -o trivy_report.json ${DOCKER_IMAGE}:${IMAGE_TAG}'
                    sh 'python3 /home/vagrant/json-to-html.py'
                    sh 'cp /home/vagrant/trivy_report.html ${WORKSPACE}/trivy_report.html'
                    slackUploadFile channel: '#jenkins-messg', filePath: 'trivy_report.html', initialComment: 'Rapport Trivy en HTML'
                }
            }
        }

        
         stage('Push Docker Image') {
            agent { label 'agent1' }
            environment {
                DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
            }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
                        sh "docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Deploy to AKS') {
            agent { label 'agent2' }
            steps {
                script {
                    def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                    if (clusterExists) {
                        echo "Cluster exists. Deploying the application."

                        // Deploy the application using manifest_files
                        sh '''
                            cd manifest_files
                            kubectl apply -f deploy_backend_mysql.yml                        '''
                    } else {
                        echo "Cluster does not exist. Creating with Terraform."
                        sh '''
                            cd /home/vagrant/myAks-cluster
                            terraform init
                            terraform apply -auto-approve
                        '''
                        sleep 60
                        sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'

                        // Deploy the application using manifest_files
                        sh ''' 
                            cd manifest_files
                            kubectl apply -f deploy_backend_mysql.yml
                        '''
                    }
                     sleep 70
                    // Get LoadBalancer IP of the backend service
                    def backendIP = sh(
                     script: "kubectl get svc backend-service | awk '/backend-service/ {print \$4}'",
                        returnStdout: true
                    ).trim()
                    env.BACKEND_IP = backendIP

                }
            }
        }
    }

  post {
    success {
        script {
            slackSend(
                channel: '#jenkins-messg', 
                message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} ! Image pushed: ${DOCKER_IMAGE}:${IMAGE_TAG} successfully. Backend IP: ${env.BACKEND_IP}"
            )
        }
    }
    failure {
        script {
            slackSend(
                channel: '#jenkins-messg', 
                message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}."
            )
        }
    }
}

}
