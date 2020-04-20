package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.al.priv.ce.messages.types.PayloadType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadMessageEnvelope implements Serializable {

	private static final long serialVersionUID = -8690457215606230807L;

	@JsonProperty
	private PayloadType type;
	
	@JsonProperty
	private PayloadMessageEnvelopeMetaData metaData;
	
	@JsonProperty
	private String message;

	public PayloadType getType() {
		return type;
	}

	public void setType(PayloadType type) {
		this.type = type;
	}

	public PayloadMessageEnvelopeMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(PayloadMessageEnvelopeMetaData metaData) {
		this.metaData = metaData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
