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
          agent { label 'agent1' } // Specify the agent1 for this stage
             environment {
               SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
               withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_AUTH_TOKEN')]) {
               sh 'mvn sonar:sonar -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.host.url=${SONAR_URL}'
        }
        }
        }
        stage('Quality Gate') {
            steps {
                // Wait for the Quality Gate result from SonarQube
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
