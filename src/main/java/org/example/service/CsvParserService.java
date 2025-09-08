package org.example.service;

import com.opencsv.CSVReader;
import org.example.controller.CustomerDetailsApiController;
import org.example.entities.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle CSV file parsing and processing.
 * Reads a CSV file, converts its contents into Java Objects, then into JSON.
 * Sends the data to the API controller's get endpoint for further processing.
 */
@Service
public class CsvParserService {

    @Autowired
    private JsonParserService jsonParserService;
    @Autowired
    private CustomerDetailsApiController customerDetailsApiController;

    /**
     * Process the CSV file: parse it and send the data to the API controller.
     *
     * @param csvFileInputStream input stream of the CSV file
     * @throws Exception if file reading or processing fails
     */
    public void processCsv(InputStream csvFileInputStream) throws Exception {
        String json = parseCsv(csvFileInputStream);
        customerDetailsApiController.saveCustomerDetails(json);
    }

    /**
     * Parse CSV file and convert to JSON string.
     *
     * @param csvFileInputStream input stream of the CSV file
     * @return JSON string representation of the CSV data
     * @throws Exception if file reading or parsing fails
     */
    private String parseCsv(InputStream csvFileInputStream) throws Exception {
        List<CustomerDetails> customers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(csvFileInputStream))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                CustomerDetails customer = new CustomerDetails(
                        line[0], // customerRef
                        line[1], // customerName
                        line[2], // addressLine1
                        line[3], // addressLine2
                        line[4], // town
                        line[5], // county
                        line[6], // country
                        line[7]  // postcode
                );
                customers.add(customer);
            }
        } catch (Exception e) {
            throw new Exception("Error reading or parsing CSV file");
        }
        return jsonParserService.writeObjectToJson(customers);
    }

}
