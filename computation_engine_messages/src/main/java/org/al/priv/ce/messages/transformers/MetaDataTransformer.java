package org.al.priv.ce.messages.transformers;

import org.al.priv.ce.messages.envelopes.PayloadMessageEnvelopeMetaData;
import org.al.priv.ce.messages.envelopes.RequestMessageEnvelopeMetaData;

public class MetaDataTransformer {

	public static PayloadMessageEnvelopeMetaData transform(RequestMessageEnvelopeMetaData requestMetaData) {
		PayloadMessageEnvelopeMetaData payloadMetaData = new PayloadMessageEnvelopeMetaData();
		
		payloadMetaData.setOriginalSource(requestMetaData.getSource());
		payloadMetaData.setMessageId(requestMetaData.getMessageId());
		
		return payloadMetaData;
	}
}
