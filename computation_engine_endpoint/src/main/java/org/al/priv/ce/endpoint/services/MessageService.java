package org.al.priv.ce.endpoint.services;

import java.util.Optional;

import org.al.priv.ce.endpoint.entities.PayloadMessageRecord;
import org.al.priv.ce.endpoint.entities.RequestMessageRecord;
import org.al.priv.ce.endpoint.exceptions.PayloadException;
import org.al.priv.ce.endpoint.exceptions.PayloadMissingException;
import org.al.priv.ce.endpoint.exceptions.RequestException;
import org.al.priv.ce.endpoint.messaging.RequestMessageSender;
import org.al.priv.ce.endpoint.repositories.PayloadMessageRecordRepository;
import org.al.priv.ce.endpoint.repositories.RequestMessageRecordRepository;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
	private RequestMessageRecordRepository requestRepository;
	
	@Autowired
	private PayloadMessageRecordRepository payloadRepository;
	
	@Autowired
	private RequestMessageSender sender;
	
	public void processRequestMessage(RequestMessageEnvelope envelope) throws RequestException {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			RequestMessageRecord record = new RequestMessageRecord();
			record.setMessageId(envelope.getMetaData().getMessageId());
			record.setMessage(mapper.writeValueAsString(envelope));
			
			log.info("Storing request message. (Message ID: " + envelope.getMetaData().getMessageId() + ")");
			requestRepository.save(record);
			
			sender.send(envelope);
		}
		catch(Throwable ex) {
			throw new RequestException("Failed to process RequestMessageEnvelope. (Message ID: " + 
					envelope.getMetaData().getMessageId() + ")", ex, envelope.getMetaData());
		}
	}
	
	public void processPayloadMessage(PayloadMessageEnvelope envelope) throws PayloadException {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			PayloadMessageRecord record = new PayloadMessageRecord();
			record.setMessageId(envelope.getMetaData().getMessageId());
			record.setMessage(mapper.writeValueAsString(envelope));
			
			log.info("Storing payload. (Message ID: " + envelope.getMetaData().getMessageId() + ")");
			payloadRepository.save(record);
		}
		catch(Throwable ex) {
			throw new PayloadException("Failed to process payloadMessageEnvelope. (Message ID: " + 
					envelope.getMetaData().getMessageId() + ")", ex, envelope.getMetaData().getMessageId());
		}
	}
	
	public PayloadMessageEnvelope getStoredPayload(long messageId) throws PayloadException, PayloadMissingException {
		try {
			Optional<PayloadMessageRecord> optional = payloadRepository.findById(messageId);
			
			if (optional == null || !optional.isPresent())
				throw new PayloadMissingException("Payload has not been delivered by the engine.", messageId);
			
			PayloadMessageRecord record = optional.get();
			
			if (record == null)
				throw new PayloadMissingException("Payload has not been delivered by the engine.", messageId);
			
			ObjectMapper mapper = new ObjectMapper();
			PayloadMessageEnvelope envelope = mapper.readValue(record.getMessage(), PayloadMessageEnvelope.class);
			
			return envelope;
		}
		catch(PayloadMissingException ex) {
			throw ex;
		}
		catch(Throwable ex) {
			throw new PayloadException("Failed to retreive payloadMessageEnvelope. (Message ID: " + 
					messageId + ")", ex, messageId);
		}
		
	}

}
