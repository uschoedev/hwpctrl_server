package com.apprApi;

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
        "classpath:application.yml"
    }
)
public class ApprApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApprApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApprApiApplication.class);
    }

}
