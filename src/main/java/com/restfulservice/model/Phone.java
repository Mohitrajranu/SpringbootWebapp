package com.restfulservice.model;

public class Phone {

	private String receiver;
	private String msgbody;
	private String mode;
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMsgbody() {
		return msgbody;
	}
	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return String.format("Phone [receiver=%s, msgbody=%s, mode=%s]", receiver, msgbody, mode);
	}
	
	
}
