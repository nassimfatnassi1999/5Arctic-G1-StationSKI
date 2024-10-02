pipeline {
    //agent { label 'agent1' }
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
        stage('Clean Install') {
            steps {
                // Clean and install dependencies
                sh 'mvn clean install'
            }
        }
        stage('Compile') {
            steps {
                // Compile the code
                sh 'mvn compile'
            }
        }
    }
}
