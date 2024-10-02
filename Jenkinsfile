pipeline {
    agent any

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: 'SamaaliMedAchref-G1-StationSKI',
',
                    url: 'https://github.com/nassimfatnassi1999/5Arctic-G1-StationSKI.git'
            }
        }

        stage('Compile') {
            steps {
                script {

                    sh 'mvn clean compile'
                    }

            }
        }
    }
}