package com.restfulservice.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericEmail.
 */
public class GenericEmail {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
	    final String username = "sales@doctiger.com";  // like yourname@outlook.com 
	    final String password = "doctiger123";   // password here
	    final String FROMNAME = "Mail Campaign";
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	 
	    StringBuilder host= new   StringBuilder (SMTPMXLookup.isAddressValid(username));
	    props.put("mail.smtp.host", host.toString());
	    //props.put("mail.smtp.host", "smtp-mail.outlook.com");mx.cloud.leadaconvert.com,mailstore1.secureserver.net,smtp.secureserver.net
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	      });
	    session.setDebug(true);

	    try {

	        Message message = new MimeMessage(session);
	       // message.set
	        message.setFrom(new InternetAddress(username,FROMNAME));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse("mohitraj.ranu@gmail.com"));   // like inzi769@gmail.com
	        message.setSubject("Mail dynamic sender Test");
	        message.setText("HI you have done sending mail with dynamic host outlook");
            message.setReplyTo(new InternetAddress[] 
            	      {new InternetAddress("")});
	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
