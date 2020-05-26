package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.al.priv.ce.messages.AbstractRequestMessage;
import org.al.priv.ce.messages.enums.MessageTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessageEnvelope implements Serializable{

	private static final long serialVersionUID = -8093792770757932376L;
	
	@JsonProperty
	private MessageTypes type;
	
	@JsonProperty
	private RequestMessageEnvelopeMetaData metaData;
	
	@JsonProperty
	private AbstractRequestMessage message;

	public MessageTypes getType() {
		return type;
	}

	public void setType(MessageTypes type) {
		this.type = type;
	}

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
