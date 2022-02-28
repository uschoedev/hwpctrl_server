package com.hmlserver.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmlserver.filter.CORSFilter;
import com.hmlserver.filter.XSSEscapesFilter;
import com.hmlserver.filter.XSSRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Class : ServletConfig
 * @Author : uschoe
 * @Date : 2021.12.17
 */
@Configuration
@EnableWebMvc
@ComponentScan( basePackages = {"com.hmlserver.controller", "com.hmlserver.service"} )
public class ServletConfig implements WebMvcConfigurer {

    /** Client <-> Server 간 통신시 데이터를 UTF-8로 인코딩 */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));

    }

    /** Dispatcher-Servlet Enabled */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable("appServlet");
    }

    @Bean("objectMapper")
    public ObjectMapper getObjectMapper(){

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /** XSS 공격 방어 */
        objectMapper.getFactory().setCharacterEscapes(new XSSEscapesFilter());
        /** JSON Key를 String으로 감싸지 않아도 처리되도록 허용 */
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        return objectMapper;

    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setViewClass(InternalResourceView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setCache(false);
        resolver.setOrder(1);

        return resolver;

    }

    @Bean
    public SimpleMappingExceptionResolver mappingExceptionResolver(){

        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        resolver.setDefaultErrorView("error");
        resolver.setDefaultStatusCode(500);
        resolver.setExceptionAttribute("exception");
        resolver.setOrder(0);

        return resolver;

    }

    /** 리소스 경로 핸들링 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(20);

        registry
                .addResourceHandler("/appr/**")
                .addResourceLocations("classpath:/appr/")
                .setCachePeriod(20);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(false).maxAge(3600);

    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {

        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();

        try {

            CORSFilter corsFilter = new CORSFilter();

            filterBean.setFilter(corsFilter);
            filterBean.setFilter(corsFilter());
            filterBean.setOrder(1);
            filterBean.addUrlPatterns("*");

        } catch(Exception e){
            e.printStackTrace();
        }


        return filterBean;

    }

    /** Cors Filter 등록 */
    private CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        /** CrossOrigin 허용 */
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        /** POST,GET,HEAD,OPTIONS Allowed */
        config.addAllowedMethod(HttpMethod.HEAD);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.GET);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);

    }

}
