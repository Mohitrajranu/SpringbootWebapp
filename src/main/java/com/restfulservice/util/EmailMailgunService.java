package com.restfulservice.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.restfulservice.model.Email;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
// TODO: Auto-generated Javadoc

/**
 * The Class EmailMailgunService.
 */
public class EmailMailgunService {
	
	/** The mailgun api key. */
	private String mailgunApiKey="e198500f1466df9518268093bcd13328-816b23ef-a0ebe910";
	
	/** The mailgun host. */
	private String mailgunHost="sandbox82ab133390874677a58cf1153c19e9ab.mailgun.org";
	
	/**
	 * Send.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean send(Email email) {
        try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter("api", mailgunApiKey));

			WebResource webResource = client.resource("https://api.mailgun.net/v3/" + mailgunHost +  "/messages");

			MultivaluedMapImpl formData = new MultivaluedMapImpl();
			formData.add("from", email.getFrom());
			formData.add("to", email.getTo().get(0));
			formData.add("subject", email.getSubject());
			formData.add("html", email.getMessage());

			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
			String output = clientResponse.getEntity(String.class);

			System.out.println("Email sent successfully : " + output);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return true;

    }
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			EmailMailgunService emgs = new EmailMailgunService();
			Email email = new Email();
			email.setFrom("sangeeta@bizlem.com");
			List<String> to = new ArrayList<String>();
			to.add("mohitraj.ranu@gmail.com");
			email.setTo(to);
			email.setSubject("Test mail from mailgun");
			email.setMessage(String.join(
			    System.getProperty("line.separator"),
			    "<h1>Mailgun api Email Test</h1>",
			    "<p>This email was sent with Mailgun using the ", 
			    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
			    " for <a href='https://www.java.com'>Java</a>."
			));
			
			emgs.send(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
