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
- Built with: **Spring Boot**, **JUnit**, **RestAssured**, **Maven**, **H2 Database**, **Mockito**, and **SLF4J** logging.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+ 
- Maven 3.8+
- Git (If cloning the repository)

### Installation & Run
```bash
# Clone the repository (HTTP)
git clone https://github.com/Jakub-C-7/MiniRest.git
# Or, clone the repository (SSH)
git clone git@github.com:Jakub-C-7/MiniRest.git

# Navigate into the directory
cd MiniRest

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application runs on port 8080 and will be accessible at `http://localhost:8080`.

### Running using Docker
```bash
# Navigate into the directory
cd MiniRest
# Build the docker image
docker build -t minirest-app .
# Run the docker container
docker run -p 8080:8080 minirest-app
```

---
## Testing

The project was developed using the Test Driven Development methodology, and unit and integration tests have been written to ensure the correctness of the code.

```bash
# Run the tests
mvn test
```

The API has been tested using the RestAssured library. 

Mockito has been used where appropriate to mock dependencies and isolate units of code.

JUnit 5 has been used as the testing framework.

## CSV Parser

The application parses a CSV file located at `src/main/resources/csv-files/data.csv` and sends it's contents to the API in JSON format which then saves its contents to the database.

The `CsvParserService` will first convert the CSV data into `CustomerDetails` Objects, and then uses the `JsonParserService` to convert these Objects into JSON format before sending it to the API. The API controller will then delegate the saving of these records to the `CustomerDetailsService` layer.

This initial CSV parsing run is initiated on application context start-up using a `CommandLineRunner`. The path to the CSV file can be configured in the `application.yaml` file by changing the value of `csv.file-path`.

The application handles cases where there are one, multiple, or no records in the CSV file, and when the file is of an invalid format.

---
## Customer Details API Overview
API for saving and retrieving customer details.

The API uses a `CustomerDetailsApiController` as the entrypoint for handling requests. This controller will delegate to the `CustomerDetailsService` to perform business logic, which then delegates to the `CustomerDetailsRepository` for data persistence.

Spring Data JPA is used for data persistence, with a Repository pattern for database operations, and an H2 in-memory database for quick development.

### /customerDetails/save
A POST endpoint to save customer details which accepts a JSON payload containing one or more customer records, and returns a JSON response.

The api service will parse the JSON payload into `CustomerDetails` objects and save them to the database.

If a record with the same `customerRef` already exists, it will be updated with the new details.

If the payload is invalid, a 400 Bad Request response will be returned through the use of a `ResponseStatusException`.

If the request is successful, a 201 CREATED response will be returned along with the list of saved customer details in JSON format.

### /customerDetails/get
A GET endpoint to retrieve a customer record by its `customerRef` which is passed in as a query parameter.

The service will use the `CustomerDetailsRepository` interface as find the record in the database by its ID `customerRef`.

If the record doesn't exist, a 404 Not Found response will be returned.

If the request is successful, a 200 OK response will be returned along with the customer details in JSON format.

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
