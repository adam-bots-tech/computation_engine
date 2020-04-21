package org.al.priv.ce.endpoint.messaging;

import org.al.priv.ce.endpoint.ApplicationConfiguration;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageSender {

	private static final Logger log = LoggerFactory.getLogger(RequestMessageSender.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired 
	private ApplicationConfiguration applicationConfiguration;
	
	public void send(RequestMessageEnvelope envelope) {
		log.info("Sending request message. (Message ID: "+envelope.getMetaData().getMessageId()+")");
        rabbitTemplate.convertAndSend(applicationConfiguration.getMessaging().getExchange(), 
        		applicationConfiguration.getMessaging().getRequestRoutingKey(), envelope);
	}
}
