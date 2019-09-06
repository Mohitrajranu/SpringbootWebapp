package com.restfulservice.util;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CallPythonApi {

	public static void main(String[] args) {

		String randomNum= BizUtil.generateRandom();
		System.out.println(randomNum);
		   /*String serverUrl = "http://35.221.160.146:5012/?file=/home/ubuntu/pyscript/ESSAR_FILES/Pricing Approval Sheet 2.xlsx";
	        RestTemplate restTemplate = new RestTemplate();
	       
	        ResponseEntity<String> responseG = restTemplate.getForEntity(serverUrl, String.class);
	        JsonParser parser = new JsonParser();
	        
	        JsonObject obj = (JsonObject) parser.parse(responseG.getBody());
	        System.out.println(responseG.getBody());
*/	
		 HttpHeaders headers = null;
		JSONObject requestPassword = null;
		
		 HttpEntity<String> passwordentity = null;
		 RestTemplate restPasswordTemplate = null;
		 ResponseEntity<String> passwordResponse = null;
		try {
			String serverUrl = "http://bizlem.io:8087/InvoiceAutoProcessUI/Userpassword";
			requestPassword = new JSONObject();
			requestPassword.put("username", "rahul@bizlem.com");
        	headers = new HttpHeaders();
        	headers.setContentType(MediaType.APPLICATION_JSON);
        	passwordentity = new HttpEntity<String>(requestPassword.toString(), headers);
        	restPasswordTemplate = new RestTemplate();
        	passwordResponse = restPasswordTemplate
		        	  .exchange(serverUrl, HttpMethod.POST, passwordentity, String.class);
        	
        	System.out.println(passwordResponse.getBody().toString());
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	
	}
}
