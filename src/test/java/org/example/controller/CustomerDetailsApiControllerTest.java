package org.example.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.entities.CustomerDetails;
import org.example.service.CustomerDetailsApiService;
import org.example.testData.CsvParserServiceData;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerDetailsApiControllerTest {

    private final CsvParserServiceData testServiceData = new CsvParserServiceData();

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
        // Mock service interaction to return the expected list of customers
        Mockito.when(customerDetailsApiService.saveCustomerDetails(testServiceData.getCsvFileExpectedJson()))
                .thenReturn(testServiceData.getAllCustomerDetails());

        // Perform POST request to the controller endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testServiceData.getCsvFileExpectedJson())
                .when()
                .post("/customerDetails/save");

        // Verify response
        assertEquals(201, response.getStatusCode());
        JSONAssert.assertEquals(testServiceData.getCsvFileExpectedJson(), response.getBody().prettyPrint(), true);
    }

    @Test
    @DisplayName("Test GET /customerDetails/get/{customerRef} - OK 200")
    public void testGetCustomerByRef() {
        String customerRef = "CUST001";

        // Mock service interaction to return a specific customer for the test
        Mockito.when(customerDetailsApiService.getCustomerDetails(eq(customerRef)))
                .thenReturn(testServiceData.getFirstCustomer());

        // Perform GET request to the controller endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("customerRef", customerRef)
                .when()
                .get("/customerDetails/get");

        // Verify response
        assertEquals(200, response.getStatusCode());
        CustomerDetails responseBody = response.getBody().as(CustomerDetails.class);
        assertThat(testServiceData.getFirstCustomer()).usingRecursiveComparison().isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test GET /customerDetails/get/{customerRef} - Not Found 404")
    public void testGetCustomerByRefNotFound() {
        String customerRef = "CUST999999";

        // Mock service interaction to return null for a non-existent customer
        Mockito.when(customerDetailsApiService.getCustomerDetails(eq(customerRef)))
                .thenReturn(null);

        // Perform GET request to the controller endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("customerRef", customerRef)
                .when()
                .get("/customerDetails/get");

        // Verify response
        assertEquals(404, response.getStatusCode());
        assertThat(response.getBody().asString()).isEmpty();
    }

}