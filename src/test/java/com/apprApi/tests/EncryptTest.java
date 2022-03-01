package com.apprApi.tests;

import com.apprApi.util.CryptoUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "classpath:encseed.properties")
@SpringBootTest
public class EncryptTest {

    private static final Logger logger = LoggerFactory.getLogger(EncryptTest.class);

    @Autowired
    private Environment env;

    @Test
    void execute() {

        CryptoUtil util = new CryptoUtil(env);

        String encSeed = util.encSeed("나라감정 웹기안기");
        String decSeed = util.decSeed("l1+SnwgOqGhvFfu4D7wiYA==");

        logger.info("encSeed :: " + encSeed);
        logger.info("decSeed :: " + decSeed);

    }

}
