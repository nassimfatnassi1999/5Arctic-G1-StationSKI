pipeline {
    agent any
    environment {
        SONARQUBE_ENV = 'SonarQube'  // Replace with your SonarQube environment name configured in Jenkins
        SONAR_TOKEN = credentials('sonarToken')  // Use the ID of the credential storing your SonarQube token
        DOCKERHUB_CREDENTIALS = credentials('docker-hub') // Docker Hub credentials stored in Jenkins
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.33.11:8081"
        NEXUS_REPOSITORY = "5Arctic4-G1-StationSKI"
        NEXUS_CREDENTIAL_ID = "NEXUS"

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
    stage("Publish to Nexus Repository Manager") {
                agent { label 'agent1' }
                steps {
                    script {
                        pom = readMavenPom file: "pom.xml";
                        filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        artifactPath = filesByGlob[0].path;
                        artifactExists = fileExists artifactPath;
                        if(artifactExists) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: "pom.xml",
                                    type: "pom"]
                                ]
                            );
                        } else {
                            error "*** File: ${artifactPath}, could not be found";
                        }
                    }
                }
            }

    post {
            success {
                script {
                    // Send a success message to Slack
                    slackSend(channel: '#jenkins-achref',
                              message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} !")
                }
            }
            failure {
                script {
                    // Send a failure message to Slack
                    slackSend(channel: '#jenkins-achref',
                              message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
                }
            }
        }

}
