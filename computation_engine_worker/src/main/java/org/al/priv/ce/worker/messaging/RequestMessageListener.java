package org.al.priv.ce.worker.messaging;

import org.al.priv.ce.worker.exceptions.EndpointException;
import org.al.priv.ce.worker.services.MessageService;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageListener {

	private static final Logger log = LoggerFactory.getLogger(RequestMessageListener.class);
	
	@Autowired
	private MessageService service;
	
	public void receiveRequestMessage(final RequestMessageEnvelope envelope) throws EndpointException {
		log.info("Receiving request message. (Message ID: "+ envelope.getMetaData().getMessageId() + ")");
		
		service.processRequestMessage(envelope);
	}

}
