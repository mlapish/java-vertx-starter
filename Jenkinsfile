podTemplate(label: 'buildpod', inheritFrom: 'maven', privileged: true, serviceAccount: 'jenkins', cloud: 'openshift', idleMinutesStr: 2, 
    containers: [
    containerTemplate(name: 'jnlp',
      image: 'repomgr.tsl.telus.com:19903/telus/jenkins-slave',
      workingDir: '/home/jenkins',
      resourceRequestCpu: '500m',
      resourceLimitCpu: '500m',
      resourceRequestMemory: '512Mi',
      resourceLimitMemory: '512Mi', 
      envVars: [
        containerEnvVar(key: 'https_proxy', value: 'http://webproxystatic-on.tsl.telus.com:8080'),
        containerEnvVar(key: 'no_proxy', value: '100.65.128.1,.tsl.telus.com'),
        containerEnvVar(key: 'MAVEN_MIRROR_URL', value: 'http://mavenrepository.tsl.telus.com/nexus/content/repositories/telus-build-authorized'),
        containerEnvVar(key: 'BUILD_URL', value: env.BUILD_URL),
        containerEnvVar(key: 'JENKINS_NAME', value: 'buildpod'),
        containerEnvVar(key: '_JAVA_OPTIONS', value: '-Xmx256m')
    ])
    ],
  volumes: [
    hostPathVolume(hostPath: '/tmp/.m2/repository', mountPath: '/home/jenkins/.m2/repository'),
    hostPathVolume(hostPath: '/tmp/.sonar', mountPath: '/home/jenkins/.sonar'),
    secretVolume(mountPath: '/etc/imagepush', secretName: 'imagepushprod'),
    secretVolume(mountPath: '/etc/ocprod', secretName: 'ocprod'),
    secretVolume(mountPath: '/etc/jiraintegration', secretName: 'jiraintegration')
 ]) { 
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
      //stage('Docker Test') {        
      //      sh "sudo docker login http://100.65.143.111:5000 -u jenkins -p `oc whoami -t`"
      //      sh "sudo docker pull 100.65.143.111:5000/pipeline-demo/java-vertx-starter"
      //      sh "sudo docker tag 100.65.143.111:5000/pipeline-demo/java-vertx-starter:latest docker-registry-default.paas-app-east.tsl.telus.com/em-sandbox/java-vertx-starter:latest"
      //      sh "sudo docker push docker-registry-default.paas-app-east.tsl.telus.com/em-sandbox/java-vertx-starter:latest"
      //}
   }
}
