pipeline {
    agent { label 'agent1' }
    environment {
        SONARQUBE_ENV = 'sonarqube'
        DOCKER_IMAGE = 'backend-g1-stationski'
        SONAR_TOKEN = credentials('sonar_token')
        IMAGE_TAG = 'latest'
    }

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('Checkout') {
            steps {
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git',
                    branch: 'HamdiAlaaEddin-G1-stationSKI'
                )
            }
        }

        stage('Clean and Install') {
            steps {
                sh 'mvn clean package | tee build.log'
            }
        }

        stage('Verify JAR File') {
            steps {
                script {
                    def jarFile = "${env.WORKSPACE}/target/5Arctic-G1-StationSKI.jar"
                    if (!fileExists(jarFile)) {
                        error "JAR file not found: ${jarFile}"
                    } else {
                        echo "JAR file found: ${jarFile}"
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
                            -Dsonar.inclusions=src/main/java/tn/esprit/spring/services/** \
                            -Dsonar.test.inclusions=src/test/java/tn/esprit/spring/services/** \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        """
                    }
                }
            }
        }
        stage('Upload to Nexus') {
                    steps {
                        script {
                            echo "Deploying to Nexus..."

                            nexusArtifactUploader(
                                nexusVersion: 'nexus3',
                                protocol: 'http',
                                nexusUrl: "192.168.33.11:9001",
                                groupId: 'tn.esprit.spring',
                                version: '1.0',
                                repository: "maven-releases",
                                credentialsId: "nexus_token",
                                artifacts: [
                                    [
                                        artifactId: 'gestion-station-ski', // Déplacez-le ici
                                        file: "${env.WORKSPACE}/target/5Arctic-G1-StationSKI.jar", // Chemin dynamique
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
