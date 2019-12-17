package com.restfulservice.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



// TODO: Auto-generated Javadoc
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
 * @author Mohit Raj
 *
 */
public class EmailSearcher {


	/** The folder inbox. */
	private Folder folderInbox = null;
	
	/** The store. */
	private  Store store = null;
	
	/**
	 * Search email.
	 *
	 * @param host the host
	 * @param port the port
	 * @param userName the user name
	 * @param password the password
	 */
	public void searchEmail(String host, String port, String userName,String password) {

		Properties properties = new Properties();

		properties.setProperty("mail.imap.ssl.enable", "true");
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);
		properties.put("mail.imap.auth.plain.disable", true);
		properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",String.valueOf(port));
		//properties.setProperty("mail.debug", "true");
		Session session = Session.getDefaultInstance(properties);
		String fileExt = null;
		try {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMYYYYhh");
			String time = dateFormat.format(now);
			
			store = session.getStore("imap");
			store.connect(userName, password);

			folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);
			
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			SearchTerm sender1 = new FromTerm(new InternetAddress("mohit.raj@bizlem.com"));
			SearchTerm searchTerm = new AndTerm(unseenFlagTerm,sender1);
			Message[] foundMessages = folderInbox.search(searchTerm);
			System.out.println("Message length found is "+foundMessages.length);
			for (int i = 0; i < foundMessages.length; i++) {
				
				Message message = foundMessages[i];
				System.out.println("The content returned in the body is "+message.getContent());
				
				System.out.println("The description provided is "+message.getDescription());
				String subject = message.getSubject();
				System.out.println("Found message #" + i + ": " + subject);
				// store attachment file name, separated by comma
				String attachFiles = "";
				String sentDate = message.getSentDate().toString();
				System.out.println("The description returned is"+sentDate);
				String contentType = message.getContentType().toString();
				String s = null;
				String messageContent = "";

				if (contentType.contains("multipart")) {
					// content may contain attachments
					System.out.println("Inside multipart attachment ");
					Multipart multiPart = (Multipart) message.getContent();
				//	s = getTextFromMessage(message);
					int numberOfParts = multiPart.getCount();
					System.out.println("Provided partcount is "+numberOfParts);
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						System.out.println("Attached Body Part is ::"+part.getDisposition());
						
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) || Part.ATTACHMENT.equalsIgnoreCase("attachments")) {
							// this part is attachment
							String fileName = part.getFileName();
							String filepath = "E://ExcelData/InputFile/"+ time;
							fileExt = filepath+"/"+fileName;
							attachFiles += fileExt + ", ";
							
							File file = new File(filepath);
							if (!file.exists()) {
								if (file.mkdir()) {
									//System.out.println("Directory is created!");
									part.saveFile(fileExt);
								} else {
									System.out.println("Failed to create directory!");
								}
							}else{
								part.saveFile(fileExt);
							}

						}
						else if(part.isMimeType("text/html") && BizUtil.isNullString(part.getDisposition())) {
							// this part may be the message content
							messageContent = part.getContent().toString();
							String parkedFile = null;
							// Writting the jsonObject into sample.json
							try {
								//E:\ExcelData\NotApproved parkedFile = "E://ExcelData//Approved//"+"MD_MESSAGE_"+subject.replaceAll("\\s","").replaceAll("(?i)Re: |(?i)Fwd: ", "")+System.currentTimeMillis()+".html";
								parkedFile = "E://ExcelData//NotApproved//"+"MDOffice_"+i+System.currentTimeMillis()+".html";
								FileWriter fileWriter = new FileWriter(parkedFile);

								fileWriter.write(messageContent);
								fileWriter.close();
								//generateExcel(messageContent);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("File Saved as  ::"+parkedFile);
							
							System.out.println("Inside else content Type");
						}
					}
					if (attachFiles.length() > 1) {
						attachFiles = attachFiles.substring(0, attachFiles.length() - 1);
						System.out.println("Files being sent in the attachment are "+attachFiles);
					//	sendMail(attachFiles,"pricingchbiz@bizlem.com","FWD",subject);
					}
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
						/*MongoGetCall.insertIntoMongo(subject, "mohit.raj@bizlem.com", messageContent);*/
						String parkedFile = null;
						// Writting the jsonObject into sample.json
						try {
							parkedFile = "E://ExcelData//NotApproved//"+"MD_MESSAGE_"+i+System.currentTimeMillis()+".html";
							FileWriter fileWriter = new FileWriter(parkedFile);

							fileWriter.write(messageContent);
							fileWriter.close();
							//generateExcel(messageContent);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("File Saved as  ::"+parkedFile);
						
					}
				}
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();


		}
		finally{
			try {
				folderInbox.close(false);

				store.close();
				folderInbox = null;
				store = null;
			} catch (Exception e) {
				folderInbox = null;
				store = null;
				e.printStackTrace();
			}

		}
	}

	/**
	 * Send mail.
	 *
	 * @param filename the filename
	 * @param to the to
	 * @param messageContent the message content
	 * @param sub the sub
	 */
	public void sendMail(String filename,String to,String messageContent,String sub){

		// Recipient's email ID needs to be mentioned.

		// Sender's email ID needs to be mentioned
		String from = "mexecutivebiz@bizlem.com";

		final String username = "mexecutivebiz@bizlem.com";//change accordingly
		final String password = "me@12345";//change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(sub);

			// Create the message part
			//MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message "text/html;charset=gb2312"
			//messageBodyPart.setText(messageContent);
			//messageBodyPart.setContent(messageContent, "text/html;charset=gb2312");
			
			// Create a multipar message
			//Multipart multipart = new MimeMultipart();

			// Set text message part
			//multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			
			 String []arr = filename.split("\\,");
			 Multipart multipart = new MimeMultipart("mixed");
			 //multipart.addBodyPart(messageBodyPart);
			 for (String file:arr) {
				 int index = file.lastIndexOf('/');
				 String fileName = file.substring(index + 1);
			     MimeBodyPart messageBodyPart = new MimeBodyPart();
			     
			     DataSource source = new FileDataSource(file);
			     messageBodyPart.setDataHandler(new DataHandler(source));
			     messageBodyPart.setFileName(fileName);
			     multipart.addBodyPart(messageBodyPart);
			 }
			
			
			 message.setContent(multipart);
			

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

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
	    
    	/**
    	 * Generate excel.
    	 *
    	 * @param htmlData the html data
    	 */
    	public static void generateExcel(String htmlData) {
			/*String htmlData = "<html><head><title>Jsoup html parse table</title></head><body><table class=tableData border=0><tr>"
					+ "<th>Sr.No.</th><th>Student Name</th><th>City</th><th>Phone No</th></tr><tr><td>1</td><td>Dixit</td>"
					+ "<td>Ahmedabad</td><td>9825098025</td></tr><tr><td>1</td><td>Saharsh</td><td>Ahmedabad</td><td>9825098015</td></tr></table>"
					+ "</body></html>";*/
			
					String fileName = "E://ExcelData//InputFile//SampleExcel_" +System.currentTimeMillis();
					// Create a Workbook
					Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

					/* CreationHelper helps us create instances of various things like DataFormat, 
					   Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
					CreationHelper createHelper = workbook.getCreationHelper();

					// Create a Sheet
					Sheet sheet = workbook.createSheet("HtmlTableData");

					//Set Header Font
					// Create a Font for styling header cells
					Font headerFont = workbook.createFont();
					headerFont.setBold(true);
					headerFont.setFontHeightInPoints((short) 14);
					headerFont.setColor(IndexedColors.BLACK.getIndex());

					// Create a CellStyle with the font
					CellStyle headerCellStyle = workbook.createCellStyle();
					headerCellStyle.setFont(headerFont);
					headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
					headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
					headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
					headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
					
					int rowCount = 0;
					Row header;

					/*
					Folllowing code parse html table
					*/
					Document doc = Jsoup.parse(htmlData);

					/* Display list of headers for

					tag here i tried to fetch data with class = tableData in table tag
					you can fetch using id or other attribute
					rowCount variable to create row for excel sheet
					*/
					Cell cell;
					for (Element table : doc.select("table")) {
					rowCount++;
					// loop through all tr of table
					/*Elements rows = table.select("tr");
					 for (int i = 1; i < rows.size(); i++) { 
					    Element row = rows.get(i);*/
				for (Element row : table.select("tr")) {
					// create row for each
				//	row.h
					if(row.childNodeSize() <= 0){
						continue;
					}
					header = sheet.createRow(rowCount);
					// loop through all
					
					Elements ths = row.select("th");
					int count = 0;
					
					for (Element element : ths) {
					// set header style
					cell = header.createCell(count);
					cell.setCellValue(element.text());
					 cell.setCellStyle(headerCellStyle);
					count++;
					}
					// now loop through all td tag Elements tds = row.select("td:not([rowspan])");
					Elements tds = row.select("td:not([colspan])");
					count = 0;
					for (Element element : tds) {
					// create cell for each
					
					cell = header.createCell(count);
					cell.setCellValue(element.text());
				    
					cell.setCellStyle(headerCellStyle);
					count++;
					}
					rowCount++;
					// set auto size column for excel sheet
					sheet = workbook.getSheetAt(0);
					for (int j = 0; j < row.select("th").size(); j++) {
					sheet.autoSizeColumn(j);
					}
					}

					}
					
					try {
						FileOutputStream fileOut = new FileOutputStream(fileName+".xlsx");
						workbook.write(fileOut);
						fileOut.close();

				        // Closing the workbook
						workbook.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){

		String host = "imap.gmail.com";
		String port = "993";
		String userName = "mexecutivebiz@bizlem.com";
		String password = "me@12345";
		EmailSearcher searcher = new EmailSearcher();

		searcher.searchEmail(host, port, userName, password);
	}
}