package com.restfulservice.util;

import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 

// TODO: Auto-generated Javadoc
/**
 * The Class SendEmail.
 */
public class SendEmail {// Replace sender@example.com with your "From" address.
    // This address must be verified."Chetan Agarwal" <chetan_agarwal@hotmail.com>; 
   // static final String FROM = "welcome@leadaconvert.com";
	
	
    /** The Constant FROM. */
static final String FROM = "welcome@leadaconvert.com";
    
    /** The Constant FROMNAME. */
    static final String FROMNAME = "Doctiger";
    
    /** The Constant REPLYTO. */
    static final String REPLYTO = "welcome@leadaconvert.com";
    // Replace recipient@example.com with a "To" address. If your account 
    /** The Constant TO. */
    // is still in the sandbox, this address must be verified.
    static final String TO = "innovatters@gmail.com";
    // Replace smtp_username with your Amazon SES SMTP user name.
   // static final String SMTP_USERNAME = "AKIAW53HZKAAZLUXWQBW";
   // static final String SMTP_USERNAME = "postmaster@mg.leadaconvert.com";
   /** The Constant SMTP_USERNAME. */
    // static final String SMTP_USERNAME = "sales@doctiger.com";
    static final String SMTP_USERNAME = "leadaconvert";
    // Replace smtp_password with your Amazon SES SMTP password.welcome@leadaconvert.com 
   
    //static final String SMTP_PASSWORD = "BG7WKSAQSR/kEVrv9RMDdJUtcFWLUkd6ww5jat8tV9mM";
   // static final String SMTP_PASSWORD = "fe6279036ac466250e3aa7bc6ec28f30-f696beb4-39f2f8aa";
  /** The Constant SMTP_PASSWORD. */
    //  static final String SMTP_PASSWORD = "Z7yB9pc6qXKxYvP0";
    static final String SMTP_PASSWORD = "Ypd6Gy9AvkBfONN1lRHenQhP";
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.smtp-relay.sendinblue.com
    //static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.email-smtp.us-east-1.amazonaws.com
    //static final String HOST = "email-smtp.us-east-1.amazonaws.com";
   // static final String HOST ="smtp.mailgun.org"; 
  /** The Constant HOST. */
    //  static final String HOST ="smtp-relay.sendinblue.com"; 
    static final String HOST ="142.44.163.181"; 
    
    /** The Constant PORT. */
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    /** The Constant SUBJECT. */
    static final String SUBJECT = "Mail Sender Api (SMTP interface accessed using Java)";

    /** The Constant BODY. */
    static final String BODY = String.join(
    	    System.getProperty("line.separator"),
    	    "<p><small><a href='https://bizlem.io:8088/apirest/appredirecturl?uri=https://bizlem.io:8088/apirest/appunsubscribeurl?EMAIL=mohit.raj@bizlem.io&utm_source=Gulf_HR_explore_1'>Unsubscribe</a></small></p>",
    	    "<p> mohit raj</p>",
    	    "<p><strong>Greetings of the Day!</strong></p>",
    	    "<p>As an HR Leader, you would appreciate how important is a crisp, well formatted and accurate communication is. The communication such as offer letters, hiring contract, appraisal letters and other HR Documents create lasting impression on the employees about the company.</p>",
    	    "<p>We invite you to please visit our platform and see how all the documents and contracts can be transformed using our smart documents. We would love to get on a call and demonstrate how complete transformation can be achieved in weeks.</p>",
    	    "<p>Please click on the link below to visit our platform</p>",
"<p><a href='https://bizlem.io:8088/apirest/appredirecturl?uri=https://doctiger.com/doctiger-for-hr/?EMAIL=mohit.raj@bizlem.io&utm_source=Gulf_HR_explore_1'>doctiger.com</a></p>",
"<p>Thanking you in advance,</p>",
"<p><strong>Rahul</strong></p>",
"<p>Doctiger Sales Management Team</p>",
"<p><a href='https://bizlem.io:8088/apirest/appredirecturl?uri=mailto:sales@doctiger.com?EMAIL=mohit.raj@bizlem.io&utm_source=Gulf_HR_explore_1'>sales@doctiger.com</a></p>",
    	"<p>+1&nbsp;&nbsp;315 636 3128</p>",
    	"<p>+44&nbsp;&nbsp;2380971006</p>",
    	"<p>+91 7506272969</p>"
    		);

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
        msg.setReplyTo(new InternetAddress[] 
      	      {new InternetAddress(REPLYTO)});
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
       // msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending..."+msg.toString());
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }} 
