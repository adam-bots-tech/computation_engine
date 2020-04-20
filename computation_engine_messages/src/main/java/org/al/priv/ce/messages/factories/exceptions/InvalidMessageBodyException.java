package org.al.priv.ce.messages.factories.exceptions;

public class InvalidMessageBodyException extends Exception {

	private static final long serialVersionUID = -1706264295290685284L;
	
	public InvalidMessageBodyException(String message, Throwable ex) {
		super(message, ex);
	}

}
