package com.apprApi.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;

@Configuration
public class JasyptConfig {

    private static Logger logger = LoggerFactory.getLogger(JasyptConfig.class);

    private final Environment env;

    public JasyptConfig(Environment env) {
        this.env = env;
    }

    /** application.yml의 설정 정보 암호화 내용을 관리함. => ENC(xxxxxx) */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {

        logger.debug("StringEncryptor.stringEncryptor 실행..");

        PooledPBEStringEncryptor pbeEncryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig pbeConfig = new SimpleStringPBEConfig();

        pbeConfig.setPassword(env.getProperty("jasypt.encryptor.password"));
        pbeConfig.setAlgorithm(env.getProperty("jasypt.encryptor.algorithm"));
        pbeConfig.setSaltGeneratorClassName(env.getProperty("jasypt.encryptor.salt-generator-classname"));
        pbeConfig.setPoolSize(env.getProperty("jasypt.encryptor.pool-size"));
        pbeConfig.setStringOutputType(env.getProperty("jasypt.encryptor.string-output-type"));
        pbeConfig.setKeyObtentionIterations(env.getProperty("jasypt.encryptor.key-obtention-iterations"));

        //logger.info("jasypt.encryptor.algorithm : " + env.getProperty("jasypt.encryptor.algorithm"));

        pbeEncryptor.setConfig(pbeConfig);

        return pbeEncryptor;

    }

}
