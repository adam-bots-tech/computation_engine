package org.al.priv.ce.worker.transformer;

import java.util.ArrayList;
import java.util.List;

import org.al.priv.ce.messages.requests.UpdateConfigurationRequest;
import org.al.priv.ce.worker.entities.Configuration;
import org.al.priv.ce.worker.enums.ConfigurationKeys;

public class UpdateConfigurationRequestToConfigurationTransformer {
	
	
	public static List<Configuration> transform(UpdateConfigurationRequest request) {
		
		List<Configuration> configurations = new ArrayList<Configuration>();
		
		Configuration interestRate = new Configuration();
		interestRate.setKey(ConfigurationKeys.DEFAULT_INTEREST_RATE.toString());
		interestRate.setValue(request.getDefaultInterestRate());
		configurations.add(interestRate);
		
		return configurations;
	}
}
