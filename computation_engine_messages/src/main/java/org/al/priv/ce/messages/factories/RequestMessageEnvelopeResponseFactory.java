package org.al.priv.ce.messages.factories;

import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeResponse;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageEnvelopeResponseFactory {

	/* *
	 * Builds a response with a message id set to the epoc of envelope.metaData.dateReceived and then sets
	 * the envelope.metadata.messageId to the newly created message id.
	 */
	public RequestMessageEnvelopeResponse build(RequestMessageEnvelope envelope) {
		RequestMessageEnvelopeResponse response = new RequestMessageEnvelopeResponse();
		
		if (envelope.getMetaData().getMessageReceived() == null) {
			envelope.getMetaData().setMessageReceived(DateTime.now());
		}
		
		response.setMessageId(envelope.getMetaData().getMessageReceived().getMillis());
		envelope.getMetaData().setMessageId(response.getMessageId());
		return response;
	}
}
