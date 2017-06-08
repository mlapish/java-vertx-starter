podTemplate(label: 'buildpod', inheritFrom: 'maven', serviceAccount: 'jenkins', cloud: 'openshift') {
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
         sh "oc start-build java-vertx-starter --from-file=target/java-vertx-starter.jar --follow"
      }
   }
}
