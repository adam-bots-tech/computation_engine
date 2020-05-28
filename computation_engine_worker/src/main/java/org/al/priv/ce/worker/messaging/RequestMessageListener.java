package org.al.priv.ce.worker.messaging;

import org.al.priv.ce.worker.exceptions.RequestException;
import org.al.priv.ce.worker.services.MessageService;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class RequestMessageListener {

	private static final Logger log = LoggerFactory.getLogger(RequestMessageListener.class);
	
	@Autowired
	private MessageService service;
	
	@RabbitListener(queues = "requests")
	public void receiveRequestMessage(final RequestMessageEnvelope envelope) throws JsonProcessingException, RequestException {
		ObjectMapper mapper = new ObjectMapper();

		log.info("Receiving request message. (Message ID: "+ envelope.getMetaData().getMessageId() + ")");
		log.debug("Message: " + mapper.writeValueAsString(envelope));
		
		service.processRequestMessage(envelope);
	}

}
