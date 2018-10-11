gcloud builds submit --substitutions _TARGET=java-vertx-starter.jar,_BUCKET=build-targets --config cloudbuild.yaml .
