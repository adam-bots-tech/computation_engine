package org.al.priv.ce.worker.services;

import java.util.List;

import org.al.priv.ce.messages.AbstractPayloadMessage;
import org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload;
import org.al.priv.ce.messages.requests.UpdateConfigurationRequest;
import org.al.priv.ce.worker.entities.Configuration;
import org.al.priv.ce.worker.exceptions.PayloadException;
import org.al.priv.ce.worker.repositories.ConfigurationRepository;
import org.al.priv.ce.worker.transformer.UpdateConfigurationRequestToConfigurationTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurationService.class);
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	public AbstractPayloadMessage update(UpdateConfigurationRequest request) throws PayloadException {
		
		ConfigurationUpdatedPayload payload = new ConfigurationUpdatedPayload();
		
		List<Configuration> configurations = UpdateConfigurationRequestToConfigurationTransformer.transform(request);
		
		try {
			configurationRepository.saveAll(configurations);
			payload.setChanged(true);
			log.info("Configuration updated in repository.");
		}
		catch(Exception ex) {
			throw new PayloadException("Failed to update configuration", ex);
		}

		return payload;
		
	}

}
