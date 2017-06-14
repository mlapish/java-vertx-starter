podTemplate(label: 'buildpod', inheritFrom: 'maven', serviceAccount: 'jenkins', cloud: 'openshift', containers: [
    containerTemplate(name: 'jnlp',  image: 'openshift/jenkins-slave-maven-centos7',
           envVars: [
            containerEnvVar(key: 'https_proxy', value: 'http://webproxystatic-on.tsl.telus.com:8080'),
            containerEnvVar(key: 'no_proxy', value: '100.65.128.1'),   
            containerEnvVar(key: 'MAVEN_MIRROR_URL', value: 'http://mavenrepository.tsl.telus.com/nexus/service/local/repositories/central/content')
        ])]) { 
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
    }
    podTemplate(label: 'docker', serviceAccount: 'builder', containers: [containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)],
        volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]) { 
        node('docker'){
        stage('Docker Test') {
            sh "docker login http://100.65.143.111:5000 -u jenkins -p `oc sa get-token jenkins`"
            sh "docker pull 100.65.143.111:5000/quota-test/java-vertx-starter"
        }
        }
   }
}
