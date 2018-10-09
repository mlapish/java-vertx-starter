from openjdk:8-jre-alpine
copy . /deployments
CMD ["java", "-jar", "$(ls deployments/)"]
