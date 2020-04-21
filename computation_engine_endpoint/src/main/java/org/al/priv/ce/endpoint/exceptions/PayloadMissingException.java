package org.al.priv.ce.endpoint.exceptions;

public class PayloadMissingException extends Exception{
	
	private static final long serialVersionUID = 152664291531523542L;
	private long messageId;

	public PayloadMissingException(String exceptionMessage, long messageId) {
		super(exceptionMessage);
		this.messageId = messageId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	
}
