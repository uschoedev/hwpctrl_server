package com.apprApi.tests;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active:local")
public class JasyptTest {

    private static Logger logger = LoggerFactory.getLogger(JasyptTest.class);

    @Test
    void execute() {

        final String userName = "dbUser";
        final String passWord = "dbPassword";
        final String dbSchema = "dbo";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();

        jasypt.setPassword("aims1234");
        jasypt.setAlgorithm("PBEWithMD5AndDES");

        final String encUserName = jasypt.encrypt(userName);
        final String encPassWord = jasypt.encrypt(passWord);
        final String encDbSchema = jasypt.encrypt(dbSchema);

        final String decUserName = jasypt.decrypt(encUserName);
        final String decPassWord = jasypt.decrypt(encPassWord);
        final String decDbSchema = jasypt.decrypt(encDbSchema);

        logger.info("encUserName : " + encUserName);
        logger.info("encPassWord : " + encPassWord);
        logger.info("encDbSchema : " + encDbSchema);

        logger.info("decUserName : " + decUserName);
        logger.info("decPassWord : " + decPassWord);
        logger.info("decDbSchema : " + decDbSchema);

    }

}
