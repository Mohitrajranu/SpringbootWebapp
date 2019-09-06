package com.restfulservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.restfulservice.model.Phone;
import com.restfulservice.service.PhoneService;

@RestController
@RequestMapping("/msggateway")
public class MessagecallController {

	public static final Logger logger = LoggerFactory.getLogger(MessagecallController.class);

	@Autowired 
	PhoneService phoneservice;
	
	 @RequestMapping(value="/phone-template",method = RequestMethod.POST)
		public ResponseEntity<?> phoneGateway(@RequestBody Phone phone, UriComponentsBuilder ucBuilder){
		 HttpHeaders headers = null;
		 try {
			if(phone.getMode().equalsIgnoreCase("text")){
				 phoneservice.sendSMS(phone);
				}
				else if (phone.getMode().equalsIgnoreCase("call")) {
					phoneservice.makeCall(phone);
				}
			 
			    headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/msggateway/phone-template/{receiver}").buildAndExpand(phone.getReceiver()).toUri());
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	 }
	
}
