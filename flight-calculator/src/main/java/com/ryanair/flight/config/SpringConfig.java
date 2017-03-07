package com.ryanair.flight.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Spring MVC based annotation configuration </p>
 * @author angel santos
 *
 */
@Configuration
@ComponentScan({"com.ryanair.flight.web.service"})
@PropertySource(ignoreResourceNotFound= true, value={"classpath:conf/default.properties", "file:${catalina.home}/conf/flight_app.properties"})
public class SpringConfig {

}
