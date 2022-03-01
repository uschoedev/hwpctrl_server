package com.apprApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * @Class : AppConfig
 * @Author : uschoe
 * @Date : 2021.12.17
 */
@Configuration
public class AppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
