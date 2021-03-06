package com.apprApi.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Class RootConfig
 * @Author uschoe
 * @Date 2021.12.17
 */
@Configuration
@Import(value= { DatabaseConfig.class, JasyptConfig.class } )
public class RootConfig {

//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//
//            @Override
//            protected void postProcessContext(Context context) {
//
//                SecurityConstraint constraint = new SecurityConstraint();
//                SecurityCollection collection = new SecurityCollection();
//
//                collection.addPattern("/*");
//
//                constraint.setUserConstraint("CONFIDENTIAL");
//                constraint.addCollection(collection);
//
//                context.addConstraint(constraint);
//
//            }
//
//
//
//        };
//
//        tomcat.addAdditionalTomcatConnectors(createSslConnector());
//
//        return tomcat;
//
//    }
//
//    private Connector createSslConnector(){
//
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setSecure(false);
//        connector.setPort(3000);
//        connector.setRedirectPort(3443);
//
//        return connector;
//
//    }

}
