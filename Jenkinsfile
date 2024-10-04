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
    }
    stage('Upload to Nexus') {
            agent { label 'agent1' } // Use agent1 for the Nexus upload
            environment {
                NEXUS_URL = 'http://192.168.33.11:9001/repository' // Nexus repository base URL
                NEXUS_REPOSITORY = 'maven-central-repository' // Repository ID in Nexus
                NEXUS_CREDENTIALS = credentials('nexus-credentials') // Jenkins Nexus credentials
                ARTIFACT_VERSION = '1.0' // Define your artifact version
                GROUP_ID = 'tn.esprit.spring' // Your groupId
                ARTIFACT_ID = 'gestion-station-ski' // Your artifactId
            }
            steps {
                script {
                    def artifactPath = "target/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar"
                    
                    // Use Nexus REST API to upload artifact
                    sh """
                    curl -v -u ${NEXUS_CREDENTIALS_USR}:${NEXUS_CREDENTIALS_PSW} \
                    --upload-file ${artifactPath} \
                    ${NEXUS_URL}/repository/${NEXUS_REPOSITORY}/${GROUP_ID.replace('.','/')}/${ARTIFACT_ID}/${ARTIFACT_VERSION}/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar
                    """
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
