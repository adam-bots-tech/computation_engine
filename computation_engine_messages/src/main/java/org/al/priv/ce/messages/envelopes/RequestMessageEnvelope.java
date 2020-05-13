package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.al.priv.ce.messages.AbstractRequestMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessageEnvelope implements Serializable{

	private static final long serialVersionUID = -8093792770757932376L;

	
	@JsonProperty
	private RequestMessageEnvelopeMetaData metaData;
	
	@JsonProperty
	private AbstractRequestMessage message;

	public RequestMessageEnvelopeMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(RequestMessageEnvelopeMetaData metaData) {
		this.metaData = metaData;
	}

	public AbstractRequestMessage getMessage() {
		return message;
	}

	public void setMessage(AbstractRequestMessage message) {
		this.message = message;
	}
	
}
