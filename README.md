# RESTful application for Event Logger

---

RESTful API provides an ability to see and modify events of application logs.

## **Tech Stack**

---

JDK 22, MyBatis, Docker, H2 in-memory database

## **Build**

---

Preprequisite: Java JDK 22

## **Run**

---

mvn -N wrapper:wrapper  
./mvnw clean install  
./mvnw spring-boot:run

## **Server**

---

http://localhost:8080/

## **Swagger documentation**

---

http://localhost:8080/swagger-ui/index.html

## **Sample flow**

---

See all events:

GET: http://localhost:8080/events

Create new event:

POST: http://localhost:8080/events

Update event:

PUT: http://localhost:8080/events/update/1

Delete event:

DELETE: http://localhost:8080/events/delete/1



