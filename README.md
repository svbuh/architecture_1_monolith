# Swapi Service Application

This is a Spring Boot application that retrieves data from the Star Wars API (SWAPI) and stores it in an H2 database. It uses Vaadin for the frontend and implements a monolithic architecture.

## Prerequisites

- Java 21
- Maven 3.6.3 or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/svbuh/architecture_1_monolith.git
cd swapi-service-app
```
### Build the Project
To build the project, run the following command:
```bash
mvn clean
mvn install
```
### Run the Application
To run the application, use the following command:
```bash
mvn spring-boot:run
```
The application will start and will be accessible at http://localhost:8080.

### Accessing the H2 Console
You can access the H2 database console at http://localhost:8080/h2-console.
```bash
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
```

### Running Tests
To run the tests, use the following command:

```bash
mvn test
```