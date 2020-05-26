package org.al.priv.ce.worker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_runtime_configuration")
public class Configuration implements Serializable {

	private static final long serialVersionUID = 8266180997473840783L;

	@Id
	private String key;
	
	@Column
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
