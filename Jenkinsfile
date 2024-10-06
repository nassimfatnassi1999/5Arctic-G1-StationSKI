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
                    branch: 'nassimFatnassi-G1-SKI',
                    credentialsId: 'github-credentials'  // Add GitHub credentials ID
                )
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Static Analysis') {
            agent { label 'agent1' } // Specify the agent for this stage
            environment {
                SONAR_URL = "http://192.168.43.11:9000/"
            }
             steps {
                dir('5Arctic4-G1-StationSki-Backend') {
                sh 'ls -l' // Listez le contenu du répertoire
                sh 'mvn sonar:sonar -Dsonar.login=**** -Dsonar.host.url=$SONAR_URL -Dsonar.java.binaries=target/classes'
        }
    }
        }
    }
}
