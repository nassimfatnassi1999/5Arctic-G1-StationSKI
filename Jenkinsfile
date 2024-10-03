pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        // SonarQube environment (replace with your SonarQube server name)
        SONARQUBE_ENV = 'sonarqube'
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

        stage('Clean package') {
            steps {
                // Clean and package dependencies
                sh 'mvn clean package'
            }
        }

        stage('Compile') {
            steps {
                // Compile the code
                sh 'mvn compile'
            }
        }

        stage('Static Analysis') {
          agent { label 'agent1' } // Specify the agent1 for this stage
            steps {
                // Perform SonarQube analysis
                withSonarQubeEnv('sonarqube') { // Make sure the name matches your SonarQube configuration
                    sh 'mvn sonar:sonar'
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
