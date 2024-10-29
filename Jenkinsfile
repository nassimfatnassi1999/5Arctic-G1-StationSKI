pipeline {
    agent { label 'agent1' }
    environment {
        SONARQUBE_ENV = 'sonarqube'
        SONAR_TOKEN = credentials('sonar-credentials')
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

       stage('SonarQube Analysis') {
            agent { label 'agent1' }
            steps {
                script {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.coverage.jacoco.xmlReportPaths=/home/vagrant/jenkins-agent1/workspace/5arctic4-G1-StationSki-Backend/target/site/jacoco/jacoco.xml
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

                NEXUS_URL = "http://192.168.33.11:9001"
                GROUP_ID = "tn.esprit.spring"
                ARTIFACT_ID = "gestion-station-ski"
                VERSION = "1.0"
            }
            steps {
                script {

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
         stage('Push Docker Image to Docker Hub') {
                    agent { label 'agent1' }
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'dockerhub_credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
                                sh 'docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
                            }
                        }
                    }
                }
                stage('Deploy to AKS') {
                            agent { label 'agent2' }
                            steps {
                                script {
                                    def clusterExists = sh(script: 'kubectl get nodes', returnStatus: true) == 0

                                    if (clusterExists) {
                                        echo "The AKS cluster exists and is accessible."
                                        sh 'kubectl apply -f deployment.yaml'
                                    } else {
                                        echo "The AKS cluster does not exist. Creating the cluster with Terraform."
                                        sh '''
                                            cd /home/vagrant/cluster
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
