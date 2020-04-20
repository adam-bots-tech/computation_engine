package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessageEnvelopeMetaData implements Serializable {

	private static final long serialVersionUID = -7919802821322705403L;

	@JsonProperty
	private String source;
	
	@JsonProperty
	private DateTime messageReceived;
	
	@JsonProperty 
	private Long messageId;
	
	public RequestMessageEnvelopeMetaData() {
		
	}
	
	public RequestMessageEnvelopeMetaData(String source) {
		this.source = source;
	}
	
	public void updateOnReceipt() {
		this.messageReceived = DateTime.now();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public DateTime getMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(DateTime messageReceived) {
		this.messageReceived = messageReceived;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
