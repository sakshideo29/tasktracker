# Task Tracker API

A RESTful Task Management application built with Spring Boot that enables users to create, retrieve, update, and delete tasks. The application uses PostgreSQL for persistence, Flyway for database migrations, and Swagger/OpenAPI for API documentation.

## Features

* Create, update, delete, and retrieve tasks
* RESTful API design
* PostgreSQL database integration
* Flyway database migrations
* Swagger/OpenAPI documentation
* Spring Boot Actuator health monitoring
* Unit and integration testing with JUnit 5
* JSON-based request and response payloads

## Technology Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Flyway
* Swagger / OpenAPI
* Maven
* JUnit 5

## API Documentation

Swagger UI is available after starting the application:

```text
http://localhost:8080/swagger-ui.html
```

or

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI specification can be accessed at:

```text
http://localhost:8080/v3/api-docs
```

## Response Format

The API communicates using JSON.

Example response:

```json
{
  "id": 7,
  "title": "Buy milk",
  "status": "TODO"
}
```

## HTTP Status Codes

The API uses standard HTTP status codes:

| Status Code             | Meaning                           |
| ----------------------- | --------------------------------- |
| 200 OK                  | Request completed successfully    |
| 201 Created             | Resource created successfully     |
| 204 No Content          | Resource deleted successfully     |
| 404 Not Found           | Requested resource does not exist |
| 503 Service Unavailable | Service temporarily unavailable   |

## Database Configuration

Configure the following environment variables:

```bash
DB_URL=jdbc:postgresql://localhost:5432/dev
DB_USERNAME=postgres
DB_PASSWORD=root
```

Application configuration:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

## Health Monitoring

Health endpoint:

```http
GET /actuator/health
```

## Running the Application

```bash
mvn spring-boot:run
```

## Running Tests

```bash
mvn test
```