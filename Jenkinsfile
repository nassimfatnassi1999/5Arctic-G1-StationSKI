pipeline {
    agent { label 'agent1' }
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    
    stages {
        stage('Checkout') {
            steps {
                // Get code from the GitHub repository
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git',
                    branch: 'HamdiAlaaEddin-G1-stationSKI'
                )
            }
        }
 
        stage('Clean and Install') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Static Analysis') {
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=$SONAR_URL -Dsonar.java.binaries=target/classes"
                }
            }
        }

        stage('Upload to Nexus') {
    steps {
        script {
            echo "Deploying to Nexus..."

            nexusArtifactUploader(
                nexusVersion: 'nexus3',
                protocol: 'http',
                nexusUrl: "192.168.33.11:9001", // Updated Nexus URL based on previous info
                groupId: 'tn.esprit.spring',
                artifactId: 'gestion-station-ski', // Corrected artifactId (without .jar)
                version: '1.0',
                repository: "maven-central-repository", // Nexus repository
                credentialsId: "nexus-credentials", // Nexus credentials ID
                artifacts: [
                    [
                        artifactId: 'gestion-station-ski', // Corrected artifactId
                        file: 'target/5Arctic-G1-StationSKI.jar', // Path to your JAR file
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
                    // Define the Nexus download parameters
                    def nexusUrl = "http://192.168.33.11:9001" // Nexus server URL
                    def groupId = "tn.esprit.spring"
                    def artifactId = "5Arctic-G1-StationSKI"
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
    }
}
