package com.restfulservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
// TODO: Auto-generated Javadoc
/**
 * The Class EmailHelper.
 */
public class EmailHelper {
 
 /**
  * The main method.
  *
  * @param args the arguments
  */
 //"smtp.secureserver.net", 993, "welcome@leadaconvert.com", "welcome@2019"
       public static void main(String[] args) {
    	   List<String> recpients= new ArrayList<>();
    	   recpients.add("mohitraj.ranu@gmail.com");
    	   recpients.add("mohitraj.ranu@outlook.com");
    	   recpients.add("mohit.raj@bizlem.io");
    	   sendMailViaGodaddy("welcome@leadaconvert.com","welcome@2019",recpients,"Send Mail Through Godaddy","Hi , Sending mail through godaady javamail api");
	}
        
        /**
         * Send mail via godaddy.
         *
         * @param from the from
         * @param password the password
         * @param to the to
         * @param subject the subject
         * @param text the text
         */
        public static void sendMailViaGodaddy(String from, String password,List<String> to,String subject,String text ) {
        try {
          Properties props = System.getProperties();
          props.setProperty("mail.transport.protocol", "smtp");
          props.setProperty("mail.host", "smtpout.secureserver.net");
                       
 
         props.put("mail.smtp.auth", "true");
         props.setProperty("mail.user", from);
         props.setProperty("mail.password", password);
 
        Session mailSession = Session.getDefaultInstance(props, null);
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport("smtp");
        MimeMessage message = new MimeMessage(mailSession);
        message.setSentDate(new java.util.Date());
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        for (int i=0;i < to.size();i++)
        {
                                         
         message.addRecipient(Message.RecipientType.TO, new  
          InternetAddress(to.get(i)));
        }
        message.setContent(text,"text/html");
        //message.setText(text);
 
                        transport.connect("smtpout.secureserver.net",from,password);
        transport.sendMessage(message,
         message.getRecipients(Message.RecipientType.TO));
        transport.close();
                       
         System.out.println("Email via go daddy sent");
        } catch (Exception e) {
        	e.printStackTrace();
        }
   }
}