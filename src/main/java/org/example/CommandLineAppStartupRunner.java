package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.service.CsvParserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    public static int counter;

    @Autowired
    public CsvParserService csvParserService;

    @Override
    //todo: make this an application property
    public void run(String...args) throws Exception {
        log.info("CommandLineAppStartupRunner run method started with args: " + String.join(", ", args));
        csvParserService.processCsv("src/main/resources/data.csv");
    }
}
