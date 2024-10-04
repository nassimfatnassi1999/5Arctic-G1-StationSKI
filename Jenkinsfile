pipeline {
    agent any
    environment {
        SONARQUBE_ENV = 'SonarQube'  // Replace with your SonarQube environment name configured in Jenkins
        SONAR_TOKEN = credentials('sonarToken')  // Use the ID of the credential storing your SonarQube token
        DOCKERHUB_CREDENTIALS = credentials('docker-hub') // Docker Hub credentials stored in Jenkins
    }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: 'SamaaliMedAchref-G1-StationSKI',
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git'
            }
        }

        stage('Compile') {
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh 'mvn clean test'
                        sh 'mvn install'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.coverage.jacoco.xmlReportPaths=factures/target/site/jacoco/jacoco.xml
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
