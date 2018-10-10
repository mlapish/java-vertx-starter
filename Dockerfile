# The Google App Engine python runtime is Debian Jessie with Python installed
# and various os-level packages to allow installation of popular Python
# libraries. The source is on github at:
#   https://github.com/GoogleCloudPlatform/python-docker
FROM gcr.io/google_appengine/openjdk:8
ARG jar_name
# Add application code.
COPY /target/$jar_name $APP_DESTINATION
