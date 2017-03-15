pipeline {
	// Specify the agent to use in execution of the pipeline
    agent any
    
    parameters {
        string(name: 'DOCKER_IMG_NAME', defaultValue: 'rest-server:${POM_VERSION}')
    }

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
				//   - package flag builds our jars and runs unit tests
				//   - site flag runs PMD and builds our static analysis report
				sh 'mvn package site'
			}
		}
		 
		stage('Post Build') {
			steps {
				echo 'Stage: Post Build'
				
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
				echo "Docker Image: ${DOCKER_IMG_NAME}"
			}
		}
    }
}