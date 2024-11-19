# RESTful application for Event Logger

---

RESTful API provides an ability to add and modify events of application logs.

## **Tech Stack**

---

[JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)  
[Spring Boot](https://spring.io/projects/spring-boot)  
[Maven](https://maven.apache.org/)  
[MyBatis](https://mybatis.org/mybatis-3/)  
[H2 in-memory database](https://www.h2database.com/html/main.html)

## **Build**

---

Prerequisite: Java JDK 21
> mvnw clean install

## **Run**

---

> mvnw spring-boot:run

## **Basic Authentication**

Use the following credentials to access the API:

- **Username:** `user`
- **Password:** `password`

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
    
Request body:  
    {
    "type": "WARNING",
    "message": "obsolete method",
    "userId": 10103,
    "transactionId": 333555668 
    }
    

Update event:

PUT: http://localhost:5000/events/{id}

Request body:
{
    "type": "INFO",
    "message": "event updated",
    "userId": 10101,
    "transactionId": 333555666
    }

Delete event:

DELETE: http://localhost:5000/events/{id}
