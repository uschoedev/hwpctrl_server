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

    /**
     * root-conxtext.xml 역할.. view 제외한 비즈니스 관련 로직 설정 */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    /**
     * servlet-conxext.xml 역할..
     * request <-> response 관련 설정... resolver, annotation, interceptor 등
     * */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class };
    }

    /**
     * 요청을 감지할 서블릿 지정
     * '/'로 할 경우, 전체 서블릿에 대한 감지를 하겠다는 뜻
     * */
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
