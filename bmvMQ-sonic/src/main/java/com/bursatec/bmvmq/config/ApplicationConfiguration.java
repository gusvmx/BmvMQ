/**
 * 
 */
package com.bursatec.bmvmq.config;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;

import com.bursatec.bmvmq.config.bind.BmvMq;

/**
 * @author gus
 *
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration {
	
	/**
	 * La configuracion de BmvMQ.
	 */
	private static BmvMq configuration;
	/***/
	private Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	/**
	 * @return Un pool de la fábrica de conexiones.
	 * @throws JMSException Si no se puede crear un conectionFactory de Sonic.
	 */
	@Bean
	ConnectionFactory jmsConnectionFactory() throws JMSException {
		progress.message.jclient.ConnectionFactory connectionFactory = new progress.message.jclient.ConnectionFactory();
		connectionFactory.setConnectionURLs(configuration.getUrl());
		connectionFactory.setClientID(configuration.getClientId());
		connectionFactory.setFaultTolerant(true);
		connectionFactory.setFaultTolerantReconnectTimeout(configuration.getReconnectionInterval());
		connectionFactory.setDefaultUser(configuration.getUsername());
		connectionFactory.setDefaultPassword(configuration.getPassword());
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		cachingConnectionFactory.setSessionCacheSize(configuration.getMaxConnections());
		
		logger.info("Caching connection factory has been initialised with the following values. "
				+ "URL:{}, Username:{}, MaxConnections:{}", 
				configuration.getUrl(), configuration.getUsername(), configuration.getMaxConnections());
		return cachingConnectionFactory;
	}
	
	/**
	 * @param connectionFactory La fábrica de conexiones JMS.
	 * @return La plantilla para las operaciones JMS relacionadas con Queues.
	 */
	@Bean(name = "jmsQueueTemplate")
	JmsTemplate jmsQueueTemplate(final ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		//Point-to-point domain
		jmsTemplate.setPubSubDomain(false);
		configureDestinationResolver(jmsTemplate);
		logger.info("A {} queue destination resolver has been configured.", configuration.getDestinationResolver());
		return jmsTemplate;
	}
	
	/**
	 * @param jmsTemplate La plantilla JMS que se configurará.
	 */
	private void configureDestinationResolver(final JmsTemplate jmsTemplate) {
		switch (configuration.getDestinationResolver()) {
		case DYNAMIC:
			jmsTemplate.setDestinationResolver(new DynamicDestinationResolver());
			break;
		case JNDI:
			jmsTemplate.setDestinationResolver(new JndiDestinationResolver());
			break;
		case HYBRID:
			JndiDestinationResolver destinationResolver = new JndiDestinationResolver();
			destinationResolver.setFallbackToDynamicDestination(true);
			jmsTemplate.setDestinationResolver(destinationResolver);
			break;
		default:
			//Usa el default, DynamicDestinationResolver.
			break;
		}
	}
	
	/**
	 * @param connectionFactory La fábrica de conexiones JMS.
	 * @return La plantilla para las operaciones JMS relacionadas con Topics.
	 */
	@Bean(name = "jmsTopicTemplate")
	JmsTemplate jmsTopicTemplate(final ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		//Publisher-subscriber domain
		jmsTemplate.setPubSubDomain(true);
		configureDestinationResolver(jmsTemplate);
		logger.info("A {} topic destination resolver has been configured.", configuration.getDestinationResolver());
		return jmsTemplate;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public static final void setConfiguration(final BmvMq configuration) {
		ApplicationConfiguration.configuration = configuration;
	}

	/**
	 * @return the configuration
	 */
	public static final BmvMq getConfiguration() {
		return configuration;
	}

}
