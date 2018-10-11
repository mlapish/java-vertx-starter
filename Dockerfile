# The Google App Engine openhdk. The source is on github at:
#   https://github.com/GoogleCloudPlatform/openjdk-runtime
# relies on the $jar_name being passed in at build time using
# --build-args
FROM gcr.io/google_appengine/openjdk:8
ARG jar_name
# Add application code.
COPY /target/$jar_name $APP_DESTINATION
