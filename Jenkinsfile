pipeline {

	agent any
    
    options {
    	// Keep the 10 most recent builds
    	buildDiscarder(logRotator(numToKeepStr:'10')) 
  	}

	stages {
	
		stage('Build') {
            steps {
                sh 'mvn clean package site'
            }
		}
		
    }

}