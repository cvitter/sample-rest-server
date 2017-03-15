pipeline {
	// Specify the agent to use in execution of the pipeline
    agent any
    
    //
    environment { 
        DOCKER_IMG_NAME = "demo-app:0.0.1"
    }
    
    options {
    	// Keep the 10 most recent builds
    	buildDiscarder(logRotator(numToKeepStr:'10')) 
  	}

	stages {
	
		stage('Checkout Code') {
			steps {
				echo 'Stage: Checkout Code'
				// Checkout our code from Github
				git 'https://github.com/cvitter/sample-rest-server'
			}
		}
		
		stage('Building') {
			steps {
				echo 'Stage: Building'
				// Build our code using Maven from the command line
				//   - package flag builds our jars and runs unit tests
				//   - site flag runs PMD and builds our static analysis report
				sh 'mvn package site'
			}
			post {
				success {
					// Archives the jar uber jar file we created
					archiveArtifacts 'target/*with-dependencies.jar	'
				}
			}
		}
		 
		stage('Publish Reports') {
			steps {
				echo 'Stage: Publish Reports'
				
				// Publish PMD analysis
				step([$class: 'PmdPublisher', pattern: 'target/pmd.xml'])
				
				// Publish Junit test reports
				junit 'target/surefire-reports/*.xml'
				
				// Archives the jar uber jar file we created
				archiveArtifacts 'target/*with-dependencies.jar	'
			}
		}
		
		stage('Build Docker Image') {
			steps {
				echo 'Stage: Build Docker Image'
				
				echo "Docker Image Name: ${DOCKER_IMG_NAME}"
			}
		}
		
    }

}