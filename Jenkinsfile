pipeline {

	agent any
    
    options {
    	buildDiscarder(logRotator(numToKeepStr:'10'))   // Keep only the 10 most recent builds 
  	}
  	
  	environment {
  		SONAR = credentials('sonar')
  		DOCKERHUB = credentials('dockerhub')
		DOCKERHUB_REPO = "craigcloudbees"
		DOCKER_IMG_NAME = "sample-rest-server:0.0.1"
  	}

	stages {
	
		stage('Build') {
			steps {
				sh 'mvn clean package'
				junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
				
				sh 'pwd'
				sh 'ls -l'
				sh 'ls -l target'
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
						sh "mvn sonar:sonar -Dsonar.host.url=http://sonar.beedemo.net:9000 -Dsonar.organization=$SONAR_USR -Dsonar.login=$SONAR_PSW"
					}, failFast: true
				)	
			}
		}
		
		stage('Create Docker Image') {
			//when {
			//	branch 'master'
			//}
			steps {
				// Build Docker image, log into Docker Hub, and push the image
				sh """
					docker build -t ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME} ./
					docker login -u $DOCKERHUB_USR -p $DOCKERHUB_PSW
					docker push ${DOCKERHUB_REPO}/${DOCKER_IMG_NAME}
				"""
			}
		}
		
		stage('Test Docker Image') {
			steps {
				echo 'Run the image'
			}
			post {
				always {
					echo 'Stop the running image'
				}
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