package org.al.priv.ce.messages.envelopes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessageEnvelopeResponse {

	@JsonProperty
	private Long messageId;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
