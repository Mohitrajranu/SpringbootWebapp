package com.restfulservice.util;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
// TODO: Auto-generated Javadoc

/**
 * The Class MailFetch.
 */
public class MailFetch {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args )  {
		try {
			String dateStr = "Thu Dec 12 15:42:06 IST 2019";
			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
			Date pastDate = (Date)formatter.parse(dateStr);
			System.out.println(pastDate.toString());
			//readGodaddyMails(pastDate,true,"welcome@leadaconvert.com", "welcome@2019");
			//readGmailMails(pastDate,false,"sales@doctiger.com", "doctiger123");
			readGodaddyMails(pastDate,false,"sales@leadautoconvert.com", "Sales@leadautoconvert@1");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
	
    /**
     * Read gmail mails.
     *
     * @param pastDate the past date
     * @param mailFlag the mail flag
     * @param username the username
     * @param password the password
     */
    public static void readGmailMails(Date pastDate,boolean mailFlag,String username,String password){

		System.out.println("Inside MailReader()...");
		   Folder inbox = null;
			Store store = null;

	        /* Set the mail properties */

	        
	        
	        Properties properties = new Properties();

			properties.setProperty("mail.imap.ssl.enable", "true");
			properties.put("mail.imap.host", "imap.gmail.com");
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.auth.plain.disable", true);
			properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.imap.socketFactory.fallback", "false");
			properties.setProperty("mail.imap.socketFactory.port",String.valueOf("993"));
	        //pop.gmail.com pop.secureserver.net
        try {
		//	Session session = Session.getDefaultInstance(new Properties( )); no-reply@sendgrid.net mailer-daemon@secureserver.net
			Session session = Session.getDefaultInstance(properties);
			store = session.getStore("imap");
			store.connect(username, password);
		    /*store = session.getStore("pop3");
		    //Sales@leadautoconvert@1 sales@leadautoconvert.com
		    store.connect("pop.gmail.com", 995, "sales@doctiger.com", "doctiger123");*/
		  //  store.connect("pop.secureserver.net", 995, "sales@leadautoconvert.com", "Sales@leadautoconvert@1");
			//store.connect("pop.secureserver.net", 995, "welcome@leadaconvert.com", "welcome@2019");
			 inbox = store.getFolder( "INBOX" );
			inbox.open( Folder.READ_WRITE );
			SearchTerm term = null;
			Calendar cal = null;
			cal = Calendar.getInstance();
			Date minDate = new Date(cal.getTimeInMillis());   //get today date
			cal.add(Calendar.DAY_OF_MONTH, 1);                //add 1 day
			Date maxDate = new Date(cal.getTimeInMillis());   //get tomorrow date
			ReceivedDateTerm minDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, minDate);
			ReceivedDateTerm maxDateTerm = new ReceivedDateTerm(ComparisonTerm.LE, maxDate);

			term = new AndTerm(term, minDateTerm);            //concat the search terms
			term = new AndTerm(term, maxDateTerm);
			/*
			Calendar cal = Calendar.getInstance();
           cal.add(Calendar.DAY_OF_MONTH, -1);

          // We would get the bounce mails received yesterday

           ReceivedDateTerm term  = new ReceivedDateTerm(ComparisonTerm.EQ,new Date(cal.getTimeInMillis()));
			*/
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			SearchTerm sender1 = new FromTerm(new InternetAddress("no-reply@sendgrid.net"));
			SearchTerm searchTerm = new AndTerm(unseenFlagTerm,sender1);
			// Fetch unseen messages from inbox folder
			Message[] messages = inbox.search(unseenFlagTerm);
			System.out.println("Message length found is "+messages.length);
			// Sort messages from recent to oldest
			/*Arrays.sort( messages, ( m1, m2 ) -> {
			  try {
			    return m2.getSentDate().compareTo( m1.getSentDate() );
			  } catch ( MessagingException e ) {
			    throw new RuntimeException( e );
			  }
			} );*/
			System.out.println("Min date is "+minDate+ " and max date set is "+maxDate);
			for ( Message message : messages ) {
				 parseMailContent(pastDate, maxDate, message,mailFlag); 
			 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inbox.close(false);
				store.close();
				inbox = null;
				store = null;
			} catch (Exception e) {
				inbox = null;
				store = null;
				e.printStackTrace();
			}
		}
	
    }
	
	/**
	 * Parses the mail content.
	 *
	 * @param pastDate the past date
	 * @param maxDate the max date
	 * @param message the message
	 * @param forwardMails the forward mails
	 */
	private static void parseMailContent(Date pastDate, Date maxDate, Message message,boolean forwardMails)
			{
		try {
			if (message.getSentDate().after(pastDate) && message.getSentDate().before(maxDate))
			   {
				 System.out.println("Min date is "+pastDate+ " and max date set is "+maxDate);
				 
				 String messagestr = null;
				 switch(message.getSubject().replaceAll("Fwd: ", ""))
				 {
				 
				 case "New Subscriber": 
					 System.out.println("From: " + message.getFrom()[0]);
					  System.out.println("To: " + message.getAllRecipients()[0]);
					  messagestr= parseMessageContent(message);
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " case1 subject:" + message.getSubject() );
					  Document doc = Jsoup.parse(messagestr);
						Elements links = doc.select("p");
						for (Element link : links) {
					          if((!("Dear admin,".contentEquals(new StringBuffer(link.text().trim())))) && (!("Doctiger.".contentEquals(new StringBuffer(link.text().trim()))))){
					        	 String emailstr []= link.text().trim().split("\\s+", -1);
					        	  System.out.println(emailstr[0]); 
					        	  break;
					          }
					      }
					  break;
				
					  
				 case "New Contact Request":
					 System.out.println("From: " + message.getFrom()[0]);
					  System.out.println("To: " + message.getAllRecipients()[0]);
					  messagestr= parseMessageContent(message);
					  Document docContact = Jsoup.parse(messagestr);
					  Elements conlinks = docContact.select("div");
						
						for (Element link : conlinks) {
							StringBuilder linkText = new StringBuilder();
					        linkText.append(link.text().trim());
					      if(linkText.toString().startsWith("Email")){
					    	  String emailstr []= linkText.toString().split("\\s+", -1);
				        	  System.out.println(emailstr[1]); 
				        	  break;
					      } }
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " case2 subject:" + message.getSubject() );
					  break;
				 case "[doctiger_crm] Please moderate: \"Contracts in the Digital Economy: Smart Contracts\"":
					 System.out.println("From: " + message.getFrom()[0]);
					  System.out.println("To: " + message.getAllRecipients()[0]);
					  messagestr= parseMessageContent(message);
					  Document docCrm = Jsoup.parse(messagestr);
						Elements linksCrm = docCrm.select("a");
						int count = 0;
						for (Element link : linksCrm) {
							count++;
							if(count == 2){
							String linkText = link.text(); // "example""
							System.out.println("The field is "+linkText+" count is "+count);
							break;
							}
							
						}
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " case3 subject:" + message.getSubject() );
					  break;
				 case "New booking":
					 System.out.println("From: " + message.getFrom()[0]);
					  System.out.println("To: " + message.getAllRecipients()[0]);
					  messagestr= parseMessageContent(message);
					  Document docbook = Jsoup.parse(messagestr);
					  Elements linksbook = docbook.select("a");
						
						for (Element link : linksbook) {
							String linkText = link.text(); // "example""
							System.out.println(linkText);
							break;
						}
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " case4 subject:" + message.getSubject() );
					  break;
				 case "Your FreeTrial Account has been Created":
					 System.out.println("From: " + message.getFrom()[0]);
					  
					  InternetAddress address = (InternetAddress) message.getAllRecipients()[0];
					  String person = address.getPersonal();

					    if (person != null) {
					        person = MimeUtility.decodeText(person) + " ";
					    } else {
					        person = "";
					    }
					   // StringBuilder firstname = new StringBuilder(person);
					  String linkText = address.getAddress(); // "example""
						int pos = linkText.indexOf( '@' );
		        	     
		        	     // Isolate the domain/machine name and get a list of mail exchangers
						StringBuilder firstname = new StringBuilder( linkText.substring( 0,pos ));
		        	     StringBuilder domain = new StringBuilder( linkText.substring( ++pos ));
		        	     System.out.println("To: " + firstname.toString());
		        	     System.out.println("Domain: " + domain.toString());
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " case5 subject:" + message.getSubject() );
					  break;
				 //Your FreeTrial Account has been Created,New booking,[doctiger_crm] Please moderate: "Contracts in the Digital Economy: Smart Contracts"
				 default:
					 System.out.println("From: " + message.getFrom()[0]);
					  System.out.println("To: " + message.getAllRecipients()[0]);
					  messagestr= parseMessageContent(message);
					  List<String> recpients= new ArrayList<>();
			    	   recpients.add("sales@doctiger.com");
			    	   
			    	   if(forwardMails){
			    	   EmailHelper.sendMailViaGodaddy("welcome@leadaconvert.com","welcome@2019",recpients,message.getSubject(),messagestr);
			    	   }
					  System.out.println( 
						      "sendDate: " + message.getSentDate()
						      + " default subject:" + message.getSubject() );
					  break;
				 }
					  
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
	/**
	 * Read godaddy mails.
	 *
	 * @param pastDate the past date
	 * @param mailFlag the mail flag
	 * @param username the username
	 * @param password the password
	 */
	public static void readGodaddyMails(Date pastDate,boolean mailFlag,String username,String password) {
		System.out.println("Inside MailReader()...");
		   Folder inbox = null;
			Store store = null;
	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	        /* Set the mail properties */

	        Properties props = new Properties();
	        // Set manual Properties
	        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
	        props.setProperty("mail.pop3.socketFactory.fallback", "false");
	        props.setProperty("mail.pop3.port", "995");
	        props.setProperty("mail.pop3.socketFactory.port", "995");
	        props.put("mail.pop3.host", "pop.secureserver.net");
	        
	        
	        
	        //pop.gmail.com pop.secureserver.net
        try {
		//	Session session = Session.getDefaultInstance(new Properties( )); no-reply@sendgrid.net mailer-daemon@secureserver.net
			Session session = Session.getDefaultInstance(props);
			
		    store = session.getStore("pop3");
		    //Sales@leadautoconvert@1 sales@leadautoconvert.com
		   store.connect("pop.secureserver.net", 995, username, password);
			//store.connect("pop.secureserver.net", 995, "welcome@leadaconvert.com", "welcome@2019");
			 inbox = store.getFolder( "INBOX" );
			inbox.open( Folder.READ_WRITE );
			SearchTerm term = null;
			Calendar cal = null;
			cal = Calendar.getInstance();
			Date minDate = new Date(cal.getTimeInMillis());   //get today date
			cal.add(Calendar.DAY_OF_MONTH, 1);                //add 1 day
			Date maxDate = new Date(cal.getTimeInMillis());   //get tomorrow date
			ReceivedDateTerm minDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, minDate);
			ReceivedDateTerm maxDateTerm = new ReceivedDateTerm(ComparisonTerm.LE, maxDate);

			term = new AndTerm(term, minDateTerm);            //concat the search terms
			term = new AndTerm(term, maxDateTerm);
			/*
			Calendar cal = Calendar.getInstance();
           cal.add(Calendar.DAY_OF_MONTH, -1);

          // We would get the bounce mails received yesterday

           ReceivedDateTerm term  = new ReceivedDateTerm(ComparisonTerm.EQ,new Date(cal.getTimeInMillis()));
			*/
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			SearchTerm sender1 = new FromTerm(new InternetAddress("no-reply@sendgrid.net"));
			SearchTerm searchTerm = new AndTerm(unseenFlagTerm,sender1);
			// Fetch unseen messages from inbox folder
			Message[] messages = inbox.search(unseenFlagTerm);
			System.out.println("Message length found is "+messages.length);
			// Sort messages from recent to oldest
			/*Arrays.sort( messages, ( m1, m2 ) -> {
			  try {
			    return m2.getSentDate().compareTo( m1.getSentDate() );
			  } catch ( MessagingException e ) {
			    throw new RuntimeException( e );
			  }
			} );*/
			System.out.println("Min date is "+minDate+ " and max date set is "+maxDate);
			for ( Message message : messages ) {
				 parseMailContent(pastDate, maxDate, message,mailFlag); 
			 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inbox.close(false);
				store.close();
				inbox = null;
				store = null;
			} catch (Exception e) {
				inbox = null;
				store = null;
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Parses the message content.
	 *
	 * @param message the message
	 * @return the string
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String parseMessageContent(Message message) throws MessagingException, IOException {
		String messageContent = "";
		try {
			String contentType = message.getContentType().toString();
//	String s = null;
			
			//  message.setFlag(Flags.Flag.SEEN, true);
				if (contentType.contains("multipart")) {
					// content may contain attachments
					System.out.println("Inside multipart attachment ");
					Multipart multiPart = (Multipart) message.getContent();
				//	s = getTextFromMessage(message);
					int numberOfParts = multiPart.getCount();
					//System.out.println("Provided partcount is "+numberOfParts);
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						//System.out.println("Attached Body Part is ::"+part.getDisposition());
						
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) || Part.ATTACHMENT.equalsIgnoreCase("attachments")) {
							
							System.out.println("Inside Attachment Part");
							
						}
						else if(part.isMimeType("text/html") && BizUtil.isNullString(part.getDisposition())) {
							// this part may be the message content
							messageContent = part.getContent().toString();
							
							System.out.println("Inside else content Type");
						}
					}
					/*if (attachFiles.length() > 1) {
						attachFiles = attachFiles.substring(0, attachFiles.length() - 1);
						System.out.println("Files being sent in the attachment are "+attachFiles);
					//	sendMail(attachFiles,"pricingchbiz@bizlem.com","FWD",subject);
					}*/
					/*MongoGetCall.insertIntoMongo(subject, "mohit.raj@bizlem.com", s);
					String res=MongoGetCall.retrieveFmemail("mohit.raj@bizlem.com", subject);
					System.out.println("Get trailmail :: "+res);*/
				}  
				else if (contentType.contains("text/plain")
						|| contentType.contains("text/html")) {
					Object content = message.getContent();
					System.out.println("Inside Outer text or html part ");
					if (content != null) {
						messageContent = content.toString();
						//System.out.println("Inside Outer text or html part "+messageContent);
						/*MongoGetCall.insertIntoMongo(subject, "mohit.raj@bizlem.com", messageContent);*/
								
					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageContent;
	}
	
	/**
	 * Gets the text from message.
	 *
	 * @param message the message
	 * @return the text from message
	 * @throws Exception the exception
	 */
	private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        } else {
            Object content = message.getContent();
            result = content.toString();
        }

        return result;
    }

    
    /**
     * Extracts the text content of a multipart email message.
     *
     * @param mimeMultipart the mime multipart
     * @return the text from mime multipart
     * @throws Exception the exception
     */
    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		String result = "";
		int partCount = mimeMultipart.getCount();
		System.out.println("MultipartCount returned is "+partCount);
		for (int i = 0; i < partCount; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			/*if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else*/ 
			if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				//System.out.println("getTextFromMimeMultipart()  "+html);
				// result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
				result = html;
				break;
			}else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
				//System.out.println("getTextFromMimeMultipart()  "+result);
			}
		}
		return result;
	}
    
}
