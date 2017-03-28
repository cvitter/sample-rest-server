pipeline {
	// Specify the agent to use in execution of the pipeline
    agent { label 'swarm' }
    
    parameters {
        // string(name: 'BUILD_GOALS', defaultValue: 'package site', description: 'Maven build goals/options')
        
        choice(
            // choices are a string of newline separated values
            // https://issues.jenkins-ci.org/browse/JENKINS-41180
            choices: 'Clean\nTest\nPackage\nSite\nAll',
            description: 'Maven Build Options',
            name: 'BUILD_GOALS')
    }
    
    //environment {
    	// Update Jenkins with swarm agent path information to ensure sh and java execute at build time
     //   PATH = '/bin:/usr/bin/:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin'
    //}
    
    options {
    	// Keep the 10 most recent builds
    	buildDiscarder(logRotator(numToKeepStr:'10')) 
  	}

	stages {
	
		stage('Clean') {
            when {
                expression { params.BUILD_GOALS == 'Clean' }
            }
            steps {
                sh 'mvn clean'
            }
		}
		
		stage('Test') {
            when {
                expression { params.BUILD_GOALS == 'Test' }
            }
            steps {
                sh 'mvn test'
            }
            post {
				success {
					// Publish Junit test reports
					junit 'target/surefire-reports/*.xml'
				}
			}
		}

		stage('Package') {
            when {
                expression { params.BUILD_GOALS == 'Package' }
            }
            steps {
                sh 'mvn package'
            }
			post {
				success {
					// Archives the jar uber jar file we created
					archiveArtifacts 'target/*with-dependencies.jar'
					
					// Publish PMD analysis
					//step([$class: 'PmdPublisher', pattern: 'target/pmd.xml'])
				
					// Publish Junit test reports
					junit 'target/surefire-reports/*.xml'
				}
			}
		}
		
		stage('Site') {
            when {
                expression { params.BUILD_GOALS == 'Site' }
            }
            steps {
                sh 'mvn site'
            }
            post {
				success {
					// Publish PMD analysis
					step([$class: 'PmdPublisher', pattern: 'target/pmd.xml'])
				}
			}
		}


		
    }

}