package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractRequestMessage;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.al.priv.ce.messages.requests.ErrorRequest;
import org.al.priv.ce.messages.requests.UpdateConfigurationRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageFactory {

	public AbstractRequestMessage build(String className, String message) 
			throws InvalidTypeException, InvalidMessageBodyException {
		
		Class<?> clazz = this.determineRequestMessageClass(className);
		
		if (clazz == null) 
			throw new InvalidTypeException("Unknown request message type: " + className);
		
		if (clazz.getSuperclass() == null || !clazz.getSuperclass().getCanonicalName().equals(
				AbstractRequestMessage.class.getCanonicalName())) 
			throw new InvalidTypeException("Class for message type '" + className + 
					"' does not extend the super class AbstractRequestMessage");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return (AbstractRequestMessage) mapper.readValue(message, clazz);
		}
		catch(Throwable ex) {
			throw new InvalidMessageBodyException("Failed to demarshall messsage body of type:" + className, ex);
		}
	}
	
	private Class<?> determineRequestMessageClass(String className) {
		switch(className) {
			case "org.al.priv.ce.messages.requests.UpdateConfigurationRequest": return UpdateConfigurationRequest.class;
			case "org.al.priv.ce.messages.requests.ErrorRequest": return ErrorRequest.class;
			default: return null;
		}
	}
}
