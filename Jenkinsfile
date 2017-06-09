podTemplate(label: 'buildpod', inheritFrom: 'maven', serviceAccount: 'jenkins', cloud: 'openshift', containers: [
    containerTemplate(name: 'jnlp', image: 'openshift/jenkins-slave-maven-centos7',
           envVars: [
            containerEnvVar(key: 'https_proxy', value: 'http://webproxystatic-on.tsl.telus.com:8080'),
            containerEnvVar(key: 'no_proxy', value: '100.65.128.1'),   
            containerEnvVar(key: 'MAVEN_MIRROR_URL', value: 'http://mavenrepository.tsl.telus.com/nexus/service/local/repositories/central/content')
        ])] ) {
    parameters {
        string(name: 'BUILD_CONFIG', defaultValue: 'java-vertx-starter', description: 'Build Configuration created in Openshift')
    } 
    node('buildpod') {    
      stage('Preparation') { // for display purposes
         // Get some code from a GitHub repository
         git 'https://github.com/mlapish/java-vertx-starter.git'
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
         sh "oc start-build ${params.BUILD_CONFIG} --from-file=target/java-vertx-starter.jar --follow"
      }
   }
}
