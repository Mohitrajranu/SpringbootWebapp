package com.restfulservice.model;

import java.util.List;
import java.util.Map;

public class Mailer {

	 private String mailFrom;
	    
	    private String mailTo;
	 
	    private String mailCc;
	 
	    private String mailBcc;
	 
	    private String mailSubject;
	 
	    private String mailContent;
	    
	    private String link;
	 
	    private String contentType;
	 
	    private List < Object > attachments;
	 
	    private Map < String, Object > model;
	 
	    public Mailer() {
	        contentType = "text/plain";
	    }
	    // getter and setter

		public String getMailFrom() {
			return mailFrom;
		}

		public void setMailFrom(String mailFrom) {
			this.mailFrom = mailFrom;
		}

		public String getMailTo() {
			return mailTo;
		}

		public void setMailTo(String mailTo) {
			this.mailTo = mailTo;
		}

		public String getMailCc() {
			return mailCc;
		}

		public void setMailCc(String mailCc) {
			this.mailCc = mailCc;
		}

		public String getMailBcc() {
			return mailBcc;
		}

		public void setMailBcc(String mailBcc) {
			this.mailBcc = mailBcc;
		}

		public String getMailSubject() {
			return mailSubject;
		}

		public void setMailSubject(String mailSubject) {
			this.mailSubject = mailSubject;
		}

		public String getMailContent() {
			return mailContent;
		}

		public void setMailContent(String mailContent) {
			this.mailContent = mailContent;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		
		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public List<Object> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<Object> attachments) {
			this.attachments = attachments;
		}

		public Map<String, Object> getModel() {
			return model;
		}

		public void setModel(Map<String, Object> model) {
			this.model = model;
		}
	    
}
