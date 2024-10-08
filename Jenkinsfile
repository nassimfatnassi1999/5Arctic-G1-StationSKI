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
                    branch: 'nassimFatnassi-G1-SKI',
                    credentialsId: 'github-credentials'
                )
            }
        }
        
        stage('Clean and Install') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Static Analysis') {
            agent { label 'agent1' }
            environment {
                SONAR_URL = "http://192.168.43.11:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.host.url=${SONAR_URL} -Dsonar.java.binaries=target/classes'
                }
            }
        }
        
        stage('Generate Code Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Upload to Nexus') {
            steps {
                script {
                    echo "Deploying to Nexus..."

                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'http://192.168.33.11:9001',
                        repository: 'maven-ski-repository',
                        credentialsId: 'nexus-credentials',
                        groupId: 'tn.esprit.spring',
                        version: '1.0',
                        artifacts: [
                            [
                                artifactId: '5Arctic-G1-StationSKI',
                                classifier: '',
                                file: 'target/5Arctic-G1-StationSKI.jar',
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
