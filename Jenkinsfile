pipeline {
    agent any

	stages {
		stage('checkout') {
			steps {
				echo 'Stage: Checkout'
				
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], 
					doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], 
					userRemoteConfigs: [[url: 'https://github.com/cvitter/sample-rest-server']]])
			}
		}
    }
}