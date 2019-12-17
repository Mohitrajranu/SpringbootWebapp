package com.restfulservice.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO: Auto-generated Javadoc
/**
 * The Class ParseHtml.
 */
public class ParseHtml {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		
		//String htmlnew ="<!doctype html><html xmlns=\"http://www.w3.org/1999/xhtml\"dir=\"ltr\" lang=\"en-US\"><head><title>Doctiger - New Subscriber</title></head><body><p>Dear admin,</p><p>rahul.gairola@bizlem.com has subcribed to your newsletter</p><p>Doctiger.</p></body></html>";
		String htmlnew ="<p>Hello Admin,<br />You have received a new submission. Here are the details:</p>"
				+ "<div>First Name*: test</div><p></p><div>Last Name*: ette</div><p></p>"
				+ "<div>Email*: rahul.gairola@bizlem.com</div><p></p><div>Phone*: ghdqhg</div><p></p>"
				+ "<div>Company: `hjkjan</div><p></p><div>Query: </div><p></p>";
		
		String htmlBooking="<div dir=\"ltr\"><div class=\"gmail_quote\">"
				+ "<div style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%\">"
				+ "<div style=\"table-layout:fixed;Margin:0;padding-top:10px;padding-bottom:10px;padding-right:10px;padding-left:10px\" dir=\"ltr\">"
				+ "<p><br>You need to approve a new booking  for: November 8, 2019<br><br>Person detail information:<br></p>"
				+ "<div style=\"text-align:left;word-wrap:break-word\">"
				+ "<strong>First Name</strong>: <span>Priyabrata</span><br>"
				+ "<strong>Last Name</strong>: <span>sarangi</span><br>"
				+ "<strong>Email</strong>: <span><a href=\"mailto:psarangi@hngil.com\" target=\"_blank\">psarangi@hngil.com</a></span><br>"
				+ "</div><br><br>Currently a new booking is waiting for approval. Please visit the moderation panel";
String crm="<div dir=\"ltr\"><br><div class=\"gmail_quote\"><div dir=\"ltr\" class=\"gmail_attr\">A new comment on the post &quot;Contracts in the Digital Economy: Smart Contracts&quot; is waiting for your approval<br></div>"
+"<a href=\"https://doctiger.com/contracts-in-the-digital-economy-smart-contracts/\" rel=\"noreferrer\" target=\"_blank\">https://doctiger.com/contracts-in-the-digital-economy-smart-contracts/</a><br>"
+"<br>Author: vurtilopmer (IP address: 148.163.70.23, .)<br>"
+"Email: <a href=\"mailto:Winterbottom13812@gmail.com\" target=\"_blank\">Winterbottom13812@gmail.com</a><br>"
+"URL: <a href=\"http://www.vurtilopmer.com/\" rel=\"noreferrer\" target=\"_blank\">http://www.vurtilopmer.com/</a><br>"
+"Comment: <br>you are in reality a just right webmaster. The web site loading speed is amazing. It sort of feels that you&#39;re doing any distinctive trick. In addition, The contents are masterpiece. you have done a fantastic activity on this subject!<br>"
+"<br>Approve it: <a href=\"https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content\" rel=\"noreferrer\" target=\"_blank\">https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content</a><br>"
+"Trash it: <a href=\"https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content\" rel=\"noreferrer\" target=\"_blank\">https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content</a><br>"
+"Spam it: <a href=\"https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content\" rel=\"noreferrer\" target=\"_blank\">https://doctiger.com/wp-admin/comment.php?action=approve&amp;c=19#wpbody-content</a><br>"
+"Currently 6 comments are waiting for approval. Please visit the moderation panel:<br>"
+"<a href=\"https://doctiger.com/wp-admin/edit-comments.php?comment_status=moderated#wpbody-content\" rel=\"noreferrer\" target=\"_blank\">https://doctiger.com/wp-admin/edit-comments.php?comment_status=moderated#wpbody-content</a><br>"
+"<br></div></div>";

		Document doc = Jsoup.parse(crm);
		//doc.select("p").forEach(System.out::println);Email*: 
		Elements links = doc.select("a");
		int count = 0;
		for (Element link : links) {
			count++;
			if(count == 2){
			String linkText = link.text(); // "example""
			System.out.println("The field is "+linkText+" count is "+count);
			break;
			}
			
		}
			/*StringBuilder linkText = new StringBuilder();
	        linkText.append(link.text().trim());
	      if(linkText.toString().startsWith("Email")){
	    	  String emailstr []= linkText.toString().split("\\s+", -1);
        	  System.out.println(emailstr[1]); 
        	  break;
	      }*/ 
	          /*if((!("Dear admin,".contentEquals(new StringBuffer(link.text().trim())))) && (!("Doctiger.".contentEquals(new StringBuffer(link.text().trim()))))){
	        	 String emailstr []= link.text().trim().split("\\s+", -1);
	        	  System.out.println(emailstr[0]); 
	          }*/
	     
		/*Document doc = Jsoup.parse(html);
		Element link = doc.select("a").first();

		String text = doc.body().text(); // "An example link"
		String linkHref = link.attr("href"); // "http://example.com/"
		String linkText = link.text(); // "example""

		String linkOuterH = link.outerHtml(); 
		    // "<a href="http://example.com"><b>example</b></a>"
		String linkInnerH = link.html(); // "<b>example</b>"
*/
	}

}
