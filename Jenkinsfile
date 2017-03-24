pipeline {
	// Specify the agent to use in execution of the pipeline
    agent { label 'swarm' }
    
    parameters {
        string(name: 'BUILD_GOALS', defaultValue: 'package site', description: 'Maven build goals/options')
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
		
            when {
                expression { params.BUILD_GOALS == 'package site' }
            }
            steps {
                sh 'mvn package site'
            }
            
            when {
                expression { params.BUILD_GOALS == 'clean' }
            }
            steps {
                sh 'mvn clean'
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