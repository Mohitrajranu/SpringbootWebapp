package com.restfulservice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.restfulservice.model.Mail;
import com.restfulservice.model.Mailer;
import com.restfulservice.model.UserToken;
import com.restfulservice.service.EmailService;
import com.restfulservice.service.UserService;
import com.restfulservice.util.BizUtil;
@CrossOrigin( origins = "*" )
@RestController
@RequestMapping("/mailapi")
public class MailApiController {

	private static final Logger log = LoggerFactory.getLogger(MailApiController.class);
	
	 @Autowired
	  private EmailService emailService;
	 @Autowired
		private UserService userService;
	 /*@Value("${app.Url}")
	 private String url;*/
	/* @Value("${app.filePath}")
	 private String filePath;*/
	 
	 @Value("${app.reseturl}")
	 private String reseturl;
	 
	 @Value("${app.slingUrl}")
	 private String urlString;
	 
	 //app.mailTangyslingUrl
	 @Value("${app.shoppingcarturl}")
	 private String shoppingcarturl;
	 
	 @Value("${app.mailapiurl}")
	 private String mailapiurl;
	 
	 @Value("${app.mailTangyslingUrl}")
	 private String mailTangyServerUrl;
	
	 
	 @RequestMapping(value = "/provisionApi", method = RequestMethod.GET)
		public String provision(@RequestParam("email") String email,HttpServletRequest request){
			String s = null;
			String appUrl = null;
			Map<String, Object> model = null;
			try{
				Optional<UserToken> optional = userService.findUserByEmail(email.trim());
		 	       if (!optional.isPresent()) {
		 	        UserToken user = new UserToken();
		 	        user.setCurrentstatus(0);
		 	        user.setEmail(email.trim());
					user.setResetToken(UUID.randomUUID().toString());
					userService.save(user);
			       appUrl = request.getScheme() + "://" + request.getServerName()+":8087/apirest/reset?token="+user.getResetToken();
		 	      
		 	       }else{
		 	    	  UserToken user = optional.get();
		 	    	  appUrl = request.getScheme() + "://" + request.getServerName()+":8087/apirest/reset?token="+user.getResetToken();
		 	       }
		 	      Mailer mailer = new Mailer();
					//mailer.setTo(user.getEmail());
					model = new HashMap<String, Object>();
					//model.put("username", user.getEmail());
					model.put("link", appUrl);
					mailer.setModel(model);
					mailer.setMailSubject("Provision Request for UserId"+email.trim());
					mailer.setMailFrom("bizlem.demo@gmail.com");
					
					mailer.setMailTo(email.trim());
					emailService.sendProvisionEmail(mailer);
					s="Provision mail Sent";
			}catch(Exception e){
				s="Provision mail failed"+e.getMessage();
			}
			return s;
			}
	 
	 @RequestMapping(value="/send-mail" ,method = RequestMethod.POST)
		public String  send(@RequestBody Mail mail) {
		 log.info("Spring Mail - Sending Simple Email with JavaMailSender Example");
		 Long start = System.currentTimeMillis();
		
	        emailService.sendSimpleMessage(mail);
	        return String.format("Message sent to %s in %d ms", mail.getTo(), System.currentTimeMillis() - start);
	  }
	 
	  /*@RequestMapping(value="/generate-file" ,method = RequestMethod.POST)
	    public ResponseEntity<?> generateFile(@RequestBody Map<String, String> reqbody,UriComponentsBuilder ucBuilder){
	        String writeToFile = reqbody.get("textContent");
	        String filename=null;
	        try {
	        	filename= filePath+System.currentTimeMillis()+".txt";
				Files.write(Paths.get(filename), writeToFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
	       // String serverUrl = "http://35.221.160.146:5000/?file=/home/ubuntu/pyscript/pharmacy_file/ExcelData1546000116428.txt";
	       String serverUrl = url+"?file="+filename;
	        RestTemplate restTemplate = new RestTemplate();
	       
	        ResponseEntity<String> responseG = restTemplate.getForEntity(serverUrl, String.class);
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("myresponse-header", responseG.getBody());
			headers.setLocation(ucBuilder.path("/mailapi/mail-template/{link}").buildAndExpand(responseG.getBody()).toUri());
		    return ResponseEntity.ok(responseG.getBody());
	        //return new ResponseEntity<String>(headers,HttpStatus.CREATED);
	    }
*/
	 ///mail-template /activemq/freeTrialNode
	 @SuppressWarnings("unchecked")
	@RequestMapping(value="/mail-template",method = RequestMethod.POST)
		public ResponseEntity<?> callActiveMqApi(@RequestBody Mailer mailer, UriComponentsBuilder ucBuilder) {
		 HttpHeaders resheaders = null;
		 JSONObject request = null;
			try {
				request = new JSONObject();
				request.put("name", "FreeTrialService");
				request.put("shoppingcarturl", shoppingcarturl);
				request.put("mailapiurl", mailapiurl);
				request.put("mailContent",mailer.getMailContent());
				request.put("link", mailer.getLink());
				request.put("mailTo", mailer.getMailTo());
				request.put("contentType",mailer.getContentType());
				request.put("mailSubject",mailer.getMailSubject());
				//getMailSubject mailSubject
				String randomNum= BizUtil.generateRandom();
				emailService.GetProducer("inbound.queue", randomNum, request.toString());
				resheaders = new HttpHeaders();
				
				resheaders.setLocation(ucBuilder.path("/mailapi/mail-template/{link}").buildAndExpand(mailer.getLink()).toUri());	
			}catch(Exception ex) {
				
			}
			
            
			
			return new ResponseEntity<String>(resheaders, HttpStatus.CREATED);

		}
	 
	 
	 
	 
	 
	 @SuppressWarnings("unchecked")
	@RequestMapping(value="/sendFreeTrialMail",method = RequestMethod.POST)
		public ResponseEntity<?>  sendEmail(@RequestBody Mailer mailer, UriComponentsBuilder ucBuilder) {
		 log.info("Spring Mail - Sending Simple Email with FreeMarker Example");
		 Long start = System.currentTimeMillis();

		 JSONObject request = null;
		 HttpHeaders headers = null;
		 HttpHeaders resheaders = null;
		 HttpEntity<String> entity = null;
		 RestTemplate restTemplate = null;
		 Map<String, Object> model = null;
		 ResponseEntity<String> loginResponse = null;
		 JSONObject requestPassword = null;
		 HttpEntity<String> passwordentity = null;
		 RestTemplate restPasswordTemplate = null;
		 ResponseEntity<String> passwordResponse = null;
		 String serverUrl = null;
		 /*ResponseEntity<String> responseG =null;
		 
		 */
		 String productType=null;
	        try {
	        	/*serverUrl = "http://bizlem.io:8082/portal/process/shoppingcart/ShoppingCartUserCheckServ123?userId="+mailer.getMailTo();
	 	        RestTemplate restGetTemplate = new RestTemplate();
	 	       
	 	        responseG = restGetTemplate.getForEntity(serverUrl, String.class);
	 	      
	 	       log.info("User Node Created in Shopping Cart"+responseG.getBody());
	        	*/
	        	serverUrl = "http://bizlem.io:8087/InvoiceAutoProcessUI/Userpassword";
				requestPassword = new JSONObject();
				requestPassword.put("username", mailer.getMailTo());
	        	headers = new HttpHeaders();
	        	headers.setContentType(MediaType.APPLICATION_JSON);
	        	passwordentity = new HttpEntity<String>(requestPassword.toString(), headers);
	        	restPasswordTemplate = new RestTemplate();
	        	passwordResponse = restPasswordTemplate
			        	  .exchange(serverUrl, HttpMethod.POST, passwordentity, String.class);
	        	
	        	//System.out.println(passwordResponse.getBody().toString());
	        	
	            model = new HashMap<String, Object>();
				model.put("message", mailer.getMailContent());
				model.put("link", mailer.getLink());
				model.put("location", "****warning***");
				model.put("signature", "This is a System Generated Mail");
				model.put("password", passwordResponse.getBody().toString());
				mailer.setModel(model);
	        	request = new JSONObject();
	        	request.put("username", mailer.getMailTo());
	        	request.put("productType", mailer.getContentType());
	        	resheaders = new HttpHeaders();
	        	
	            entity = new HttpEntity<String>(request.toString(), headers);
	        	restTemplate = new RestTemplate();
	        	productType = mailer.getContentType();
	        	switch (productType) {
	        	case "InvoiceAutoProcessFreeTrial":
	        		loginResponse = restTemplate
		        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
					break;
				case "DocTigerFreeTrial":
					loginResponse = restTemplate
		        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
					break;
				case "CarrotRuleFreeTrial":
					loginResponse = restTemplate
		        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
					break;
				case "MailTangyFreeTrial":
					loginResponse = restTemplate
		        	  .exchange(mailTangyServerUrl, HttpMethod.POST, entity, String.class);
					break;
				case "LeadAutoConvFrTrial":
					loginResponse = restTemplate
		        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
										break;
					//LeadAutoConvFrTrial,  MailTangyFrTrial
				default:
					loginResponse = restTemplate
		        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
					break;
				}
	        	//mailTangyServerUrl
	        	
	        	
	        	
	        	
	        	if (loginResponse.getStatusCode() == HttpStatus.OK) {
	        		emailService.sendEmail(mailer);
	        		log.info("Mail Sent in "+(System.currentTimeMillis() - start));
	        	} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
	        		log.error("Unable to create node for the user in freeTrial");
	        	}
				
			} catch (Exception e) {
				//e.printStackTrace();
				log.error("Getting error in sendFreeTrialMail api"+e.getMessage());
			}
	        resheaders.setLocation(ucBuilder.path("/mailapi/sendFreeTrialMail/{link}").buildAndExpand(mailer.getLink()).toUri());
			
			return new ResponseEntity<String>(resheaders, HttpStatus.CREATED);
	  }

	 @SuppressWarnings("unchecked")
		@RequestMapping(value="/createFreeTrialAccount",method = RequestMethod.POST)
			public ResponseEntity<?>  createAccount(@RequestBody Mailer mailer, UriComponentsBuilder ucBuilder) {
			 log.info("Spring Mail - Sending Simple Email with FreeMarker Example");
			 Long start = System.currentTimeMillis();

			 JSONObject request = null;
			 HttpHeaders headers = null;
			 HttpHeaders resheaders = null;
			 HttpEntity<String> entity = null;
			 RestTemplate restTemplate = null;
			 Map<String, Object> model = null;
			 ResponseEntity<String> loginResponse = null;
			 //JSONObject requestPassword = null;
			// HttpEntity<String> passwordentity = null;
			// RestTemplate restPasswordTemplate = null;
			 //ResponseEntity<String> passwordResponse = null;
			 String serverUrl = null;
			 ResponseEntity<String> responseG =null;
			
			 String productType=null;
		        try {
		        	//serverUrl = "http://bizlem.io:8087/InvoiceAutoProcessUI/Userpassword";
		 	        Optional<UserToken> optional = userService.findUserByEmail(mailer.getMailTo());
		 	       if (!optional.isPresent()) {
		 	        UserToken user = new UserToken();
		 	        user.setCurrentstatus(0);
		 	        user.setEmail(mailer.getMailTo());
					user.setResetToken(UUID.randomUUID().toString());

					// Save token to database
					userService.save(user);

					String appUrl = reseturl.trim()+user.getResetToken();
					serverUrl = shoppingcarturl+mailer.getMailTo();
		 	        RestTemplate restGetTemplate = new RestTemplate();
		 	       
		 	        responseG = restGetTemplate.getForEntity(serverUrl, String.class);
		 	      
		 	       log.info("User Node Created in Shopping Cart"+responseG.getBody());
		        	
		        	
		 	        
		 	        headers = new HttpHeaders();
		        	headers.setContentType(MediaType.APPLICATION_JSON);
		        
		        	//System.out.println(passwordResponse.getBody().toString());
		        	
		            model = new HashMap<String, Object>();
					model.put("message", mailer.getMailContent());
					model.put("link", appUrl);
					model.put("location", "****warning***");
					model.put("signature", "This is a System Generated Mail");
					/*model.put("password", passwordResponse.getBody().toString());*/
					mailer.setModel(model);
		        	request = new JSONObject();
		        	request.put("username", mailer.getMailTo());
		        	request.put("productType", mailer.getContentType());
		        	resheaders = new HttpHeaders();
		        	
		            entity = new HttpEntity<String>(request.toString(), headers);
		        	restTemplate = new RestTemplate();
		        	productType = mailer.getContentType();
		        	switch (productType) {
		        	case "InvoiceAutoProcessFreeTrial":
		        		loginResponse = restTemplate
			        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
						break;
					case "DocTigerFreeTrial":
						loginResponse = restTemplate
			        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
						break;
					case "CarrotRuleFreeTrial":
						loginResponse = restTemplate
			        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
						break;
					case "MailTangyFreeTrial":
						loginResponse = restTemplate
			        	  .exchange(mailTangyServerUrl, HttpMethod.POST, entity, String.class);
						break;
					case "LeadAutoConvFrTrial":
						loginResponse = restTemplate
			        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
											break;
						//LeadAutoConvFrTrial,  MailTangyFrTrial
					default:
						loginResponse = restTemplate
			        	  .exchange(urlString, HttpMethod.POST, entity, String.class);
						break;
					}
		        	//mailTangyServerUrl
		        	
		        	
		        	
		        	
		        	if (loginResponse.getStatusCode() == HttpStatus.OK) {
		        		
		        		emailService.sendFreeTrialEmail(mailer);
		        		log.info("Mail Sent in "+(System.currentTimeMillis() - start));
		        	} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
		        		log.error("Unable to create node for the user in freeTrial");
		        	}
		 	       }
				} catch (Exception e) {
					//e.printStackTrace();
					log.error("Getting error in sendFreeTrialMail api"+e.getMessage());
				}
		        resheaders.setLocation(ucBuilder.path("/mailapi/createFreeTrialAccount/{link}").buildAndExpand(mailer.getLink()).toUri());
		        
				return new ResponseEntity<String>(resheaders, HttpStatus.CREATED);
		  }
 
	 
	 }