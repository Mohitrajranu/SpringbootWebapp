package com.restfulservice.service;

import java.util.Map;

public interface Webservice {

	public Map<String, Object> postWebserviceData(String json);
	public Map<String, Object> putWebserviceData(String json);
	public Map<String, Object> getuserwsdata(String user, String servicename);
	public Map<String, Object> getuserdata(String user, String servicename, String webserviceid);
	public String deletuserdata(String user, String servicename, String webserviceid);
	public Map<String, Object> callWebserviceData(String json);
}
