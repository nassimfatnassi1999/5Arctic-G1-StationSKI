pipeline {
    agent { label 'agent1' }
    environment {
        SONARQUBE_ENV = 'sonarqube'
        SONAR_TOKEN = credentials('sonar_token')
        DOCKERHUB_CREDENTIALS = credentials('docker_token')
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
                sh 'mvn clean install '
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
                        version: '1.1',
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
            steps {
                script {
                    def nexusUrl = "http://192.168.33.11:9001"
                    def groupId = "tn.esprit.spring"
                    def artifactId = "5Arctic-G1-StationSKI"
                    def version = "1.1"

                    sh """
                        docker build -t hamdialaaeddin-5arctic4-g1-stationski:3.2 } \
                        --build-arg NEXUS_URL=${nexusUrl} \
                        --build-arg GROUP_ID=${groupId} \
                        --build-arg ARTIFACT_ID=${artifactId} \
                        --build-arg VERSION=${version} .
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
                               sh 'docker tag hamdialaaeddin-5arctic4-g1-stationski:3.2 $DOCKER_USERNAME/hamdialaaeddin-5arctic4-g1-stationski:3.2'
                               sh 'docker push $DOCKER_USERNAME/hamdialaaeddin-5arctic4-g1-stationski:3.2'
                           }
                       }
                   }
               }
               stage('Deploy to AKS') {
                                           agent { label 'agent1' }
                                           steps {
                                               script {
                                                   def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                                                   if (clusterExists) {
                                                       echo "The AKS cluster exists and is accessible."
                                                       sh 'kubectl apply -f deployment.yaml'
                                                   } else {
                                                       echo "The AKS cluster does not exist. Creating the cluster with Terraform."
                                                       sh '''

                                                            cd /home/vagrant/mycluster
                                                            terraform init
                                                            terraform apply -auto-approve
                                                       '''
                                                       sleep 30
                                                       sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'
                                                       sh 'kubectl apply -f deployment.yaml'
                                                   }
                                               }
                                           }
                                       }
    }
}
