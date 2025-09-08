package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.service.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * AppStartupRunner implements CommandLineRunner to execute code at application startup.
 * It processes a CSV file specified by the 'csv.file-path' property using the CsvParserService.
 * Only active when the 'test' profile is not active.
 */
@Profile("!test")
@Component
@Slf4j
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    public CsvParserService csvParserService;

    @Value("${csv.file-path}")
    private String csvFilePath;

    /**
     * Runs the CSV processing logic at application startup.
     *
     * @param args command line arguments
     * @throws Exception if CSV processing fails
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("CommandLineAppStartupRunner run method started with args: {}", String.join(", ", args));
        if (csvFilePath == null || csvFilePath.isEmpty()) {
            throw new FileNotFoundException("CSV file path is not specified in application properties.");
        }

        InputStream csvFilePathInputStream;
        try {
            csvFilePathInputStream = getClass().getResourceAsStream(csvFilePath);
            if (csvFilePathInputStream == null) {
                throw new FileNotFoundException("Resource not found: " + csvFilePath);
            }
        } catch (Exception e) {
            log.error("Error reading CSV file path from properties: {}", e.getMessage());
            throw e;
        }

        csvParserService.processCsv(csvFilePathInputStream);
    }
}
