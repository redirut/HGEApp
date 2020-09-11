FROM openjdk:8-jre-alpine
WORKDIR /usr/src/application
COPY target/*.jar application.jar
ENTRYPOINT ["java"]
CMD ["-jar", "application.jar"]