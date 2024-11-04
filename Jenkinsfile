pipeline {
    agent { label 'agent1' }
    environment {
        SONARQUBE_ENV = 'sonarqube'
        DOCKER_IMAGE = 'backend-g1-stationski'
        IMAGE_TAG = 'latest'
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub_credentials')


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

       stage('Static Analysis SonarCloud') {
                  agent { label 'agent1' }
                  environment {
                      SONAR_URL = "https://sonarcloud.io" // URL de SonarCloud
                  }
                  steps {
                      withCredentials([string(credentialsId: 'sonar-cloud-credentials', variable: 'SONAR_TOKEN')]) {
                          sh '''
                               mvn sonar:sonar \
                              -Dsonar.login=${SONAR_TOKEN} \
                              -Dsonar.host.url=${SONAR_URL} \
                              -Dsonar.java.binaries=target/classes \
                              -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                              -Dsonar.projectKey=5Arctic-G1-StationSKI \
                              -Dsonar.organization=hamdialaeddin \
                          '''
                      }
                  }
              }
      }