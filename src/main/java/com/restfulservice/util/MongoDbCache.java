package com.restfulservice.util;



	import org.bson.Document;

	import com.mongodb.MongoClient;
	import com.mongodb.MongoClientOptions;
	import com.mongodb.MongoClientURI;
	import com.mongodb.client.MongoCollection;
	import com.mongodb.client.MongoDatabase;

	public class MongoDbCache {

	private static final  String uri = "mongodb://localhost:27017/?ssl=true";
	private static MongoClientURI connectionString = null;
	private static MongoClient mongo = null;
	private static volatile MongoDbCache instance = null;
	private MongoDbCache(){


	}

	//MongoClient m = MongoDbCache.getInstance();

	public MongoClient getConnection() {
	return mongo;
	}

	public void destroyConnection() {
	if (mongo != null) {
	mongo.close();
	mongo = null;
	instance = null;
	}
	}
	// Utility method to get database instance
	private MongoDatabase getDB() {
	return mongo.getDatabase("test");
	}

	// Utility method to get user collection
	private MongoCollection<Document> getUserCollection() {
	return getDB().getCollection("user");
	}

	public static MongoDbCache getInstance() {
	// Double check locking principle.
	// If there is no instance available, create new one (i.e. lazy initialization).
	if (instance == null) {

	// To provide thread-safe implementation.
	synchronized(MongoDbCache.class) {

	// Check again as multiple threads can reach above step.
	if (instance == null) {
	instance = new MongoDbCache();
	try{
	System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
	System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
	System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
	System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
	MongoClientOptions.Builder options = MongoClientOptions.builder().connectionsPerHost(1);
	//.maxConnectionIdleTime((60 *1_000)).maxConnectionLifeTime((120 * 1_000));
	//String uri = "mongodb://localhost:27017";
	connectionString = new MongoClientURI(uri,options);
	mongo = new MongoClient(connectionString);
	// mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
	}
	catch(Exception e){

	}

	}
	}
	}
	return instance;
	}


	}

