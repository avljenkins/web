pipeline {
	agent any
options {
	buildDiscarder logRotator(daysToKeepStr: '5', numToKeepStr: '6')
	} 

  environment {
    MAJOR_VERSION = 1
	 DOCKER_IMAGE_NAME = "avljenkins/web-notifier"
	  AFAQY_IMAGE_NAME = "hub.eg.afaqy.co/java/web-notifier"
  }

  stages {
     stage('Build Docker Image') {
            
            steps {
		  sh 'mvn clean -Dmaven.javadoc.skip=true verify compile package install '  
		  sh 'cp docker/stage/Dockerfile .'
		    script {
                    app = docker.build(DOCKER_IMAGE_NAME) 
	            img = docker.build(AFAQY_IMAGE_NAME)		    
                
            }
	    }		    
        }
    stage('Push Docker Image') {
            
            steps {
		
                script { 
                   docker.withRegistry('https://registry.hub.docker.com', 'hub') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                   
				   }
                }
            }
        }
	  stage('Push Afaqy Image') {
            
            steps {
                script { 
		   withDockerServer([uri: "tcp://<hub.eg.afaqy.co-socket>"]) {	
                  // docker.withRegistry('https://hub.eg.afaqy.co') 
	            withDockerRegistry([credentialsId: 'afaqy-hub', url: "https://<hub.eg.afaqy.co-registry>/"]) {		   
			   ams.push("${env.BUILD_NUMBER}")
                        ams.push("latest")
                   
				   }
                }
		}
            }
	    }
    stage('build') {
      
      steps {
        sh 'echo success'
      }
      post {
        success {
          emailext(
            subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] Development Promoted to Master",
            body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' Development Promoted to Master":</p>
            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
            to: "ahmedaly@afaqy.com"
          )
        }
      }
    }
	  stage('building') {
      
      steps {
        sh 'echo success'
      }
  post {
    failure {
      emailext(
        subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] Failed!",
        body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' Failed!":</p>
        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
        to: "ahmedaly@afaqy.com"
      )
    }
  }
  }
  }
}
