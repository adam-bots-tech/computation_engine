package org.al.priv.ce.endpoint.entities;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "payloads")
public class PayloadMessageRecord implements Serializable {

	private static final long serialVersionUID = 142315452142453415L;

	@PrimaryKeyColumn(
			name = "message_id", 
			type = PrimaryKeyType.PARTITIONED, 
			ordering = Ordering.DESCENDING)
	private long messageId;
	
	@Column(value = "message")
	private String message;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
