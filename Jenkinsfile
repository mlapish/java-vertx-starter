node('maven') {
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
         sh "mvn -Dhttp.proxyHost=webproxystatic-on.tsl.telus.com -Dhttp.proxyPort=8080 -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/mvn -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}
