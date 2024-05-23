pipeline {
    agent any
    stages{
        stage("Pull"){
            steps{
                echo 'Pull'
                sh 'cd /home/brunopascoa03/project/TQS_Project_2023_2024/ && git checkout main && git pull'
            }
        }
        stage("Build"){
            steps{
                echo 'Build'
                sh 'cd /home/brunopascoa03/project/TQS_Project_2023_2024/ && docker compose build'
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