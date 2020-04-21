package org.al.priv.ce.endpoint.api;

import org.al.priv.ce.endpoint.exceptions.PayloadException;
import org.al.priv.ce.endpoint.exceptions.PayloadMissingException;
import org.al.priv.ce.endpoint.exceptions.RequestException;
import org.al.priv.ce.endpoint.services.MessageService;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeResponse;
import org.al.priv.ce.messages.factories.RequestMessageEnvelopeResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private RequestMessageEnvelopeResponseFactory factory;
	
	@Autowired
	private MessageService service;
	

	@PostMapping("/message")
	public RequestMessageEnvelopeResponse post(@RequestBody RequestMessageEnvelope envelope) throws RequestException {
		envelope.getMetaData().updateOnReceipt();
		
		RequestMessageEnvelopeResponse response = factory.build(envelope);
		
		log.info("Receieving request message. Generated Message ID: " + envelope.getMetaData().getMessageId());
		
		service.processInboundRequestMessage(envelope);
		
		return response;
		
	}
	
	@GetMapping("/message/{id}")
	public PayloadMessageEnvelope get(@PathVariable("id") long id) throws PayloadException, PayloadMissingException {
		return service.getStoredPayload(id);
	}
}


