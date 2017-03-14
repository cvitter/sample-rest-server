pipeline {
	//
    agent any

	stages {
		
		stage('Checkout') {
			steps {
				echo 'Stage: Checkout'
				// Checkout our code from Github
				git 'https://github.com/cvitter/sample-rest-server'
			}
		}
		
		stage('Build') {
			steps {
				echo 'Stage: Build'
				// Build our code using Maven from the command line
				// - package flag builds our jars and runs unit tests
				// - site flag runs PMD and builds our static analysis documents
				sh 'mvn package site'
			}
		}
		 
		stage('Post Build') {
			steps {
				echo 'Stage: Post Build'
				
				//
				step([$class: 'PmdPublisher', pattern: '**/target/pmd.xml'])
				
				// - junit publishes our test re
				junit 'target/surefire-reports/*.xml'
				
				// Archives the jar uber jar file we create
				archiveArtifacts 'target/*with-dependencies.jar	'
			}
		}
    }
}