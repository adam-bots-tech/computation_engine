package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelopeMetaData;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload;
import org.al.priv.ce.messages.payloads.ErrorPayload;
import org.al.priv.ce.messages.types.PayloadType;

public class PayloadMessageEnvelopeFactory {
	
	public PayloadMessageEnvelope build(AbstractPayloadMessage payload, PayloadMessageEnvelopeMetaData metaData) throws InvalidTypeException, InvalidMessageBodyException {
		
		PayloadType type = this.determineType(payload.getClass());
		
		if (type == null) {
			throw new InvalidTypeException(payload.getClass().getCanonicalName() + " does not have a supported enum.");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String marshalledPayload = null;
		
		try {
			marshalledPayload = mapper.writeValueAsString(payload);
		}
		catch(Throwable ex) {
			throw new InvalidMessageBodyException("Failed to marshall '" + type.getClass().getCanonicalName() + 
					"' into message type " + type, ex);
		}
		
		PayloadMessageEnvelope envelope = new PayloadMessageEnvelope();
		
		envelope.setMetaData(metaData);
		envelope.setType(type);
		envelope.setMessage(marshalledPayload);
		
		return envelope;
	}
	
	private PayloadType determineType(Class<?> clazz) {
		
		if (clazz.getCanonicalName().equals(ConfigurationUpdatedPayload.class.getCanonicalName()))
			return PayloadType.CONFIGURATION_UPDATED;
		else if (clazz.getCanonicalName().equals(ErrorPayload.class.getCanonicalName()))
			return PayloadType.ERROR;
		else
			return null;
	}

}
