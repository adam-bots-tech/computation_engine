package org.al.priv.ce.worker.exceptions;

public class PayloadException extends Exception{
	
	private static final long serialVersionUID = 152664291531523542L;

	public PayloadException(String exceptionMessage, Throwable ex) {
		super(exceptionMessage, ex);
	}	
}
