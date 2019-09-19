package com.restfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
@SpringBootApplication(scanBasePackages = {"com.restfulservice.*"})
//@EnableCaching
public class MainApplicationClass extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MainApplicationClass.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
    	return application.sources(MainApplicationClass.class);
    }

}
