package org.al.priv.ce.endpoint.exceptions;

public class EndpointException extends Exception{
	
	private static final long serialVersionUID = 152664291531523542L;

	public EndpointException(String exceptionMessage, Throwable ex) {
		super(exceptionMessage, ex);
	}
}
