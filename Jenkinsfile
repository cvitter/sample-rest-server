pipeline {
    agent any

	stages {
		stage('Checkout') {
			steps {
				echo 'Stage: Checkout'
				git 'https://github.com/cvitter/sample-rest-server'
			}
		}
		stage('Build') {
			steps {
				echo 'Stage: Build'
				sh 'mvn package site'
			}
		}
		stage('Post Build') {
			steps {
				echo 'Stage: Post Build'
				junit 'target/surefire-reports/*.xml'
				archiveArtifacts 'target/*.jar'
			}
		}
    }
}