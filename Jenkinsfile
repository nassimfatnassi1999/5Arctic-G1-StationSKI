pipeline {
    agent any

    environment {
        SONARQUBE_ENV = 'SonarQube'
        SONAR_TOKEN = credentials('sonarToken')
        DOCKERHUB_CREDENTIALS = credentials('docker-hub')
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
                        sh 'ls -l target/'
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

        stage("Publish to Nexus Repository Manager") {
            agent { label 'agent1' }
            steps {
                script {
                    echo "Deploying to Nexus..."

                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.33.11:8081", // Updated Nexus URL based on previous info
                        groupId: 'tn.esprit.spring',
                        artifactId: 'gestion-station-ski',
                        version: '1.0',
                        repository: "5Arctic4-G1-StationSKI", // Based on previous Nexus repo
                        credentialsId: "NEXUS", // Using your stored Nexus credentials
                        artifacts: [
                            [
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

    post {
        success {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} !")
            }
        }
        failure {
            script {
                slackSend(channel: '#jenkins-achref',
                          message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }
}
