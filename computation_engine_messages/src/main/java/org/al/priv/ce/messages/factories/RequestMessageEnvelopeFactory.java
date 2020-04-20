package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractRequestMessage;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeMetaData;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.al.priv.ce.messages.requests.UpdateConfigurationRequest;
import org.al.priv.ce.messages.types.RequestType;

public class RequestMessageEnvelopeFactory {
	
	public RequestMessageEnvelope build(AbstractRequestMessage request, RequestMessageEnvelopeMetaData metaData) throws InvalidTypeException, InvalidMessageBodyException {
		
		RequestType type = this.determineType(request.getClass());
		
		if (type == null) {
			throw new InvalidTypeException(request.getClass().getCanonicalName() + " does not have a supported enum.");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String marshalledPayload = null;
		
		try {
			marshalledPayload = mapper.writeValueAsString(request);
		}
		catch(Throwable ex) {
			throw new InvalidMessageBodyException("Failed to marshall '" + type.getClass().getCanonicalName() + 
					"' into message type " + type, ex);
		}
		
		RequestMessageEnvelope envelope = new RequestMessageEnvelope();
		
		envelope.setMetaData(metaData);
		envelope.setType(type);
		envelope.setMessage(marshalledPayload);
		
		return envelope;
	}
	
	private RequestType determineType(Class<?> clazz) {
		
		if (clazz.getCanonicalName().equals(UpdateConfigurationRequest.class.getCanonicalName()))
			return RequestType.UPDATE_CONFIGURATION;
		else
			return null;
	}

}
