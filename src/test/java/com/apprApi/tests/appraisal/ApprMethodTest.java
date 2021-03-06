package com.apprApi.tests.appraisal;

import com.apprApi.service.ApprApiService;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active:local")
//@SpringBootTest(properties = "spring.config.location=classpath:test.properties")
@TestPropertySource(locations = "classpath:test.properties")
public class ApprMethodTest {

    private static Logger logger = LoggerFactory.getLogger(ApprMethodTest.class);

    @Autowired
    private Environment env;

    @Autowired
    private ApprApiService apprApiService;

    @BeforeEach
    void beforeEach() { logger.debug("ApprMethodTest.BeforeEach"); }

    @Test
    void execute() throws Exception{

        /** Test 3단계 법칙 : given-when-then */

        /** 1. given */
        final String apprId = env.getProperty("appr.apprId");
        Map<String, Object> params = new HashMap<String, Object>(){{
            put("apprId", apprId);
        }};
        /** ------------------------------- */

        /** 2. when */
        JsonObject dataJson = apprApiService.checkApprMethod(params);
        /** ------------------------------- */

        logger.info("dataJson :::: " + dataJson.toString());

        /** 3. then */
        assertThat(dataJson);
        /** ------------------------------- */

    }

    @AfterEach
    void afterEach() { logger.debug("ApprMethodTest.AfterEach"); }

}
