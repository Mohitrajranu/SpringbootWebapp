package com.restfulservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.restfulservice.model.UserToken;
import com.restfulservice.repository.UserTokenRepository;
import com.restfulservice.util.BizUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class UserServiceImpl.
 * @author Mohit Raj
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	/** The user token repository. */
	@Autowired
	private UserTokenRepository userTokenRepository;
	

	/* (non-Javadoc)
	 * @see com.restfulservice.service.UserService#findUserByEmail(java.lang.String)
	 */
	@Override
	public Optional<UserToken> findUserByEmail(String email) {
		return userTokenRepository.findByEmail(email);
	}

	/* (non-Javadoc)
	 * @see com.restfulservice.service.UserService#findUserByResetToken(java.lang.String)
	 */
	@Override
	public Optional<UserToken> findUserByResetToken(String resetToken) {
		return userTokenRepository.findByResetToken(resetToken);
	}

	/* (non-Javadoc)
	 * @see com.restfulservice.service.UserService#save(com.restfulservice.model.UserToken)
	 */
	@Override
	public void save(UserToken userToken) {
		userTokenRepository.save(userToken);
	}
	
	/* (non-Javadoc)
	 * @see com.restfulservice.service.UserService#unsubscribeList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public  void unsubscribeList(String mailTemplate,String unSubscribeEmail,String url){

		MongoClientURI connectionString = null;
		MongoClient mongo = null;
		MongoDatabase db = null;
		MongoCollection<Document> table = null;
		MongoCursor<Document> cursor  =  null;
		
		try{
			System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
			System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
			System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
			System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
			String uri = "mongodb://localhost:27017/?ssl=true";
			//String uri = "mongodb://localhost:27017";
			connectionString = new MongoClientURI(uri);
			mongo = new MongoClient(connectionString);
			db = mongo.getDatabase("salesautoconvert");
			table = db.getCollection("FirstCategoryMails");
			String col_name = "Campaign_id", srch_string = mailTemplate;
			
		       FindIterable<Document> fi = table.find(Filters.eq(col_name, srch_string));      
		        cursor = fi.iterator();
				
				while (cursor.hasNext()) {
					JSONObject obj=new JSONObject(cursor.next().toJson());
					JSONObject dependencyjson= new JSONObject();
					if(obj.has("CreatedBy") && obj.has("group") && obj.has("Campaign_id")  && obj.has("funnelName") && obj.has("SentExploreContacts") && (!(obj.getJSONArray("SentExploreContacts").isEmpty())) )
					{
					
					dependencyjson.put("email", obj.getString("CreatedBy"));
					dependencyjson.put("group", obj.getString("group"));
					dependencyjson.put("MailTempName", obj.getString("Campaign_id"));
					String response=BizUtil.callPostAPIJSON(url, dependencyjson);
					 if(!BizUtil.isNullString(response)){
					 try {
						JSONObject outresponse = new JSONObject(response);
						BasicDBObject query = new BasicDBObject();
						query.put("CreatedBy", obj.getString("CreatedBy"));
						query.put("funnelName", obj.getString("funnelName"));
						query.put("group", obj.getString("group"));
						query.put(col_name, mailTemplate);
						BasicDBObject fields = new BasicDBObject("SentExploreContacts", 
						    new BasicDBObject( outresponse.getString("To"), unSubscribeEmail));
						BasicDBObject update = new BasicDBObject("$pull",fields);
						table.updateOne(query, update);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						continue;
					}
					 }}
					}
		}catch(Exception e){
		//	e.printStackTrace();
		}
		finally{
			 if(null != cursor){
	        	 cursor.close();
	        	 }
       	 if(null !=mongo){
       	 mongo.close();
       	 }
       }
		
	}

	@Override
	public List<UserToken> findByResetTokenIsNull() {
		return userTokenRepository.findByResetTokenIsNull();
	}


}