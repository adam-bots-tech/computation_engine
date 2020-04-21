package org.al.priv.ce.messages.requests;

import java.io.Serializable;

import org.al.priv.ce.messages.AbstractRequestMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorRequest extends AbstractRequestMessage implements Serializable {

	private static final long serialVersionUID = 8722155505770605050L;

	@JsonProperty
	private String originatingComponent;
	
	@JsonProperty
	private String message;
	
	@JsonProperty
	private String stackTrace;
	
	@JsonProperty
	private String exceptionClassName;
	
	public String getOriginatingComponent() {
		return originatingComponent;
	}
	public void setOriginatingComponent(String originatingComponent) {
		this.originatingComponent = originatingComponent;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getExceptionClassName() {
		return exceptionClassName;
	}
	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}
	
	
}
