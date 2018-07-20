package com.example.ktws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class KtwsApplicationTests {

    @Test
    @Transactional
    public void contextLoads() {
        KtwsApplication.main(new String[]{});
    }

}
