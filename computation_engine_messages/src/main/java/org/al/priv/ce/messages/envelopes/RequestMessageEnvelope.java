package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.al.priv.ce.messages.types.RequestType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessageEnvelope implements Serializable{

	private static final long serialVersionUID = -8093792770757932376L;

	@JsonProperty
	private RequestType type;
	
	@JsonProperty
	private RequestMessageEnvelopeMetaData metaData;
	
	@JsonProperty
	private String message;

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public RequestMessageEnvelopeMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(RequestMessageEnvelopeMetaData metaData) {
		this.metaData = metaData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
