package com.restfulservice.model;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Mailer.
 * @author Mohit Raj
 */
public class Mailer {

	 /** The mail from. */
 	private String mailFrom;
	    
	    /** The mail to. */
    	private String mailTo;
	 
	    /** The mail cc. */
    	private String mailCc;
	 
	    /** The mail bcc. */
    	private String mailBcc;
	 
	    /** The mail subject. */
    	private String mailSubject;
	 
	    /** The mail content. */
    	private String mailContent;
	    
	    /** The link. */
    	private String link;
	 
	    /** The content type. */
    	private String contentType;
	 
	    /** The attachments. */
    	private List < Object > attachments;
	 
	    /** The model. */
    	private Map < String, Object > model;
	 
	    /**
    	 * Instantiates a new mailer.
    	 */
    	public Mailer() {
	        contentType = "text/plain";
	    }
	    // getter and setter

		/**
    	 * Gets the mail from.
    	 *
    	 * @return the mail from
    	 */
    	public String getMailFrom() {
			return mailFrom;
		}

		/**
		 * Sets the mail from.
		 *
		 * @param mailFrom the new mail from
		 */
		public void setMailFrom(String mailFrom) {
			this.mailFrom = mailFrom;
		}

		/**
		 * Gets the mail to.
		 *
		 * @return the mail to
		 */
		public String getMailTo() {
			return mailTo;
		}

		/**
		 * Sets the mail to.
		 *
		 * @param mailTo the new mail to
		 */
		public void setMailTo(String mailTo) {
			this.mailTo = mailTo;
		}

		/**
		 * Gets the mail cc.
		 *
		 * @return the mail cc
		 */
		public String getMailCc() {
			return mailCc;
		}

		/**
		 * Sets the mail cc.
		 *
		 * @param mailCc the new mail cc
		 */
		public void setMailCc(String mailCc) {
			this.mailCc = mailCc;
		}

		/**
		 * Gets the mail bcc.
		 *
		 * @return the mail bcc
		 */
		public String getMailBcc() {
			return mailBcc;
		}

		/**
		 * Sets the mail bcc.
		 *
		 * @param mailBcc the new mail bcc
		 */
		public void setMailBcc(String mailBcc) {
			this.mailBcc = mailBcc;
		}

		/**
		 * Gets the mail subject.
		 *
		 * @return the mail subject
		 */
		public String getMailSubject() {
			return mailSubject;
		}

		/**
		 * Sets the mail subject.
		 *
		 * @param mailSubject the new mail subject
		 */
		public void setMailSubject(String mailSubject) {
			this.mailSubject = mailSubject;
		}

		/**
		 * Gets the mail content.
		 *
		 * @return the mail content
		 */
		public String getMailContent() {
			return mailContent;
		}

		/**
		 * Sets the mail content.
		 *
		 * @param mailContent the new mail content
		 */
		public void setMailContent(String mailContent) {
			this.mailContent = mailContent;
		}

		/**
		 * Gets the content type.
		 *
		 * @return the content type
		 */
		public String getContentType() {
			return contentType;
		}

		/**
		 * Sets the content type.
		 *
		 * @param contentType the new content type
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		
		/**
		 * Gets the link.
		 *
		 * @return the link
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Sets the link.
		 *
		 * @param link the new link
		 */
		public void setLink(String link) {
			this.link = link;
		}

		/**
		 * Gets the attachments.
		 *
		 * @return the attachments
		 */
		public List<Object> getAttachments() {
			return attachments;
		}

		/**
		 * Sets the attachments.
		 *
		 * @param attachments the new attachments
		 */
		public void setAttachments(List<Object> attachments) {
			this.attachments = attachments;
		}

		/**
		 * Gets the model.
		 *
		 * @return the model
		 */
		public Map<String, Object> getModel() {
			return model;
		}

		/**
		 * Sets the model.
		 *
		 * @param model the model
		 */
		public void setModel(Map<String, Object> model) {
			this.model = model;
		}
	    
}
