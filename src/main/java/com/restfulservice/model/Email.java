package com.restfulservice.model;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Email.
 * @author Mohit Raj
 */
public class Email {

	/** The from. */
	private String from;
    
    /** The to. */
    private List<String> to;
    
    /** The cc. */
    private List<String> cc;
    
    /** The bcc. */
    private List<String> bcc;
    
    /** The subject. */
    private String subject;
    
    /** The message. */
    private String message;

    /**
     * Instantiates a new email.
     */
    public Email() {}

    /**
     * Instantiates a new email.
     *
     * @param from the from
     * @param to the to
     * @param cc the cc
     * @param bcc the bcc
     */
    public Email(String from, List<String> to, List<String> cc, List<String> bcc) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    /**
     * Instantiates a new email.
     *
     * @param from the from
     * @param to the to
     * @param cc the cc
     * @param bcc the bcc
     * @param subject the subject
     * @param message the message
     */
    public Email(String from, List<String> to, List<String> cc, List<String> bcc, String subject, String message) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
    }

    /**
     * Gets the from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets the to.
     *
     * @return the to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * Sets the to.
     *
     * @param to the new to
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * Gets the cc.
     *
     * @return the cc
     */
    public List<String> getCc() {
        return cc;
    }

    /**
     * Sets the cc.
     *
     * @param cc the new cc
     */
    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    /**
     * Gets the bcc.
     *
     * @return the bcc
     */
    public List<String> getBcc() {
        return bcc;
    }

    /**
     * Sets the bcc.
     *
     * @param bcc the new bcc
     */
    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
