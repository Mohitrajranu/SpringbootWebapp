package com.restfulservice.controller;

import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.restfulservice.service.Webservice;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wsapi")
public class WebServiceController {

	@Autowired
	private Webservice webservice;

	@RequestMapping(value="/getwebserviceDet" ,method = RequestMethod.GET)
	public  Map<String, Object> generatePostgresData(@RequestParam String user,@RequestParam String servicename,@RequestParam String webserviceid){
		
		 JSONObject mainJson = null;
		 Map<String, Object> json = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		/* JSONObject input = null;
		 JSONArray arr = new JSONArray();*/
		 String uri = null;
			try {
				
				json = new HashMap<>();
				System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
				System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
				System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
				System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
				uri = "mongodb://localhost:27017/?ssl=true";
			   connectionString = new MongoClientURI(uri);
			   mongoClient = new MongoClient(connectionString);
			   database = mongoClient.getDatabase("webservice");
			   collection=database.getCollection(servicename);
			   FindIterable<Document> fi = collection.find(Filters.and(Filters.eq("user", user), Filters.eq("webserviceid", webserviceid)));        
		        MongoCursor<Document> cursor = fi.iterator();
		        try {
		            while(cursor.hasNext()) {
		               // log.info(cursor.next().toJson());
		            	mainJson = new JSONObject(cursor.next().toJson());
		            	//arr.put(mainJson);
		            }
		        } finally {
		            cursor.close();
		        }
		      if(null != mainJson){
				json.put("outputdata", mainJson.toString());
		      }else{
		    	  json.put("outputdata", "Blank");  
		      }
			}  catch (Exception e) {
				//e.printStackTrace();
				System.out.println("getwebserviceDet error occured "+e.getMessage());
			}
			finally{
				if(null!=mongoClient){
				mongoClient.close();
				mongoClient = null;
				}
			}
			return json;
	}
	
	@RequestMapping(value="/postwebserviceDet" ,method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody String json){
		// Map<String, Object> jsonOut = webservice.postWebserviceData(json);
		return webservice.postWebserviceData(json);
		
	}

	
	
	
}
