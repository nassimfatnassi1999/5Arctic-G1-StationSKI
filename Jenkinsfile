pipeline {
    agent any // Default agent for the pipeline

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
                    branch: 'HannachiNoursine_G1_StationSKI'
                )
            }
        }

        stage('Clean and Compile') {
            agent { label 'agent_1' } // Use agent_1 for this stage
            steps {
                echo 'Building the project with Maven'
                // Execute Maven to clean and compile
                sh 'mvn clean install'
            }
        }

        stage('Generate JaCoCo Report') {
            agent { label 'agent_1' } // Use agent_1 for this stage
            steps {
                echo 'Generating JaCoCo report'
                // Execute Maven to generate the JaCoCo report
                sh 'mvn jacoco:report'
            }
        }

        stage('Code Quality with SonarQube') {
            agent { label 'agent_1' } // Use agent_1 for this stage
            environment {
                SONAR_URL = "http://192.168.33.11:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    echo 'Running SonarQube analysis'
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.token=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Deploy to Nexus') {
            agent { label 'agent_1' } // Use agent_1 for this stage
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
        }
    }

    post {
        success {
            script {
                slackSend(
                    channel: '#jenkins_noursine',
                    message: "Le build a réussi : ${env.JOB_NAME} #${env.BUILD_NUMBER} ! Image pushed: arctic-g1-stationski:latest successfully. Backend IP: ${env.BACKEND_IP}"
                )
            }
        }
        failure {
            script {
                slackSend(
                    channel: '#jenkins_noursine',
                    message: "Le build a échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}."
                )
            }
        }
    }
}
