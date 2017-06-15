podTemplate(label: 'buildpod', inheritFrom: 'maven', privileged: true, serviceAccount: 'jenkins', cloud: 'openshift', containers: [
    containerTemplate(name: 'jnlp',  image: 'repomgr.tsl.telus.com:19903/telus/jenkins-slave:latest',
                      //alwaysPullImage: true,
           envVars: [
            containerEnvVar(key: 'https_proxy', value: 'http://webproxystatic-on.tsl.telus.com:8080'),
            containerEnvVar(key: 'no_proxy', value: '100.65.128.1'),   
            containerEnvVar(key: 'MAVEN_MIRROR_URL', value: 'http://mavenrepository.tsl.telus.com/nexus/service/local/repositories/central/content'),
            containerEnvVar(key: 'DOCKER_SOCKET', value: '/var/run/docker.sock')
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
         unstash name:"jar"
         sh "oc start-build java-vertx-starter --from-file=target/java-vertx-starter.jar --follow"
      }
      stage('Docker Test') {
            sh "docker login http://100.65.143.111:5000 -u jenkins -p `oc sa get-token jenkins`"
            sh "docker pull 100.65.143.111:5000/quota-test/java-vertx-starter"
      }
   }
}
