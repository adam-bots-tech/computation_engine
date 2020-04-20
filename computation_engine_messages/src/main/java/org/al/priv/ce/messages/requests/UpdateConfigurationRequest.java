package org.al.priv.ce.messages.requests;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateConfigurationRequest implements Serializable {
	
	private static final long serialVersionUID = 3543403139037247823L;
	
	@JsonProperty
	private String defaultInterestRate;

	public String getDefaultInterestRate() {
		return defaultInterestRate;
	}

	public void setDefaultInterestRate(String defaultInterestRate) {
		this.defaultInterestRate = defaultInterestRate;
	}
	
}
