package org.al.priv.ce.endpoint.messaging;

import org.al.priv.ce.endpoint.exceptions.PayloadException;
import org.al.priv.ce.endpoint.services.MessageService;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class PayloadMessageListener {

	private static final Logger log = LoggerFactory.getLogger(PayloadMessageListener.class);
	
	@Autowired
	private MessageService service;
	
	@RabbitListener(queues = "payloads")
	public void receivePayloadMessage(final PayloadMessageEnvelope envelope) throws PayloadException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("Receiving payload message. (Message ID: "+ envelope.getMetaData().getMessageId() + ")");
		log.debug("Message: " + mapper.writeValueAsString(envelope));
		
		service.processPayloadMessage(envelope);
	}

}
