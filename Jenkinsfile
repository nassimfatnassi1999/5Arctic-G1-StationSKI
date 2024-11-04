pipeline {
    agent { label 'agent1' }
    environment {
        SONARQUBE_ENV = 'sonarqube'
        DOCKER_IMAGE = 'hamdialaaeddin-g1-stationski'
        SONAR_TOKEN = credentials('sonar_token')
        IMAGE_TAG = 'latest'
        DOCKERHUB_CREDENTIALS = credentials('docker-token')
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
                        nexusUrl: "192.168.33.11:8081",
                        groupId: 'tn.esprit.spring',
                        version: '1.0',
                        repository: "maven-releases",
                        credentialsId: "nexus_token",
                        artifacts: [
                            [
                                artifactId: 'gestion-station-ski',
                                file: "${env.WORKSPACE}/target/5Arctic-G1-StationSKI.jar",
                                type: 'jar'
                            ]
                        ]
                    )

                    echo "Deployment to Nexus completed!"
                }
            }
        }

        stage('Build Docker Image') {
            agent { label 'master' }
            environment {
                NEXUS_URL = "http://192.168.33.11:8081"
                GROUP_ID = "tn.esprit.spring"
                ARTIFACT_ID = "gestion-station-ski"
                VERSION = "1.0"
            }
            steps {
                script {
                    // Build Docker image
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

        stage('Push Docker Image to Docker Hub') {
            agent { label 'master' }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker_token', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh 'docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
                        sh 'docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
                    }
                }
            }
        }
    }
}
