pipeline {
    agent any
    stages{
        stage("Build"){
            steps{
                echo 'Build'
                sh 'newgrp docker && docker compose build'
            }
        }
        stage("Deploy"){
            steps{
                echo 'Deploy'
                sh 'newgrp docker && docker compose up -d'
            }
        }
    }
}