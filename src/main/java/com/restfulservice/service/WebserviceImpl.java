package com.restfulservice.service;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;
//import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import com.mongodb.AggregationOutput;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.restfulservice.model.WordPress;
import com.restfulservice.util.BizUtil;

@Service("webservice")
public class WebserviceImpl implements Webservice {
	private static final Logger log = LoggerFactory.getLogger(WebserviceImpl.class);
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
				log.error("getwebserviceDet error occured "+e.getMessage());
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
				log.error("getwebserviceDet error occured "+e.getMessage());
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
			log.error("postwebserviceDet error occured "+e.getMessage());
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
			log.error("putwebserviceDet error occured "+e.getMessage());
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
			log.error("deletuserdata error occured "+e.getMessage());
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
	public String getMailSentCount(String CreatedBy, String funnelName, String subCategory, String mailFlag,String arrayName) {

		MongoClientURI connectionString = null;
		MongoClient mongo = null;
		MongoDatabase db = null;
		MongoCollection<Document> table = null;
		
		StringBuilder resp = null;
		try{
			System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
			System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
			System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
			System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
			String uri = "mongodb://localhost:27017/?ssl=true";
			connectionString = new MongoClientURI(uri);
			mongo = new MongoClient(connectionString);
			db = mongo.getDatabase("salesautoconvert");
			table = db.getCollection("FirstCategoryMails");
			StringBuilder arrayN = null;
			/*
			db.FirstCategoryMails.aggregate({$match:{"funnelName" : "PersonalContacts","updateflag" : "2",
			"Category" : "Explore"}},{ $unwind: "$Datasource" },
			{ $group: { id: "", count: { $sum: 1 } } },{ $project: { id: 0, count: 1 } });
			
			BasicDBObject unwind = new BasicDBObject("$unwind", "$Datasource");
			BasicDBObject match = new BasicDBObject("$match", new BasicDBObject(
					"CreatedBy", CreatedBy).append("funnelName", funnelName).append("Category", subCategory).append("updateflag", mailFlag));
			BasicDBObject project = new BasicDBObject("$project", new BasicDBObject(
		            "_id", 0).append("count", 1));
		    List<BasicDBObject> pipeline = Arrays.asList(unwind, match, project);
		    AggregationOutput output = (AggregationOutput) table.aggregate(pipeline);
		    Iterable<DBObject> results = output.results();
		    for (DBObject result : results) {
		    	resp = new StringBuilder();
		    	resp.append(result.get("count").toString());
		    }
			
		    */
			resp = new StringBuilder();
			arrayN = new StringBuilder();
			arrayN.append("$").append(arrayName);
		    AggregateIterable<Document> output = table.aggregate(Arrays.asList(
		            new Document("$unwind", arrayN),
		            new Document("$match", new Document("CreatedBy", CreatedBy).append("funnelName", funnelName).append("Category", subCategory).append("updateflag", mailFlag)),
		            new Document("$group", new Document("_id", "").append("count", new Document("$sum",1))),
		           // new Document("$limit", 200),
		            new Document("$project", new Document("_id", 0)
		                        .append("count", 1))
		            ));

		    // Print for demo
		    for (Document dbObject : output)
		    {
		    	
		    	log.info("MailList count is "+dbObject.toJson());
		    	resp.append(dbObject.get("count").toString());
		    }
		    
		}catch(Exception e){
			resp = new StringBuilder();
			resp.append(e.getMessage());
		}
		finally{
			 
       	 if(null !=mongo){
       	 mongo.close();
       	 }
       }
		
		return resp.toString();
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
        		/*byte[] plainCredsBytes = accessToken.getBytes();
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
		/*	output= new JSONObject();
			output.put("Unit_Name__c", "12345");
			output.put("Day", "12");
			output.put("Month", "January");
			output.put("Year", "2018");
			output.put("seller_name__c", "Instrumech Company Limited");
			output.put("Sole_First_Purchaser__c", "Al-Azawi");
			output.put("Joint_Purchaser__c", "Al-Farhan");
			output.put("Nationality__c", "Emirati");
			output.put("Passport_No__c", "12T2345");
			output.put("Joint_Purchaser__c", "Al-Murad");
			output.put("Registration_Id__c", "367834");
			output.put("Registered_In__c", "653423");
			output.put("Date_of_Registration__c", "01-10-2028");
			output.put("Correspondence_Address__c", "Zayed City");
			output.put("City__c", "Dubai");
			output.put("Country__c", "UAE");
			output.put("Telephone__c", "9699999999");
			output.put("Mobile__c", "9899999999");
			output.put("Email__c", "xyz.gmail.com");
			output.put("Plot_Area__c", "5000sqft");
			output.put("Bedroom_Type_Name__c","1 BHK");
			output.put("Building_Name__c","Zenith");
			output.put("Master_Community_en__c","LineTek");
			output.put("Plot_Number__c","23");
			output.put("Purchase_Price__c","1000");
			output.put("Purchase_Price __c_en","2000");
			output.put("Permitted_Use__c","yes");
			output.put("Anticipated_Completion_Date__c","03-10-2018");
*/
        	jsonOut.put("outputdata", output.toString());
		}catch (Exception e) {
		log.error("External ws call error caught"+e.getMessage());
		}
		return jsonOut;
	}
	@Override
	public void savefunnel(WordPress user) {
		MongoClientURI connectionString = null;
		MongoClient mongo = null;
		MongoDatabase db = null;
		MongoCollection<Document> table = null;
		MongoCursor<Document> cursor  =  null;
		try{
			DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			Date dateobj = new Date();
			String Current_Date = df.format(dateobj);
			System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
			System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
			System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
			System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
			String uri = "mongodb://localhost:27017/?ssl=true";
			
			connectionString = new MongoClientURI(uri);
			mongo = new MongoClient(connectionString);
			db = mongo.getDatabase("salesautoconvert");
			table = db.getCollection("FirstCategoryMails");
			Bson updtfilter = and(eq("Category", "Explore"), eq("funnelName", user.getName()), eq("CreatedBy", "jobs@bizlem.com"));
			 FindIterable<Document> fi = table.find(updtfilter);      
		        cursor = fi.iterator();
		        boolean existflag = false;
				while (cursor.hasNext()) {
					cursor.next().toJson();	
					existflag = true;
				}
			if(!(existflag)){
				Document camp_details_doc = new Document();
				camp_details_doc.put("CreatedBy", "jobs@bizlem.com");
				camp_details_doc.put("funnelName", user.getName());
				camp_details_doc.put("Category", "Explore");
				camp_details_doc.put("FromName", "ExcelTemp");
				camp_details_doc.put("campaignName", "HeadRoom");
				camp_details_doc.put("FromEmailAddress", "HeadRoom");
				camp_details_doc.put("Campaign_id", "HeadRoom");
				camp_details_doc.put("Created_date", Current_Date);
				camp_details_doc.put("week", "3");
				camp_details_doc.put("group", "DoctigerMailTemplate");
				List<Document> schedulearr = new ArrayList<Document>();
				camp_details_doc.put("scheduleday", schedulearr);
				camp_details_doc.put("scheduleTime", "");
				camp_details_doc.put("updateflag", "-1");
				camp_details_doc.put("leadMailIdCount", "");
				camp_details_doc.put("subFunnelCampCount", "0");
				table.updateOne(updtfilter, new Document("$set", camp_details_doc), new UpdateOptions().upsert(true));
				Document newContact = new Document().append("Email", user.getEmailid())
				.append("FirstName", user.getFirstname())
				.append("LastName",user.getLastname())
				.append("PhoneNumber", "blank")
				.append("Country", user.getLocation())
				.append("CompanyName", user.getCompanyname())
				.append("CompanyHeadCount", user.getDesignation())
				.append("Industry", user.getCompanydomain())
				.append("Institute", user.getType())
				.append("Source", user.getSource());
				table.updateOne(updtfilter,Updates.addToSet("Contacts", newContact));
			}else{
				Document newContact = new Document().append("Email", user.getEmailid())
						.append("FirstName", user.getFirstname())
						.append("LastName",user.getLastname())
						.append("PhoneNumber", "blank")
						.append("Country", user.getLocation())
						.append("CompanyName", user.getCompanyname())
						.append("CompanyHeadCount", user.getDesignation())
						.append("Industry", user.getCompanydomain())
						.append("Institute", user.getType())
						.append("Source", user.getSource());
						table.updateOne(updtfilter,Updates.addToSet("Contacts", newContact));
			}
			
		
		}catch(Exception e){
			log.error("External funnel created error caught"+e.getMessage());
		}
		finally{
			
			 if(null !=cursor){
				 cursor.close();
				 cursor = null;
		       	 }
       	 if(null !=mongo){
       	 mongo.close();
       	mongo = null;
       	 }
       }
		
	}
}
