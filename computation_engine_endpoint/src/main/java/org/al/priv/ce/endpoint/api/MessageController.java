package org.al.priv.ce.endpoint.api;

import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeResponse;
import org.al.priv.ce.messages.factories.RequestMessageEnvelopeResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	@Autowired
	private RequestMessageEnvelopeResponseFactory factory;

	@PostMapping("/message")
	public RequestMessageEnvelopeResponse post(@RequestBody RequestMessageEnvelope envelope) {
		envelope.getMetaData().updateOnReceipt();
		
		RequestMessageEnvelopeResponse response = factory.build(envelope);
		
		return response;
		
	}
}


