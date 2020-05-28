package org.al.priv.ce.worker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.al.priv.ce.messages.factories", 
		"org.al.priv.ce.worker"})
@EnableJpaRepositories(basePackages = "org.al.priv.ce.worker.repositories")
public class Application {
	
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public TopicExchange appExchange() {
	    return new TopicExchange(applicationConfiguration.getMessaging().getExchange());
	}

	@Bean
	public Queue appQueuePayloads() {
	    return new Queue(applicationConfiguration.getMessaging().getPayloadQueue());
	}

	@Bean
	public Queue appQueueRequests() {
	    return new Queue(applicationConfiguration.getMessaging().getRequestQueue());
	}

	@Bean
	public Binding declareBindingRequests() {
	    return BindingBuilder.bind(appQueueRequests()).to(appExchange()).with(
	    		applicationConfiguration.getMessaging().getRequestRoutingKey());
	}

	@Bean
	public Binding declareBindingPayloads() {
	    return BindingBuilder.bind(appQueuePayloads()).to(appExchange()).with(
	    		applicationConfiguration.getMessaging().getPayloadRoutingKey());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	    return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule());
	    return new Jackson2JsonMessageConverter(mapper);
	}
}
