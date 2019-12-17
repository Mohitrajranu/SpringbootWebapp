package com.restfulservice.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.restfulservice.exception.ApiResponse;
import com.restfulservice.model.WordPress;
import com.restfulservice.service.Webservice;
import com.restfulservice.util.BizUtil;
// TODO: Auto-generated Javadoc

/**
 * The Class WebServiceController.
 * @author Mohit Raj
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wsapi")
public class WebServiceController {

	/** The webservice. */
	@Autowired
	private Webservice webservice;

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @param ucBuilder the uc builder
	 * @return the response entity
	 */
	@RequestMapping(value = "/postWpapi/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody WordPress user, UriComponentsBuilder ucBuilder) {
		if (BizUtil.isNullString(user.getEmailid())) {
			return new ResponseEntity<>(new ApiResponse(false, "Email Address  doesnot exist!"), HttpStatus.BAD_REQUEST);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		webservice.savefunnel(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/apirest/wsapi/postWpapi/{id}").buildAndExpand(user.getEmailid()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * Retrieve all data.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @return the map
	 */
	@RequestMapping(value="/getuserwebserviceDet" ,method = RequestMethod.GET)
	public  Map<String, Object> retrieveAllData(@RequestParam String user,@RequestParam String servicename){
		
		 return webservice.getuserwsdata(user, servicename);
	}
	
	/**
	 * Retrieve ws data.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @param webserviceid the webserviceid
	 * @return the map
	 */
	@RequestMapping(value="/getwebserviceDet" ,method = RequestMethod.GET)
	public  Map<String, Object> retrieveWsData(@RequestParam String user,@RequestParam String servicename,@RequestParam String webserviceid){
		
		 return webservice.getuserdata(user, servicename, webserviceid);
	}

	
	/**
	 * Save.
	 *
	 * @param json the json
	 * @return the map
	 */
	@RequestMapping(value="/postwebserviceDet" ,method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody String json){
		return webservice.postWebserviceData(json);
	}

	/**
	 * Update.
	 *
	 * @param json the json
	 * @return the map
	 */
	@RequestMapping(value="/updatewebserviceDet" ,method = RequestMethod.POST)
	public Map<String, Object> update(@RequestBody String json){
		return webservice.putWebserviceData(json);
	}
	
	/**
	 * Externalws.
	 *
	 * @param json the json
	 * @return the map
	 */
	@RequestMapping(value="/callapi" ,method = RequestMethod.POST)
	public Map<String, Object> externalws(@RequestBody String json){
		return webservice.callWebserviceData(json);
	}
	
	/**
	 * Delete.
	 *
	 * @param user the user
	 * @param servicename the servicename
	 * @param webserviceid the webserviceid
	 * @return the string
	 */
	@RequestMapping(value="/deletewebserviceDet" ,method = RequestMethod.GET)
	public String delete(@RequestParam String user,@RequestParam String servicename,@RequestParam String webserviceid){
	        return webservice.deletuserdata(user, servicename, webserviceid);
	    }
	
	/**
	 * Fetch mail count.
	 *
	 * @param CreatedBy the created by
	 * @param funnelName the funnel name
	 * @param subCategory the sub category
	 * @param mailFlag the mail flag
	 * @param arrayName the array name
	 * @return the string
	 */
	//String CreatedBy, String funnelName, String subCategory, String mailFlag sales@doctiger.com
	@RequestMapping(value="/fetchMailCountDet" ,method = RequestMethod.GET)
	public String fetchMailCount(@RequestParam String CreatedBy,@RequestParam String funnelName,@RequestParam String subCategory,@RequestParam String mailFlag,@RequestParam String arrayName){
	        return webservice.getMailSentCount(CreatedBy, funnelName, subCategory, mailFlag,arrayName);
	    }
	
}
