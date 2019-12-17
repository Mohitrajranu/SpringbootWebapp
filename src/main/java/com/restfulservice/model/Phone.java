package com.restfulservice.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Phone.
 * @author Mohit Raj
 */
public class Phone {

	/** The receiver. */
	private String receiver;
	
	/** The msgbody. */
	private String msgbody;
	
	/** The mode. */
	private String mode;
	
	/**
	 * Gets the receiver.
	 *
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	
	/**
	 * Sets the receiver.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Gets the msgbody.
	 *
	 * @return the msgbody
	 */
	public String getMsgbody() {
		return msgbody;
	}
	
	/**
	 * Sets the msgbody.
	 *
	 * @param msgbody the new msgbody
	 */
	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}
	
	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Phone [receiver=%s, msgbody=%s, mode=%s]", receiver, msgbody, mode);
	}
	
	
}
