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
                    branch: 'HannachiNoursine_G1_StationSKI'
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
                SONAR_URL = "http://192.168.33.10:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=$SONAR_URL -Dsonar.java.binaries=target/classes"
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                // Using Nexus credentials
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh 'mvn deploy -DskipTests=true -Dnexus.username=$NEXUS_USER -Dnexus.password=$NEXUS_PASS'
                }
            }
        }
    }
}