package org.example.controller;

import io.restassured.response.Response;
import org.example.service.CustomerDetailsApiService;
import org.example.testData.CsvParserServiceData;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.entities.CustomerDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerDetailsApiControllerTest {

    CsvParserServiceData testServiceData = new CsvParserServiceData();

    @MockitoBean
    private CustomerDetailsApiService customerDetailsApiService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Test POST /customerDetails/save - Created 201")
    public void testPostSaveCustomers() throws JSONException {
        Mockito.when(customerDetailsApiService.saveCustomerDetails(testServiceData.getCsvFileExpectedJson()))
                .thenReturn(testServiceData.getAllCustomerDetails());
        
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testServiceData.getCsvFileExpectedJson())
                .when()
                .post("/customerDetails/save");

        assertEquals(201, response.getStatusCode());
        JSONAssert.assertEquals(testServiceData.getCsvFileExpectedJson(), response.getBody().prettyPrint(), true);
    }

    @Test
    @DisplayName("Test GET /customerDetails/get/{customerRef} - OK 200")
    public void testGetCustomerByRef() {
        String customerRef = "CUST001";

        Mockito.when(customerDetailsApiService.getCustomerDetails(eq(customerRef)))
                .thenReturn(testServiceData.getFirstCustomer());

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("customerRef", customerRef)
                .when()
                .get("/customerDetails/get");

        assertEquals(200, response.getStatusCode());
        CustomerDetails actual = response.getBody().as(CustomerDetails.class);
        assertThat(testServiceData.getFirstCustomer()).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("Test GET /customerDetails/get/{customerRef} - Not Found 404")
    public void testGetCustomerByRefNotFound(){
        String customerRef = "CUST999999";

        Mockito.when(customerDetailsApiService.getCustomerDetails(eq(customerRef)))
                .thenReturn(null);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("customerRef", customerRef)
                .when()
                .get("/customerDetails/get");

        assertEquals(404, response.getStatusCode());
        assertThat(response.getBody().asString()).isEmpty();
    }

}