FROM openjdk:21
COPY target/event-logger-spring-boot.jar events.jar
CMD ["java", "-jar", "/events.jar"]