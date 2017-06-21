podTemplate(label: 'buildpod', inheritFrom: 'maven', privileged: true, serviceAccount: 'jenkins', cloud: 'openshift', containers: [
    containerTemplate(name: 'jnlp',  image: 'repomgr.tsl.telus.com:19903/telus/jenkins-slave',
                      //alwaysPullImage: true,
           envVars: [
            containerEnvVar(key: 'https_proxy', value: 'http://webproxystatic-on.tsl.telus.com:8080'),
            containerEnvVar(key: 'no_proxy', value: '100.65.128.1'),   
            containerEnvVar(key: 'MAVEN_MIRROR_URL', value: 'http://mavenrepository.tsl.telus.com/nexus/service/local/repositories/central/content'),            
        ])]
            ,volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]
            ) { 
    node('buildpod') {    
        
      stage('Preparation') { // for display purposes
         // Get some code from a GitHub repository
         String appName = "java-vertx-starter"
         git "https://github.com/mlapish/java-vertx-starter.git"
      }
      stage('Build') {
         // Run the maven build
         if (isUnix()) {
            sh "mvn package"
         } else {
            bat(/mvn package/)
         }
         stash name:"jar", includes:"target/java-vertx-starter.jar"
      }
      stage('Build Image') {
          steps{
             unstash name:"jar"
             sh "oc start-build java-vertx-starter --from-file=target/java-vertx-starter.jar --follow"
          }
      }
      stage('Docker Test') {        
            sh "sudo docker login http://100.65.143.111:5000 -u jenkins -p `oc whoami -t`"
            sh "sudo docker pull 100.65.143.111:5000/jenkins2-test/java-vertx-starter"
            sh "sudo docker tag 100.65.143.111:5000/jenkins2-test/java-vertx-starter:latest docker-registry-default.paas-app-east.tsl.telus.com/em-sandbox/java-vertx-starter:latest"
            sh "sudo docker push docker-registry-default.paas-app-east.tsl.telus.com/em-sandbox/java-vertx-starter:latest"
      }
   }
}
