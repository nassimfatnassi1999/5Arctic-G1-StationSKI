
pipeline {
    agent { label 'default' } // Agent par défaut pour le pipeline
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
 environment {
        DOCKER_IMAGE = 'manaimaram-g1-stationski'  // Dynamic Docker image name
        IMAGE_TAG = 'latest'  // Image tag (e.g., 'latest' or version)
        KUBECONFIG = '/home/vagrant/.kube/config'
        PROMETHEUS_URL = 'http://192.168.50.4:9090'
        GRAFANA_URL = 'http://192.168.50.4:3000'

    }
    stages {


   /*  stage('webhook done') {
                steps {
                    echo "Pipeline triggered by push event"
                }
            }*/


        stage('Clone Repository') {
        agent { label 'default' }
            steps {
                echo 'Cloning the GitHub repository'
                // Get code from the GitHub repository
                git(
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git',
                    branch: 'ManaiMaram_G1_StationSKI'
                )
            }
        }

        stage('Clean and Compile') {
        //agent any
           agent { label 'default' } // Utiliser agent1 pour cette étape
            steps {
                echo 'Building the project with Maven'
                // Exécutez Maven pour nettoyer et compiler
                sh 'mvn clean install'
            }
        }

       stage('Generate JaCoCo Report') {
        agent any
            steps {
                echo 'Generating JaCoCo report'
                // Exécutez Maven pour générer le rapport JaCoCo
                sh 'mvn jacoco:report'
            }
        }

        stage('Code Quality with SonarQube') {
       // agent any
       agent { label 'default' }
            environment {
                SONAR_URL = "http://192.168.50.4:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    echo 'Running SonarQube analysis'
                    sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=$SONAR_URL \
                     -Dsonar.coverage.jacoco.xmlReportPaths=/home/vagrant/workspace/ManaiMaram_G1_StationSKI/target/site/jacoco/jacoco.xml"
                }
            }
        }

        stage('Deploy to Nexus') {
        agent { label 'default' }
            steps {
                echo 'Deploying to Nexus'
                // Using Nexus credentials
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh 'mvn deploy -DskipTests=true -Dnexus.username=$NEXUS_USER -Dnexus.password=$NEXUS_PASS'
                }
            }
        }
        stage('Build Docker Image') {
        agent { label 'default' }
            steps {
                script {
                    def nexusUrl = "http://192.168.50.4:8081"
                    def groupId = "tn.esprit.spring"
                    def artifactId = "gestion-station-ski"
                    def version = "1.0"

                    sh """
                        docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} \
                        --build-arg NEXUS_URL=${nexusUrl} \
                        --build-arg GROUP_ID=${groupId} \
                        --build-arg ARTIFACT_ID=${artifactId} \
                        --build-arg VERSION=${version} .
                    """
                }
            }
        }

                  

                stage('Push Docker Image to Docker Hub') {
                agent { label 'default' }
               
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
                                sh 'docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${IMAGE_TAG}'
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
                                  sh 'kubectl apply -f deploy.yml'
                              } else {
                                  echo "The AKS cluster does not exist. Creating the cluster with Terraform."
                                  sh '''
                                      cd /home/vagrant/mycluster
                                       terraform init
                                       terraform apply -auto-approve
                                  '''
                                  //sleep 60
                                  sh 'az aks get-credentials --resource-group myResourceGroup --name myAKSCluster --overwrite-existing'
                                  sh 'kubectl apply -f deploy.yml'
                              }
                          }
                      }
                  }



/*
 stage('Check VM Status') {
            steps {
                script {
                    def vmStatus = sh(script: "curl -s '${PROMETHEUS_URL}/api/v1/query?query=up{instance=\"192.168.50.4:9100\"}'", returnStdout: true).trim()

                    if (!vmStatus.contains('"value":[[1,"1"]]')) {
                        mail to: 'maram.manai@esprit.tn',
                             subject: "Alert: VM Down!",
                             body: """
                             The VM is down. Please check the status at the Grafana dashboard:
                             ${GRAFANA_URL}
                             """
                        error("VM is down")
                    } else {
                        echo "VM is up and healthy."
                    }
                }
            }
        }*/




    }

   post {
        success {
            script {
                slackSend(channel: '#jenkins-maram',
                          message: "Build succeeded: ${env.JOB_NAME} #${env.BUILD_NUMBER}!")
            }
        }
        failure {
            script {
                slackSend(channel: '#jenkins-maram',
                          message: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}.")
            }
        }
    }




}
