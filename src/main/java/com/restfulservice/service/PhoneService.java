package com.restfulservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.restfulservice.model.Phone;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Message;

@Service
public class PhoneService {
	private static final Logger log = LoggerFactory.getLogger(PhoneService.class);
	public static final String ACCOUNT_SID = "AC33bf01eb146bd37250ad87365b8cf3da"; 
    public static final String AUTH_TOKEN = "7f6b43e8ab135387afe9a7ef70dcd9db";
    public static final String TWILIO_NUMBER = "+18593286242";
    
    public void sendSMS(Phone phone) {
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", phone.getMsgbody()));
            params.add(new BasicNameValuePair("To", phone.getReceiver())); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            log.info("Sms sent and the sid is:: "+message.getSid());
        } 
        catch (TwilioRestException e) {
            log.error("Error Occured while sending sms:: "+e.getErrorMessage());
        }
    }
    
    public void makeCall(Phone phone) {
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Url", "https://brodan.biz/call.xml"));
            params.add(new BasicNameValuePair("To", phone.getReceiver())); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            CallFactory callFactory = client.getAccount().getCallFactory();
            Call call = callFactory.create(params);
        } 
        catch (TwilioRestException e) {
        	log.error("Error Occured while sending sms:: %d ",e.getErrorMessage());
           
        }
    }
    
}
