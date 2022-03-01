package com.apprApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Class RootConfig
 * @Author uschoe
 * @Date 2021.12.17
 */
@Configuration
@Import(value= { DatabaseConfig.class } )
public class RootConfig {
}
