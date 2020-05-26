package org.al.priv.ce.endpoint.messaging;

import org.al.priv.ce.endpoint.exceptions.EndpointException;
import org.al.priv.ce.endpoint.services.MessageService;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayloadMessageListener {

	private static final Logger log = LoggerFactory.getLogger(PayloadMessageListener.class);
	
	@Autowired
	private MessageService service;
	
	public void receivePayloadMessage(final PayloadMessageEnvelope envelope) throws EndpointException {
		log.info("Receiving payload message. (Message ID: "+ envelope.getMetaData().getMessageId() + ")");
		
		service.processPayloadMessage(envelope);
	}

}
