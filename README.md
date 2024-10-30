# RESTful application for Event Logger

---

RESTful API provides an ability to see and modify events of application logs.

## **Tech Stack**

---

JDK 21, MyBatis, H2 in-memory database

## **Build**

---

Prerequisite: Java JDK 21  
Docker installed on your machine

## **Run**

---
 
mvn spring-boot:run

## **Server**

---

http://localhost:5000/

## **Swagger documentation**

---

http://localhost:5000/swagger-ui/index.html

## **Sample flow**

---

See all events:

GET: http://localhost:5000/events

Create new event:

POST: http://localhost:5000/events

Update event:

PUT: http://localhost:5000/events/update/1

Delete event:

DELETE: http://localhost:5000/events/delete/1
