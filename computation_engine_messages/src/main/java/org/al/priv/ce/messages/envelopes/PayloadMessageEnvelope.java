package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.al.priv.ce.messages.AbstractPayloadMessage;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadMessageEnvelope implements Serializable {

	private static final long serialVersionUID = -8690457215606230807L;
	
	@JsonProperty
	private PayloadMessageEnvelopeMetaData metaData;
	
	@JsonProperty
	private AbstractPayloadMessage message;

	public PayloadMessageEnvelopeMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(PayloadMessageEnvelopeMetaData metaData) {
		this.metaData = metaData;
	}

	public AbstractPayloadMessage getMessage() {
		return message;
	}

	public void setMessage(AbstractPayloadMessage message) {
		this.message = message;
	}
}
