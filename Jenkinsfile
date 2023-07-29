#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        jdk "jdk-17.0.1"
    }
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build and Deploy') {
            steps {
                echo 'Building'
                script {
                    sh './gradlew build'
                }
            }
        }
        stage('Upload to maven') {
        steps {
              echo 'Publishing'
              script {
                   sh './gradlew publish'
              }
           }
        }
    }
    post {
        always {
            archiveArtifacts 'Common/build/libs/**.jar'
            archiveArtifacts 'Fabric/build/libs/**.jar'
            archiveArtifacts 'Forge/build/libs/**.jar'
        }
    }
}
