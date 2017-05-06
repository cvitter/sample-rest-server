pipeline {

	agent any
    
    options {
    	buildDiscarder(logRotator(numToKeepStr:'10'))   // Keep only the 10 most recent builds 
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
		
			// Import our Sonar credentials in the environment block
			// See https://jenkins.io/doc/pipeline/tour/environment/#credentials-in-the-environment
			environment {
				SONAR = credentials('sonar')
			}
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
			environment {
				DOCKERHUB = credentials('dockerhub')
				DOCKER_IMG_NAME = "sample-rest-server:0.0.1"
			}
			//when {
			//	branch 'master'
			//}
			steps {
				sh 'docker -v'
				
				sh """
					docker build -t craigcloudbees/${DOCKER_IMG_NAME} ./
					docker login -u $DOCKERHUB_USR -p $DOCKERHUB_PSW
					docker push craigcloudbees/${DOCKER_IMG_NAME}
				"""
				
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