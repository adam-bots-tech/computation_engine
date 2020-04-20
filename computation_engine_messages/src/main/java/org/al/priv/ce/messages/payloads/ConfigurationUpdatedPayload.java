package org.al.priv.ce.messages.payloads;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurationUpdatedPayload implements Serializable {
	
	private static final long serialVersionUID = -4698046045859411278L;
	
	@JsonProperty
	private Boolean changed;

	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
}
