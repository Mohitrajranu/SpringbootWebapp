package com.restfulservice.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.restfulservice.util.BizUtil;

@Service("webservice")
public class WebserviceImpl implements Webservice {

	public Map<String, Object> getuserwsdata(String user, String servicename) {
		JSONObject mainJson = null;
		JSONObject out =null;
		 Map<String, Object> json = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		 JSONArray arr = null;
		 String uri = null;
			try {
				arr = new JSONArray();
				out = new JSONObject();
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
			   FindIterable<Document> fi = collection.find(Filters.eq("user", user));        
		        MongoCursor<Document> cursor = fi.iterator();
		        try {
		            while(cursor.hasNext()) {
		            	mainJson = new JSONObject(cursor.next().toJson());
		            	arr.put(mainJson);
		            }
		        } finally {
		            cursor.close();
		        }
		        out.put("datalist", arr);
				json.put("outputdata", out.toString());
		     
			}  catch (Exception e) {
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
	public Map<String, Object> getuserdata(String user, String servicename, String webserviceid) {
		JSONObject mainJson = null;
		 Map<String, Object> json = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		
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
		            	mainJson = new JSONObject(cursor.next().toJson());
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
	
	public Map<String, Object> postWebserviceData(String json) {
		Map<String, Object> jsonOut = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		 String uri = null;
		 String collectionname = null;
		try {
			    jsonOut =  new HashMap<>();
			    Document doc = Document.parse(json);
				collectionname = doc.getString("servicename");
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
		
			jsonOut.put("outputdata", json);
		} catch (Exception e) {
			jsonOut =  new HashMap<>();
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

	public Map<String, Object> putWebserviceData(String json) {
		Map<String, Object> jsonOut = null;
		 MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		 String uri = null;
		 String collectionname = null;
		 String webserviceid = null;
		try {
			    jsonOut =  new HashMap<>();
			    Document doc = Document.parse(json);
				collectionname = doc.getString("servicename");
				webserviceid = doc.getString("webserviceid");
				System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
				System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
				System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
				System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
				uri = "mongodb://localhost:27017/?ssl=true";
			   connectionString = new MongoClientURI(uri);
			   mongoClient = new MongoClient(connectionString);
			   database = mongoClient.getDatabase("webservice");
			   collection=database.getCollection(collectionname);
			   Bson condition = new Document("$eq", webserviceid);
				Bson filter = new Document("webserviceid", condition);
				collection.deleteOne(filter);
			   
			   collection.insertOne(doc);
			   jsonOut.put("outputdata", json);
		} catch (Exception e) {
			jsonOut =  new HashMap<>();
			jsonOut.put("outputdata", json);
			System.out.println("putwebserviceDet error occured "+e.getMessage());
		}
		finally{
			if(null!=mongoClient){
			mongoClient.close();
			mongoClient = null;
			}
		}
		return jsonOut;
	}
	public String deletuserdata(String user, String servicename, String webserviceid) {
		MongoClient mongoClient = null;
		 MongoDatabase database  = null;
		 MongoCollection<Document> collection = null;
		 MongoClientURI connectionString = null;
		 String uri = null;
		 String res=null;
		 try {
				System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
				System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
				System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
				System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
				uri = "mongodb://localhost:27017/?ssl=true";
			   connectionString = new MongoClientURI(uri);
			   mongoClient = new MongoClient(connectionString);
			   database = mongoClient.getDatabase("webservice");
			   collection=database.getCollection(servicename);
			   Bson condition = new Document("$eq", webserviceid);
				Bson filter = new Document("webserviceid", condition);
				collection.deleteOne(filter);
				res = "Success";
			   
		} catch (Exception e) {
			res = "Fail";
			System.out.println("deletuserdata error occured "+e.getMessage());
		}
		finally{
			if(null!=mongoClient){
			mongoClient.close();
			mongoClient = null;
			}
		}
		
		return res;
	}
	@Override
	public Map<String, Object> callWebserviceData(String json) {
		HttpHeaders headers = null;
		JSONObject request = null;
		JSONObject response = null;
		JSONObject output = null;
		Map<String, Object> jsonOut = null;
		 HttpEntity<String> passwordentity = null;
		 RestTemplate restPasswordTemplate = null;
		 ResponseEntity<String> passwordResponse = null;
		try {
			jsonOut =  new HashMap<>();
			request = new JSONObject(json);
			String serverUrl = request.getString("url");
			response = request.getJSONObject("input");
        	headers = new HttpHeaders();
        	headers.setContentType(MediaType.APPLICATION_JSON);
        	restPasswordTemplate = new RestTemplate();
        	if(request.has("token") && (!BizUtil.isNullString(request.getString("token")))){
        		restPasswordTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        		restPasswordTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        		String accessToken = request.getString("token");
        		/*byte[] plainCredsBytes = plainCreds.getBytes();
        		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        		String base64Creds = new String(base64CredsBytes);
*/
        		headers.add("Authorization", "Bearer "+accessToken);
        	}
        	else if(request.has("username") && request.has("password") && ((!BizUtil.isNullString(request.getString("username"))) &&  (!BizUtil.isNullString(request.getString("password"))) ) ){
        		restPasswordTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        		restPasswordTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        		String plainCreds = request.getString("username")+":"+request.getString("password");
        		byte[] plainCredsBytes = plainCreds.getBytes();
        		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        		String base64Creds = new String(base64CredsBytes);
        		headers.add("Authorization", "Basic " + base64Creds);
        	} 
        	passwordentity = new HttpEntity<String>(response.toString(), headers);
        	passwordResponse = restPasswordTemplate
		        	  .exchange(serverUrl, HttpMethod.POST, passwordentity, String.class);
        	output = new JSONObject(passwordResponse.getBody().toString());
        	jsonOut.put("outputdata", output.toString());
		}catch (Exception e) {
			System.out.println("External ws call error caught"+e.getMessage());
		}
		return jsonOut;
	}
}
