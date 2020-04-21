package org.al.priv.ce.endpoint.exceptions;

import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeMetaData;

public class RequestException extends Exception{
	
	private static final long serialVersionUID = 152664291531523542L;
	private RequestMessageEnvelopeMetaData metaData;

	public RequestException(String exceptionMessage, Throwable ex, RequestMessageEnvelopeMetaData metaData) {
		super(exceptionMessage, ex);
		this.metaData = metaData;
	}

	public RequestMessageEnvelopeMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(RequestMessageEnvelopeMetaData metaData) {
		this.metaData = metaData;
	}
}
