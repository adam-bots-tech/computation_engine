package org.al.priv.ce.messages.envelopes;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadMessageEnvelopeMetaData implements Serializable {

	private static final long serialVersionUID = -4997593898835568423L;

	@JsonProperty
	private String originalSource;
	
	@JsonProperty
	private DateTime dateProcessed;
	
	@JsonProperty
	private DateTime dateSent;
	
	@JsonProperty
	private Long messageId;
	
	public PayloadMessageEnvelopeMetaData() {
		
	}
	
	public PayloadMessageEnvelopeMetaData(String originalSource, Long messageId) {
		this.originalSource = originalSource;
		this.messageId = messageId;
		this.dateProcessed = DateTime.now();
	}
	
	public void updateOnSent() {
		this.dateSent = DateTime.now();
	}

	public String getOriginalSource() {
		return originalSource;
	}

	public void setOriginalSource(String originalSource) {
		this.originalSource = originalSource;
	}

	public DateTime getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(DateTime dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public DateTime getDateSent() {
		return dateSent;
	}

	public void setDateSent(DateTime dateSent) {
		this.dateSent = dateSent;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
