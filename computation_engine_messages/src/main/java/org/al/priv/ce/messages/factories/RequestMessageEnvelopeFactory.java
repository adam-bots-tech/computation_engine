package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractRequestMessage;
import org.al.priv.ce.messages.enums.MessageTypes;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeMetaData;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageEnvelopeFactory {
	
	public RequestMessageEnvelope build(AbstractRequestMessage request, RequestMessageEnvelopeMetaData metaData,
			MessageTypes type) throws InvalidTypeException {
		
		RequestMessageEnvelope envelope = new RequestMessageEnvelope();
		
		envelope.setType(type);
		envelope.setMetaData(metaData);
		envelope.setMessage(request);
		
		return envelope;
	}
}
