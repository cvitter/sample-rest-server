pipeline {
	// Specify the agent to use in execution of the pipeline
    agent { label 'swarm' }
    
    parameters {
        string(name: 'BUILD-GOALS', defaultValue: 'package site', description: 'Maven build goals/options')
    }
    
    environment {
    	// Update Jenkins with swarm agent path information to ensure sh and java execute at build time
        PATH = '/bin:/usr/bin/:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin'
    }
    
    options {
    	// Keep the 10 most recent builds
    	buildDiscarder(logRotator(numToKeepStr:'10')) 
  	}

	stages {

		stage('Building') {
		
			if (params.BUILD-GOALS == 'package site') {
            	sh 'mvn package site'
        	} else {
            	echo 'Nothing to see here!'
        	}

			post {
				success {
					// Archives the jar uber jar file we created
					archiveArtifacts 'target/*with-dependencies.jar'
					
					// Publish PMD analysis
					step([$class: 'PmdPublisher', pattern: 'target/pmd.xml'])
				
					// Publish Junit test reports
					junit 'target/surefire-reports/*.xml'
				}
			}
		}

		
		stage('Build Docker Image') {
			steps {
				//echo "Docker Image Name: ${DOCKER_IMG_NAME}"
				sh 'docker -v'
			}
		}
		
    }

}