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
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Static Analysis') {
            agent { label 'agent1' } // Specify the agent for this stage
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
                // Use withCredentials to inject the SonarQube token
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=${SONAR_URL} -Dsonar.java.binaries=target/classes'
                }
            }
        }

        stage('Generate Code Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

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

        stage('Build and Push Docker Image') {
            agent { label 'agent2' } // Run this stage on agent2
            environment {
                DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
            }
            steps {
                script {
                    // Build the Docker image using the environment variable
                    sh "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
                    
                    // Use the Docker Hub credentials to log in and push the image
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Login to Docker Hub
                        sh script: 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin', returnStdout: true
                        
                        // Tag the Docker image
                        sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
                        
                        // Push the Docker image to Docker Hub
                        sh "docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}"
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
