package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload;
import org.al.priv.ce.messages.payloads.ErrorPayload;
import org.al.priv.ce.messages.types.PayloadType;
import org.springframework.stereotype.Component;

@Component
public class PayloadMessageFactory {

	public AbstractPayloadMessage build(PayloadType type, String message) 
			throws InvalidTypeException, InvalidMessageBodyException {
		
		Class<?> clazz = this.determineMessageClass(type);
		
		if (clazz == null) 
			throw new InvalidTypeException("Unknown payload message type: " + type);
		
		if (clazz.getSuperclass() == null || !clazz.getSuperclass().getCanonicalName().equals(
				AbstractPayloadMessage.class.getCanonicalName())) 
			throw new InvalidTypeException("Class for message type '" + type + 
					"' does not extend the super class AbstractPayloadMessage");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return (AbstractPayloadMessage) mapper.readValue(message, clazz);
		}
		catch(Throwable ex) {
			throw new InvalidMessageBodyException("Failed to demarshall messsage body of type:" + type, ex);
		}
	}
	
	private Class<?> determineMessageClass(PayloadType type) {
		switch(type) {
			case CONFIGURATION_UPDATED: return ConfigurationUpdatedPayload.class;
			case ERROR: return ErrorPayload.class;
			default: return null;
		}
	}
}
