pipeline {
    agent any
    stages {
        stage('Maven Build') {
            steps {
                script {
                    // Compile le projet avec Maven
                    sh 'mvn clean install'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Analyse le code avec SonarQube
                    withSonarQubeEnv('SonarQube') { // 'SonarQube' correspond au nom de l'installation SonarQube configurée dans Jenkins
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
    }
}
