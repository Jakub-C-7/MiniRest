package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MiniRestApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testMain() {
        MiniRestApplication.main(new String[]{});
    }
}
