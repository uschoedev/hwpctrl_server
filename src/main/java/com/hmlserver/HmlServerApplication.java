package com.hmlserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

/**
 * @PropertySource : 설정파일 정보를 읽어서 환경 변수로 사용할 수 있도록 한다.
 * */
@SpringBootApplication
@PropertySource(value = {
        "classpath:application.properties"
    }
)
public class HmlServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HmlServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HmlServerApplication.class);
    }

}
