pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = 'nassimfatnassi-g1-stationski'  // Dynamic Docker image name
        IMAGE_TAG = 'latest'  // Image tag (e.g., 'latest' or version)
    }
    stages {
        stage('Checkout') {
            steps {
                // Get code from the GitHub repository
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git', 
                    branch: 'nassimFatnassi-G1-SKI',
                    credentialsId: 'github-credentials'
                )
            }
        }

        stage('Build') {
            agent { label 'agent1' } 
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Run Tests Junit & Mockito') {
            agent { label 'agent1' }
             steps {
               // Exécution des tests unitaires
                sh 'mvn clean test'
              }
         }
        stage('Generate JaCoCo Report') {
            agent { label 'agent1' }
            steps {
            // Génération du rapport JaCoCo après les tests
            sh 'mvn jacoco:report'
            }
        }
            stage('Static Analysis') {
                agent { label 'agent1' }
                environment {
                    SONAR_URL = "http://192.168.33.11:9000/"
                }
                steps {
                    withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh '''
                         mvn sonar:sonar \
                        -Dsonar.login=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }/*
        stage('Upload to Nexus') {
            agent { label 'agent1' } // Use agent1 for the Nexus upload
            steps {
                script {
                    echo "Deploying to Nexus..."

                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.33.11:9001", // Updated Nexus URL based on previous info
                        groupId: 'tn.esprit.spring',
                        artifactId: 'gestion-station-ski',
                        version: '1.0',
                        repository: "maven-central-repository", // Based on previous Nexus repo
                        credentialsId: "nexus-credentials", // Using your stored Nexus credentials
                        artifacts: [
                            [
                                artifactId: 'gestion-station-ski',
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
                    // Define the Nexus download parameters
                    def nexusUrl = "http://192.168.33.11:9001" // Nexus server URL
                    def groupId = "tn.esprit.spring"
                    def artifactId = "gestion-station-ski"
                    def version = "1.0"

                    // Build the Docker image, passing Nexus parameters as build arguments
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
        stage('Trivy Scan') {
            agent { label 'agent1' } 
            steps {
                script {
                    // Commande pour lancer le scan avec Trivy
                    sh 'trivy image --scanners vuln --timeout 600s ${DOCKER_IMAGE}:${IMAGE_TAG}'
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
        }*/
        
        stage('Azure Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'azure-credentials', usernameVariable: 'AZURE_USERNAME', passwordVariable: 'AZURE_PASSWORD')]) {
                        sh '''
                            az login -u $AZURE_USERNAME -p $AZURE_PASSWORD
                        '''
                    }
                }
            }
        }
       // stage terraform command for create cluster on AZURE
       stage('Terraform Apply - Create AKS Cluster') {
            agent { label 'master' }
            steps {
                script {
                    // Vérifie si le cluster existe déjà
                    def clusterExists = sh(
                        script: "az aks show --resource-group myResourceGroup --name myAKSCluster",
                        returnStatus: true
                    ) == 0 // Si le code de sortie est 0, le cluster existe

                    if (clusterExists) {
                        echo "Le cluster AKS existe déjà, étape Terraform sautée."
                    } else {
                        echo "Le cluster AKS n'existe pas, lancement de Terraform."
                        dir('/home/vagrant/clusterAKS') {
                            // Initialisation de Terraform
                            sh 'terraform init'

                            // Exécuter Terraform Plan
                            sh 'terraform plan -out=tfplan'

                            // Appliquer le plan Terraform pour créer le cluster
                            sh 'terraform apply -auto-approve tfplan'
                        }
                    }
                }
            }
        }
   
    
    
    }
    post {
        success {
            script {
                // Send a success message to Slack with image name and tag
                slackSend(channel: '#jenkins-messg', 
                          message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} ! Image pushed: ${DOCKER_IMAGE}:${IMAGE_TAG} successfully.")
            }
        }
        failure {
            script {
                // Send a failure message to Slack
                slackSend(channel: '#jenkins-messg', 
                          message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }
}
