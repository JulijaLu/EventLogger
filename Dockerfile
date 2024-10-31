FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN ./mvnw clean package
EXPOSE 5000
COPY --from=build /target/event-logger-spring-boot.jar app.jar
