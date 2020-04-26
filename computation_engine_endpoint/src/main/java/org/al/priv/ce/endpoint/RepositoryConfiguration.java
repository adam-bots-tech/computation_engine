package org.al.priv.ce.endpoint;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "org.al.priv.ce.endpoint.repositories")
public class RepositoryConfiguration extends AbstractCassandraConfiguration {

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Override
	protected String getKeyspaceName() {
		return applicationConfiguration.getRepository().getKeyspace();
	}
	
	@Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(applicationConfiguration.getRepository().getHost());
        cluster.setPort(applicationConfiguration.getRepository().getPort());
        cluster.setJmxReportingEnabled(false);
        return cluster;
    }
 
	@Bean
	public CassandraMappingContext mappingContext() throws ClassNotFoundException {
	    CassandraMappingContext mappingContext= new CassandraMappingContext();
	    mappingContext.setInitialEntitySet(getInitialEntitySet());
	    return mappingContext;
	}
	
	@Override
	public String[] getEntityBasePackages() {
	    return new String[]{"org.al.priv.ce.endpoint.entities"};
	}

	@Override
	protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
	    return CassandraEntityClassScanner.scan(getEntityBasePackages());
	}
	
	
}
