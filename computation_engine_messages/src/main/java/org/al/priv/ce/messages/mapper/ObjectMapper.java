package org.al.priv.ce.messages.mapper;

import com.fasterxml.jackson.datatype.joda.JodaModule;

public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper
{
	private static final long serialVersionUID = 1418170872623238400L;
	
	public ObjectMapper() {
		super();
		this.registerModule(new JodaModule());
	}

}
