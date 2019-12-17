package com.restfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
// TODO: Auto-generated Javadoc

/**
 * The Class MainApplicationClass.
 * @author Mohit Raj
 */
@SpringBootApplication(scanBasePackages = {"com.restfulservice.*"})
//@EnableCaching
public class MainApplicationClass extends SpringBootServletInitializer {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplicationClass.class, args);
    }

    /* (non-Javadoc)
     * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
    	return application.sources(MainApplicationClass.class);
    }

}
