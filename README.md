# Spring GuestBook Sample Application

## About

* This application will consists two types of users
	* Guests
	* Administrator

* User needs to login in order to write a new entry in the application.
* Entry can be either a single image or a text.
* Administrator can view all the entries posted by the user. Also have permission to approve & remove the entries.

## What's inside 
This project is based on the [Spring Boot](https://spring.io/projects/spring-boot) project and uses these packages :
- Maven
- Spring Core
- Spring Data
- [Thymleaf](https://www.thymeleaf.org/)

## Installation 
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies

## Database configuration 
Create a MySQL database with the name `guestdb` and add the credentials to `/resources/application.yml`.  
The default ones are :

```
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/guestdb
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
```

## Usage 
Run the project through the IDE and head out to [http://localhost:8099/](http://localhost:8099/)

or 

run this command in the command line:
```
mvn spring-boot:run
```

## In case you find a bug/suggested improvement for Spring GuestBook
Our issue tracker is available here: https://github.com/ggautam/guestbook/issues