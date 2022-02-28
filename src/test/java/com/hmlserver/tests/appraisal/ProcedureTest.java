package com.hmlserver.tests.appraisal;

import com.google.gson.JsonObject;
import com.hmlserver.service.HmlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:test.properties")
public class ProcedureTest {

    private static Logger logger = LoggerFactory.getLogger(ProcedureTest.class);

    @Autowired
    private Environment env;

    @Autowired
    private HmlService hmlService;

    @BeforeEach
    void beforeEach() { logger.debug("ProcedureTest.BeforeEach"); }

    @Test
    void execute() throws Exception{

        /** Test 3단계 법칙 : given-when-then */

        /** 1. given */
        final String apprId = env.getProperty("appr.apprId");
//        final String separator = env.getProperty("appr.separator");
        final List<String> procedureList = new ArrayList<>(
          Arrays.asList(
//                  "SP_TPL_COVER", "SP_TPL_PHOTO", "SP_TPL_APPSHEET",
//                  "SP_TPL_P01", "SP_TPL_P02", "SP_TPL_P03", "SP_TPL_P04",
//                  "SP_TPL_A01", "SP_TPL_A02", "SP_TPL_A03", "SP_TPL_A04", "SP_TPL_A07", "SP_TPL_A08", "SP_TPL_A11",
//                  "SP_TPL_F01", "SP_TPL_F02", "SP_TPL_F03", "SP_TPL_F04",
//                  "SP_TPL_SPECIFICATION", "SP_TPL_MAP", "SP_TPL_BLUEPRINT", "SP_TPL_PICTURE"
                "SP_TPL_ALL"
          )
        );

//        List<String> fieldList = new ArrayList<>(
//            Arrays.asList(
//                "SP_SELECT_APP_F04/Field"
//            )
//        );

        Map<String, Object> params = new HashMap<String, Object>(){{
            put("apprId", apprId);
            put("procedureList", procedureList);
//            put("separator", separator);
//            put("fieldList", fieldList);
        }};
        /** ------------------------------- */

        /** 2. when */
        JsonObject dataJson = hmlService.callApprData(params);
        /** ------------------------------- */

        logger.info("dataJson :::: " + dataJson.toString());

        /** 3. then */
        assertThat(dataJson);
        /** ------------------------------- */

    }

    @AfterEach
    void afterEach() { logger.debug("ProcedureTest.AfterEach"); }

}
