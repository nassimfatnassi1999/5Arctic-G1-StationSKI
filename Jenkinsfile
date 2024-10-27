pipeline {
    agent { label 'agent1' }
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

        stage('Print Workspace Directory') {
            steps {
                sh 'pwd'
            }
        }

        stage('Clean and Install') {
            steps {
                sh 'mvn clean package | tee build.log'
            }
        }

        stage('Find JAR File') {
            steps {
                sh 'find . -name "*.jar"'
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

        stage('Static Analysis') {
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
   steps {
                script {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco/jacoco.xml
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
                        version: '1.0.2',
                        repository: "maven-releases",
                        credentialsId: "nexus-credentials",
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

        stage('Build Docker Image') {
            environment {
                DOCKER_IMAGE = 'gestion-station-ski'
                IMAGE_TAG = '1.0'
                NEXUS_URL = "http://192.168.33.11:9001"
                GROUP_ID = "tn.esprit.spring"
                ARTIFACT_ID = "gestion-station-ski"
                VERSION = "1.0"
            }
            steps {
                script {
                    // Vérifiez le contenu du répertoire de travail
                    sh 'ls -l'

                    // Construisez l'image Docker
                    sh """
                        docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} \
                        --build-arg NEXUS_URL=${NEXUS_URL} \
                        --build-arg GROUP_ID=${GROUP_ID} \
                        --build-arg ARTIFACT_ID=${ARTIFACT_ID} \
                        --build-arg VERSION=${VERSION} .
                    """
                }
            }
        }
    }
}
