pipeline {

	agent any
    
    options {
    	buildDiscarder(logRotator(numToKeepStr:'10'))   	// Keep only the 10 most recent builds 
  	}
  	
  	environment {
  		SONAR = credentials('sonar')						// Sonar Credentials
  		SONAR_SERVER = "http://sonar.beedemo.net:9000"		// Sonar Server Address
  		DOCKERHUB = credentials('dockerhub')				// Docker Hub Credentials
		DOCKERHUB_REPO = "craigcloudbees"					// Repo on Docker Hub to push our image to
		DOCKER_IMG_NAME = "sample-rest-server:0.0.1"		// Name of our Docker image
  	}

	stages {
	
		stage('Build') {
			steps {
				sh 'mvn clean package'
				junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
			}
		}
		
		stage('Create Docker Image') {
			steps {
				// Build Docker image, log into Docker Hub, and push the image to our repo
				sh "docker build -t ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME} ./"
			}
		}

		
		stage('Test Docker Image') {
			steps {
				// Run the Docker image we created previously
				sh 'docker run -d -p 4567:4567 ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}'
				
				//
				
				// Stop the Docker image
				sh 'docker stop $(docker ps -q --filter ancestor="${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}") || true'
			}
		}


		stage('Push Docker Image') { // Pushes the Docker image to Docker Hub
			when {
				branch 'master'
			}
			steps {
				sh """
					docker login -u $DOCKERHUB_USR -p $DOCKERHUB_PSW
					docker push ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}
				"""
			}
		}
		
		
		stage('Delete Local Docker Image') {
			steps {
				echo ''
				//sh 'docker rmi -f  $(docker images | grep "${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}")'
			}
		}
		
		
		stage('Quality Analysis') {
			when {
				branch 'master'
			}
			steps {
				parallel (
					"Integration Test" : {
						echo 'Run integration tests here...'
					},
					"Sonar Scan" : {
						sh "mvn sonar:sonar -Dsonar.host.url=$SONAR_SERVER -Dsonar.organization=$SONAR_USR -Dsonar.login=$SONAR_PSW"
					}, failFast: true
				)	
			}
		}
	
		
		stage('Create Site') {
			when {
				branch 'master'
			}
			steps {
				sh 'mvn site:site'
			}
		}
		
		stage('Archive Artifacts') {
			when {
				branch 'master'
			}
			steps {
				// Archive the jar files created
				archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
				
				// Zip up the site directory and archive it
				sh 'zip -r target/site.zip target/site'
				archiveArtifacts artifacts: '**/target/*.zip', fingerprint: true
			}
		}
		
		stage('Debug Output') {
			when {
				branch 'development'
			}
			steps {
				sh 'pwd'
				sh 'ls -l'
				sh 'ls -l target'
			}
		}
		
		
    }
    
    post {
    
    	always {
    		echo 'Always'
    	}
    	
    	success {
    		echo 'success'
    	}
    	
    	failure {
    		echo 'failure'
    	}
    }

}