package org.al.priv.ce.worker.messaging;

import org.al.priv.ce.worker.ApplicationConfiguration;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayloadMessageSender {

	private static final Logger log = LoggerFactory.getLogger(PayloadMessageSender.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired 
	private ApplicationConfiguration applicationConfiguration;
	
	public void send(PayloadMessageEnvelope envelope) {
		log.info("Sending payload message. (Message ID: "+envelope.getMetaData().getMessageId()+")");
        rabbitTemplate.convertAndSend(applicationConfiguration.getMessaging().getExchange(), 
        		applicationConfiguration.getMessaging().getPayloadRoutingKey(), envelope);
	}
}
