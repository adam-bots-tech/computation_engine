package org.al.priv.ce.endpoint.exceptions;

public class PayloadException extends Exception{
	
	private static final long serialVersionUID = 152664291531523542L;
	private long messageId;

	public PayloadException(String exceptionMessage, Throwable ex, long messageId) {
		super(exceptionMessage, ex);
		this.messageId = messageId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	
}
