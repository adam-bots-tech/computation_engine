package org.al.priv.ce.endpoint.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "requests")
public class RequestMessageRecord {

	@Id
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
