package org.al.priv.ce.worker.services;

import org.al.priv.ce.worker.exceptions.PayloadException;
import org.al.priv.ce.worker.messaging.PayloadMessageSender;
import org.al.priv.ce.worker.services.MessageService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelopeMetaData;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.factories.PayloadMessageEnvelopeFactory;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.payloads.ErrorPayload;
import org.al.priv.ce.messages.requests.UpdateConfigurationRequest;
import org.al.priv.ce.messages.transformers.MetaDataTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private PayloadMessageEnvelopeFactory payloadFactory;
	
	@Autowired
	private PayloadMessageSender sender;
	
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	public void processRequestMessage(RequestMessageEnvelope envelope) {
		
		AbstractPayloadMessage payload = null;
		
		switch(envelope.getType()) {
			case UPDATE_CONFIGURATION: {
				log.info("Message type recognized. Invoking configuration service..."); 
				
				try {
					payload = configurationService.update((UpdateConfigurationRequest) envelope.getMessage());
				}
				catch(PayloadException ex) {
					payload = buildErrorPayload(ex);
				}
			} break;
			default: {
				log.info("No matching service for message type: " + envelope.getType().toString());
			} break;
		}
		
		if (payload == null) {
			log.error("No payload created for message ID " + envelope.getMetaData().getMessageId() + ".");
			PayloadException ex = new PayloadException(
					"No payload created for message ID " + envelope.getMetaData().getMessageId() + ".",
					new NullPointerException());
			payload = buildErrorPayload(ex);
		}
		
		PayloadMessageEnvelopeMetaData metaData = MetaDataTransformer.transform(envelope.getMetaData());
		metaData.setDateProcessed(DateTime.now());
		
		try {
			PayloadMessageEnvelope payloadEnvelope = payloadFactory.build(payload, metaData);
			sender.send(payloadEnvelope);
		}
		catch(InvalidTypeException ex) {
			log.error("Message type not supported in message factories (Message ID: " + metaData.getMessageId() + "). Creating error message instead...");
			PayloadException payEx = new PayloadException(
					"No payload created for message ID " + envelope.getMetaData().getMessageId() + ".",
					ex);
			payload = buildErrorPayload(payEx);
			
			try {
				PayloadMessageEnvelope payloadEnvelope = payloadFactory.build(payload, metaData);
				sender.send(payloadEnvelope);
			}
			catch(InvalidTypeException innerEx) {
				log.error("Error message type not supported in factories. Cannot create any message to send.");
				return;
			}
		}
	}
	
	private ErrorPayload buildErrorPayload(Exception ex) {
		ErrorPayload error = new ErrorPayload();
		error.setExceptionClassName(ex.getClass().getCanonicalName());
		error.setMessage(ex.getMessage());
		error.setOriginatingComponent("WORKER");
		error.setStackTrace(ExceptionUtils.getStackTrace(ex));
		return error;
	}
}
