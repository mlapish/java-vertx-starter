podTemplate(label: 'buildpod', inheritFrom: 'maven', serviceAccount: 'jenkins') {
   node('buildpod') {
      def mvnHome
      stage('Preparation') { // for display purposes
         // Get some code from a GitHub repository
         git 'https://github.com/mlapish/java-vertx-starter.git'
         // Get the Maven tool.
         // ** NOTE: This 'M3' Maven tool must be configured
         // **       in the global configuration.           
         // mvnHome = tool 'M3'
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
