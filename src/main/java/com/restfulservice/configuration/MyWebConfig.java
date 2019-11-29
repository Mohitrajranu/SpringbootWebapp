package com.restfulservice.configuration;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.mongodb.MongoClientOptions;

import freemarker.template.TemplateException;

@Configuration
@ComponentScan
public class MyWebConfig extends WebMvcConfigurerAdapter{
	
//	 @Override
//	    public void configureViewResolvers(ViewResolverRegistry registry) {
//	        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//	        resolver.setPrefix("/WEB-INF/view/");
//	        resolver.setSuffix(".jsp");
//	        resolver.setViewClass(JstlView.class);
//	        registry.viewResolver(resolver);
//	    }
//	
	@Bean
    public  MongoClientOptions mongoClientOptions(){
		System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
		System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
		System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
		System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions options=builder.sslEnabled(true).build();        
        return options;
    }
	@Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("classpath:templates", "src/main/resources/templates");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }	

}