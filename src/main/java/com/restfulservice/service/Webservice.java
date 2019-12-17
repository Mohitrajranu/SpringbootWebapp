package com.restfulservice.service;

import java.util.Map;

import com.restfulservice.model.WordPress;

// TODO: Auto-generated Javadoc
/**
 * The Interface Webservice.
 * @author Mohit Raj
 */
public interface Webservice {

	/**
	 * Post webservice data.
	 *
	 * @param json the json
	 * @return the map
	 */
	public Map<String, Object> postWebserviceData(String json);
	
	/**
	 * Put webservice data.
	 *
	 * @param json the json
	 * @return the map
	 */
	public Map<String, Object> putWebserviceData(String json);
	
	/**
	 * Gets the userwsdata.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @return the userwsdata
	 */
	public Map<String, Object> getuserwsdata(String user, String servicename);
	
	/**
	 * Gets the userdata.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @param webserviceid the webserviceid
	 * @return the userdata
	 */
	public Map<String, Object> getuserdata(String user, String servicename, String webserviceid);
	
	/**
	 * Deletuserdata.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @param webserviceid the webserviceid
	 * @return the string
	 */
	public String deletuserdata(String user, String servicename, String webserviceid);
	
	/**
	 * Call webservice data.
	 *
	 * @param json the json
	 * @return the map
	 */
	public Map<String, Object> callWebserviceData(String json);
	 
 	/**
 	 * Gets the mail sent count.
 	 *
 	 * @param CreatedBy the created by
 	 * @param funnelName the funnel name
 	 * @param subCategory the sub category
 	 * @param mailFlag the mail flag
 	 * @param arrayName the array name
 	 * @return the mail sent count
 	 */
 	public String getMailSentCount(String CreatedBy, String funnelName,String subCategory,String mailFlag,String arrayName);
	 
 	/**
 	 * Savefunnel.
 	 *
 	 * @param user the user
 	 */
 	public void savefunnel(WordPress user);
}
