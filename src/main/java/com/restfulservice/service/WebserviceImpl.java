package com.restfulservice.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service("webservice")
public class WebserviceImpl implements Webservice {

	public Map<String, Object> postWebserviceData(String json) {
		Map<String, Object> jsonOut = null;
		// JSONObject objAllJson = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		 String uri = null;
		 String collectionname = null;
			//String webserviceid = null;
		try {
			    jsonOut =  new HashMap<>();
			 //   objAllJson = new JSONObject();
			    Document doc = Document.parse(json);
				collectionname = doc.getString("servicename");
			  //  webserviceid = doc.getString("webserviceid");
				System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
				System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
				System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
				System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
				uri = "mongodb://localhost:27017/?ssl=true";
			   connectionString = new MongoClientURI(uri);
			   mongoClient = new MongoClient(connectionString);
			   database = mongoClient.getDatabase("webservice");
			   collection=database.getCollection(collectionname);
			   collection.insertOne(doc);
			   
			/*
			objAllJson.put("result","success");
			objAllJson.put("webserviceid", webserviceid);
			objAllJson.put("servicename", collectionname);
			*/
			//webserviceid
			jsonOut.put("outputdata", json);
		//	System.out.println(jsonObj);
		} catch (Exception e) {
           // objAllJson.put("result","fail");
			
			jsonOut.put("outputdata", json);
			System.out.println("postwebserviceDet error occured "+e.getMessage());
		}
		finally{
			if(null!=mongoClient){
			mongoClient.close();
			mongoClient = null;
			}
		}
		return jsonOut;
	}
}
