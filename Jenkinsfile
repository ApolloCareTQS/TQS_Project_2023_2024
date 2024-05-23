pipeline {
    agent any
    stages{
        stage("Build"){
            steps{
                echo 'Build'
                sh 'cd /home/brunopascoa03/project/TQS_Project_2023_2024/ && docker compose build -d'
            }
        }
        stage("Deploy"){
            steps{
                echo 'Deploy'
                sh 'cd /home/brunopascoa03/project/TQS_Project_2023_2024/ && docker compose up -d'
            }
        }
    }
}