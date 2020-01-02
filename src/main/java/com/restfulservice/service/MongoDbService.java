package com.restfulservice.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.restfulservice.util.MongoDbCache;

@Component
public class MongoDbService {
	   
	   @PostConstruct
	   public void init() {
		   
		   MongoDbCache.getInstance();
	   }

	   @PreDestroy
	   public void destroy() {
		   
		   MongoDbCache.getInstance().destroyConnection();
	   }
	
}
