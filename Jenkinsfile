node('maven') {
openshiftBuild(buildConfig: 'java-vertx-sample', showBuildLogs: 'true', namespace: 'java-vertx-sample')
stage 'deploy'
openshiftDeploy(deploymentConfig: 'java-vertx-sample', namespace: 'java-vertx-sample')
}
