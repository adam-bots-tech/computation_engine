package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload;
import org.al.priv.ce.messages.payloads.ErrorPayload;
import org.springframework.stereotype.Component;

@Component
public class PayloadMessageFactory {

	public AbstractPayloadMessage build(String className, String message) 
			throws InvalidTypeException, InvalidMessageBodyException {
		
		Class<?> clazz = this.determineMessageClass(className);
		
		if (clazz == null) 
			throw new InvalidTypeException("Unknown payload message type: " + className);
		
		if (clazz.getSuperclass() == null || !clazz.getSuperclass().getCanonicalName().equals(
				AbstractPayloadMessage.class.getCanonicalName())) 
			throw new InvalidTypeException("Class for message type '" + className + 
					"' does not extend the super class AbstractPayloadMessage");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return (AbstractPayloadMessage) mapper.readValue(message, clazz);
		}
		catch(Throwable ex) {
			throw new InvalidMessageBodyException("Failed to demarshall messsage body of class:" + className, ex);
		}
	}
	
	private Class<?> determineMessageClass(String className) {
		switch(className) {
			case "org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload": return ConfigurationUpdatedPayload.class;
			case "org.al.priv.ce.messages.payloads.ErrorPayload": return ErrorPayload.class;
			default: return null;
		}
	}
}
