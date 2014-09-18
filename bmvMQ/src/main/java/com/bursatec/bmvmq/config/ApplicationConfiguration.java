/**
 * 
 */
package com.bursatec.bmvmq.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author gus
 *
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration {
	
	/**
	 * @return
	 */
	@Bean
	ConnectionFactory jmsConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		//TODO Parametrizar la URL para pruebas.
//		connectionFactory.setBrokerURL("vm://localhost?broker.persistent=false");
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
		pooledConnectionFactory.setMaxConnections(8);
		return pooledConnectionFactory;
	}
	
	/**
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "jmsQueueTemplate")
	JmsTemplate jmsQueueTemplate(final ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		//Point-to-point domain
		jmsTemplate.setPubSubDomain(false);
		return jmsTemplate;
	}
	
	/**
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "jmsTopicTemplate")
	JmsTemplate jmsTopicTemplate(final ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		//Publisher-subscriber domain
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}

}
