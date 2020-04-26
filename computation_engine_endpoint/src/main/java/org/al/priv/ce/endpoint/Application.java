package org.al.priv.ce.endpoint;

import org.al.priv.ce.endpoint.messaging.PayloadMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.al.priv.ce.messages.factories", 
		"org.al.priv.ce.endpoint"})
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
	    return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(applicationConfiguration.getMessaging().getPayloadQueue());
	    container.setMessageListener(listenerAdapter);
	    return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(PayloadMessageListener listener) {
	    return new MessageListenerAdapter(listener, "receivePayloadMessage");
	}
}
