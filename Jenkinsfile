pipeline {
    agent any
    stages{
        stage("Build"){
            steps{
                echo 'Build'
                sh 'docker compose build'
            }
        }
        stage("Deploy"){
            steps{
                echo 'Deploy'
                sh 'docker compose up -d'
            }
        }
    }
}