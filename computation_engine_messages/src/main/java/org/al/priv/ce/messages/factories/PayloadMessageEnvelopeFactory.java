package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelopeMetaData;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.springframework.stereotype.Component;

@Component
public class PayloadMessageEnvelopeFactory {
	
	public PayloadMessageEnvelope build(AbstractPayloadMessage payload, PayloadMessageEnvelopeMetaData metaData) throws InvalidTypeException {
		
		PayloadMessageEnvelope envelope = new PayloadMessageEnvelope();
		
		envelope.setMetaData(metaData);
		envelope.setMessage(payload);
		
		return envelope;
	}
}
