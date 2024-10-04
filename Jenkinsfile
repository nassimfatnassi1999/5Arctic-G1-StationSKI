pipeline {
    agent any
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

}

    }

    post {
        success {
            script {
                // Envoyer un message à Slack
                slackSend(channel: '#jenkins-messg', 
                          message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} !")
            }
        }
        failure {
            script {
                // Envoyer un message à Slack en cas d'échec
                slackSend(channel: '#jenkins-messg', 
                          message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }
}
