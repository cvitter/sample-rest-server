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
		APP_VERSION = "0.0.1"								// Version of the app, used to tag the Docker image
		DOCKER_IMG_NAME = "sample-rest-server"				// Name of our Docker image
		CONTAINER_ADDRESS = "localhost"						// Address at which running container can be reached
															// for http based testing ex: http://localhost:4567/hello
  	}

	stages {
	
		// Extract application information from the pom.xml file
		stage('Parse POM') {
			steps {
				script {
					pom = readMavenPom file: 'pom.xml'
					echo "${pom}"
					APP_VERSION = pom.version
					echo "pom.version = ${APP_VERSION}"
				}
			}
		}
	
		stage('Build') {
			steps {
				sh 'mvn clean package'
				junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
			}
		}
		
		
		stage('Create Docker Image') {
			steps {
				sh "docker build -t ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}:${APP_VERSION} ./"
			}
		}

		
		stage('Test Docker Image') {
			steps {
				// Run the Docker image we created previously
				sh 'docker run -d -p 4567:4567 ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}:${APP_VERSION}'
				
				// Use httpRequest to check default API endpoint, will throw an error if the endpoint
				// isn't accessible at the address specified
				//script {
				//	env.RESULT = httpRequest "http://${CONTAINER_ADDRESS}:4567/hello"
				//}
				// TODO: Capture test results and record somewhere
				
				// Stop the Docker image
				sh 'docker stop $(docker ps -q --filter ancestor="${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}:${APP_VERSION}") || true'
			}
		}


		// Pushes the Docker image to Docker Hub - Master only
		stage('Push Docker Image') { 
			when {
				branch 'master'
			}
			steps {
				sh """
					docker login -u $DOCKERHUB_USR -p $DOCKERHUB_PSW
					docker push ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}:${APP_VERSION}
				"""
			}
		}
		
		
		// Delete the Docker image created locally on the agent to clean up our environment
		stage('Delete Local Docker Image') {
			steps {
				sh 'docker images | grep "${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}" | xargs docker rmi -f || true'
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
    	
    	success {
    		echo 'SUCCESS!'
    	}
    	
    	failure {
    		echo 'FAILURE!'
    	}
    }

}