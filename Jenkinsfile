pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/murthuza/HOTEL_MANAGEMENT_FINAL.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t smarthotel-app .'
            }
        }
        stage('Docker Run') {
            steps {
                sh 'docker run -d -e SPRING_PROFILES_ACTIVE=docker -p 8089:8089 smarthotel-app'
            }
        }
    }
}
