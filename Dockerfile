FROM openjdk:21
WORKDIR /eventApp
COPY target/event-logger.jar evenLogger.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "evenLogger.jar"]