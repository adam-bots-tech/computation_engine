package org.al.priv.ce.endpoint;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("computation-engine")
public class ApplicationConfiguration {
	
	// MESSAGING CONFIG
	
	private Messaging messaging;

	public static class Messaging {
		private String exchange;
		private String payloadQueue;
		private String requestQueue;
		private String requestRoutingKey;
		private String payloadRoutingKey;
		
		public String getExchange() {
			return exchange;
		}
		public void setExchange(String exchange) {
			this.exchange = exchange;
		}
		public String getPayloadQueue() {
			return payloadQueue;
		}
		public void setPayloadQueue(String payloadQueue) {
			this.payloadQueue = payloadQueue;
		}
		public String getRequestQueue() {
			return requestQueue;
		}
		public void setRequestQueue(String requestQueue) {
			this.requestQueue = requestQueue;
		}
		public String getRequestRoutingKey() {
			return requestRoutingKey;
		}
		public void setRequestRoutingKey(String requestRoutingKey) {
			this.requestRoutingKey = requestRoutingKey;
		}
		public String getPayloadRoutingKey() {
			return payloadRoutingKey;
		}
		public void setPayloadRoutingKey(String payloadsRoutingKey) {
			this.payloadRoutingKey = payloadsRoutingKey;
		}
	}

	public Messaging getMessaging() {
		return messaging;
	}

	public void setMessaging(Messaging messaging) {
		this.messaging = messaging;
	}
	
	private Repository repository;
	
	public static class Repository {
		private String host;
		private int port;
		private String keyspace;
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getKeyspace() {
			return keyspace;
		}
		public void setKeyspace(String keyspace) {
			this.keyspace = keyspace;
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
