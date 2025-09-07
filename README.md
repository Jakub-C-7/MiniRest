# MiniRest - A Customer API and CSV Data Parser

A Spring Boot REST API for storing and retrieving customer details.  
This API exposes endpoints to **save** customer records and **fetch** them by their reference ID.
This Java app also parses a CSV file and stores its contents in a database.

---

## 📋 Features
- **POST** endpoint to save a list of customer records.
- **GET** endpoint to retrieve a customer record by `customerRef`.
- JSON request/response format.
- CSV file parsing and data storage.
- Built with: **Spring Boot**, **JUnit**, **RestAssured**, **Maven**, **H2 Database**, and **SLF4J** logging.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+ 
- Maven 3.8+
- Git (If cloning the repository)

### Installation & Run
```bash
# Clone the repository
git clone https://github.com/Jakub-C-7/MiniRest.git
cd MiniRest

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API runs on port 8080 and will be accessible at `http://localhost:8080`.

### Running using Docker
```bash
# Build the docker image
docker build -t minirest-app .
# Run the docker container
docker run -p 8080:8080 minirest-app
```

---
## Testing

The project was developed using the Test Driven Development methodology, and includes unit and integration tests. 

```bash
# Run the tests
mvn test
```

The API has been tested using the RestAssured library. 

Mockito has been used where appropriate to mock dependencies and isolate units of code.

## CSV Parser

The application parses a CSV file located at `src/main/resources/data.csv` and saves its contents to the database. 

The application handles cases where there are one, multiple, or no records in the CSV file, and when the file is of an invalid format.

This is initiated at application start-up using a `CommandLineRunner`.

---
## Customer Details API Overview
API for saving and retrieving customer details.
### Swagger Specification
When the application is running, the openapi specification can be viewed using Swagger UI.

The Swagger UI can be used to interact with and test the API endpoints.

```bash
# View the Swagger UI specification at this URL
http://localhost:8080/swagger-ui.html

# View the OpenApi yaml file at this URL
http://localhost:8080/v3/api-docs
```
---

## Customer Details API Specification

### Version: 1.0.0

### /customerDetails/save

#### POST

##### Summary:
Save customer details

##### Description:

Saves one or more customer records from a JSON payload.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Customer details saved successfully |
| 400 | Invalid request payload |

### /customerDetails/get

#### GET

##### Summary:
Get customer details

##### Description:

Retrieves a customer record by its reference.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| customerRef | query | Unique reference for the customer | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Customer details found |
| 404 | Customer not found |
