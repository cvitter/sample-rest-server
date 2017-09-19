pipeline {
   agent any

   options {
      buildDiscarder(logRotator(numToKeepStr:'10'))
   }

   stages {
      stage('Build') {
         steps {
            sh 'mvn clean package'
         }
      }
      
      stage('Development Tests') {
         when {
            branch 'development'
         }
         steps {
            echo "Run the development tests!"
         }
      }
   }
}
