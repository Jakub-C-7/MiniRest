package org.example;

import org.example.service.CsvParserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MiniRestApplicationTest {

    @MockitoSpyBean
    private AppStartupRunner appStartupRunner;
    @MockitoSpyBean
    private CsvParserService csvParserService;

    @Test
    @DisplayName("Test context loads")
    public void contextLoads() {
    }

    @Test
    @DisplayName("Test main method runs without exceptions")
    public void testMain() {
        MiniRestApplication.main(new String[]{});
    }

    @Test
    @DisplayName("Test CommandLineRunner runs on context start")
    public void commandLineRunnerRunsOnContextStart() throws Exception {
        verify(csvParserService, times(1)).processCsv(any());
        verify(appStartupRunner, times(1)).run();
    }
}
