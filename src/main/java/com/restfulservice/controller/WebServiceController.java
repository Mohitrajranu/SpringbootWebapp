package com.restfulservice.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.restfulservice.service.Webservice;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wsapi")
public class WebServiceController {

	@Autowired
	private Webservice webservice;

	@RequestMapping(value="/getuserwebserviceDet" ,method = RequestMethod.GET)
	public  Map<String, Object> retrieveAllData(@RequestParam String user,@RequestParam String servicename){
		
		 return webservice.getuserwsdata(user, servicename);
	}
	
	@RequestMapping(value="/getwebserviceDet" ,method = RequestMethod.GET)
	public  Map<String, Object> retrieveWsData(@RequestParam String user,@RequestParam String servicename,@RequestParam String webserviceid){
		
		 return webservice.getuserdata(user, servicename, webserviceid);
	}

	
	@RequestMapping(value="/postwebserviceDet" ,method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody String json){
		return webservice.postWebserviceData(json);
	}

	@RequestMapping(value="/updatewebserviceDet" ,method = RequestMethod.POST)
	public Map<String, Object> update(@RequestBody String json){
		return webservice.putWebserviceData(json);
	}
	
	@RequestMapping(value="/callapi" ,method = RequestMethod.POST)
	public Map<String, Object> externalws(@RequestBody String json){
		return webservice.callWebserviceData(json);
	}
	@RequestMapping(value="deletewebserviceDet" ,method = RequestMethod.GET)
	public String delete(@RequestParam String user,@RequestParam String servicename,@RequestParam String webserviceid){
	        return webservice.deletuserdata(user, servicename, webserviceid);
	    }
	
}
