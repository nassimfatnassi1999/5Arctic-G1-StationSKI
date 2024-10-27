
pipeline {
    agent any // Agent par défaut pour le pipeline
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('Clone Repository') {
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
        agent any
          //  agent { label 'agent_1' } // Utiliser agent1 pour cette étape
            steps {
                echo 'Building the project with Maven'
                // Exécutez Maven pour nettoyer et compiler
                sh 'mvn clean install'
            }
        }

        stage('Generate JaCoCo Report') {
        agent any
           // agent { label 'agent_1' } // Utiliser agent1 pour cette étape
            steps {
                echo 'Generating JaCoCo report'
                // Exécutez Maven pour générer le rapport JaCoCo
                sh 'mvn jacoco:report'
            }
        }

        stage('Code Quality with SonarQube') {
        agent any
          //  agent { label 'agent_1' } // Utiliser agent1 pour cette étape
            environment {
                SONAR_URL = "http://192.168.50.4:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    echo 'Running SonarQube analysis'
                    sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=$SONAR_URL \
                     -Dsonar.coverage.jacoco.xmlReportPaths=/home/vagrant/workspace/ManaiMaram_G1_StationSKI/target/site/jacoco/jacoco.xml"
                }
            }
        }

      /*  stage('Deploy to Nexus') {
            agent { label 'agent_1' } // Utiliser agent1 pour cette étape
            steps {
                echo 'Deploying to Nexus'
                // Using Nexus credentials
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh 'mvn deploy -DskipTests=true -Dnexus.username=$NEXUS_USER -Dnexus.password=$NEXUS_PASS'
                }
            }
        }
         stage('Build Docker Image') {
                    agent { label 'agent_1' }
                    steps {
                        script {
                            sh 'docker build -t arctic-g1-stationski:latest /home/vagrant/workspace/HannachiNoursine_G1_StationSKI'
                        }
                    }
                }

                stage('Push Docker Image to Docker Hub') {
                    agent { label 'agent_1' }
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag arctic-g1-stationski:latest $DOCKER_USERNAME/arctic-g1-stationski:latest'
                                sh 'docker push $DOCKER_USERNAME/arctic-g1-stationski:latest'
                            }
                        }
                    }
                }*/


    }
}
