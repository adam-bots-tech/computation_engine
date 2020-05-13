package org.al.priv.ce.endpoint.api;

import org.al.priv.ce.endpoint.exceptions.PayloadException;
import org.al.priv.ce.endpoint.exceptions.PayloadMissingException;
import org.al.priv.ce.endpoint.exceptions.RequestException;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelope;
import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelopeMetaData;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelope;
import org.al.priv.ce.messages.factories.PayloadMessageEnvelopeFactory;
import org.al.priv.ce.messages.factories.RequestMessageEnvelopeFactory;
import org.al.priv.ce.messages.factories.exceptions.InvalidMessageBodyException;
import org.al.priv.ce.messages.factories.exceptions.InvalidTypeException;
import org.al.priv.ce.messages.payloads.ErrorPayload;
import org.al.priv.ce.messages.requests.ErrorRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);
	
	@Autowired
	private RequestMessageEnvelopeFactory requestFactory;
	
	@Autowired
	private PayloadMessageEnvelopeFactory payloadFactory;

	@ExceptionHandler(RequestException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RequestMessageEnvelope handleException(RequestException ex) 
			throws InvalidTypeException, InvalidMessageBodyException {
		ErrorRequest error = new ErrorRequest();
		error.setExceptionClassName(ex.getClass().getCanonicalName());
		error.setMessage(ex.getMessage());
		error.setOriginatingComponent("ENDPOINT");
		error.setStackTrace(ExceptionUtils.getStackTrace(ex));
		
		log.error("Error occured while processing a request: " + ex.getMessage(), ex);
		
		return requestFactory.build(error, ex.getMetaData());
	}
	
	@ExceptionHandler(PayloadException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public PayloadMessageEnvelope handleException(PayloadException ex) 
			throws InvalidTypeException, InvalidMessageBodyException {
		ErrorPayload error = new ErrorPayload();
		error.setExceptionClassName(ex.getClass().getCanonicalName());
		error.setMessage(ex.getMessage());
		error.setOriginatingComponent("ENDPOINT");
		error.setStackTrace(ExceptionUtils.getStackTrace(ex));
		
		PayloadMessageEnvelopeMetaData metaData = new PayloadMessageEnvelopeMetaData();
		metaData.setMessageId(ex.getMessageId());
		metaData.updateOnSent();
		
		log.error("Error occured while processing a payload: " + ex.getMessage(), ex);
		
		return payloadFactory.build(error, metaData);
	}
	
	@ExceptionHandler(PayloadMissingException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public PayloadMessageEnvelope handleException(PayloadMissingException ex) 
			throws InvalidTypeException, InvalidMessageBodyException {
		ErrorPayload error = new ErrorPayload();
		error.setExceptionClassName(ex.getClass().getCanonicalName());
		error.setMessage(ex.getMessage());
		error.setOriginatingComponent("ENDPOINT");
		error.setStackTrace(ExceptionUtils.getStackTrace(ex));
		
		PayloadMessageEnvelopeMetaData metaData = new PayloadMessageEnvelopeMetaData();
		metaData.setMessageId(ex.getMessageId());
		metaData.updateOnSent();
		
		return payloadFactory.build(error, metaData);
	}
}
