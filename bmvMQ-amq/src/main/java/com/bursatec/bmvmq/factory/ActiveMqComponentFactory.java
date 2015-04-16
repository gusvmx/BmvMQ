/**
 * Bursatec - BMV Apr 8, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.factory;

import java.io.FileNotFoundException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqConfigurationReader;
import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.listener.ActiveMqExceptionListener;
import com.bursatec.bmvmq.listener.BmvMqExceptionListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.MessageListenerAdapter;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class ActiveMqComponentFactory extends JmsComponentFactory {


	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqComponentFactory.class);
	/***/
	private PooledConnectionFactory pooledConnectionFactory;
	/***/
	private static final String FAILOVER_PROTOCOL = "failover:";
	/***/
	private static final String RECONNECTION_INTERVAL_PARAM = "maxReconnectDelay=";
	
	/**
	 * @param configFileLocation La ubicación del archivo de configuración.
	 * @throws FileNotFoundException En caso de no encontrar el archivo de configuración.
	 */
	public ActiveMqComponentFactory(final String configFileLocation)
			throws FileNotFoundException {
		super(configFileLocation);
	}
	
	@Override
	protected final ConnectionFactory getConnectionFactory(final BmvMq config) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(FAILOVER_PROTOCOL + "(" + config.getUrl() + ")?" 
				+ RECONNECTION_INTERVAL_PARAM + config.getReconnectionInterval());
		connectionFactory.setUserName(config.getUsername());
		connectionFactory.setPassword(config.getPassword());
		connectionFactory.setUseAsyncSend(config.isAsyncSend());
		connectionFactory.setExceptionListener(getExceptionListenerAdapter());
		connectionFactory.setTransportListener(null);
		connectionFactory.setClientIDPrefix(config.getClientId());
		connectionFactory.setConnectionIDPrefix(config.getClientId() + "-conn-");
		this.pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
		this.pooledConnectionFactory.setMaxConnections(config.getMaxConnections());
		LOGGER.info("Pooled connection factory has been initialised with the following values. "
				+ "URL:{}, Username:{}, MaxConnections:{}", 
				config.getUrl(), config.getUsername(), config.getMaxConnections());
		return pooledConnectionFactory;
	}
	
	@Override
	public final MessageConsumer createExclusiveQueueConsumer(
			final String destination, final MessageListener messageListener) throws JMSException {
		Session consumersSession = getConsumersSession();
		Destination queue = new ActiveMQQueue(destination + "?consumer.exclusive=true");
		MessageListenerAdapter adapter = new MessageListenerAdapter(consumersSession, messageListener);
		MessageConsumer consumer = consumersSession.createConsumer(queue);
		consumer.setMessageListener(adapter);
		return consumer;
	}

	@Override
	public final void stop() {
		pooledConnectionFactory.stop();
	}

	@Override
	protected final void setProprietaryConnectionParams(final Connection connection) throws JMSException {
		PooledConnection pooledConnection = (PooledConnection) connection;
		ActiveMQConnection amqConnection = (ActiveMQConnection) pooledConnection.getConnection();
		BmvMq config = BmvMqContext.getConfiguration();
		amqConnection.addTransportListener(getExceptionListenerAdapter());
		amqConnection.setClientID(config.getClientId());
	}
	
	/**
	 * @return Un exception listener adapter inicializado con el exception listener configurado. 
	 */
	private ActiveMqExceptionListener getExceptionListenerAdapter() {
		BmvMq config = BmvMqContext.getConfiguration();
		String exceptionListenerName = config.getErrorHandlerClassName();
		BmvMqExceptionListener exceptionListener = BmvMqConfigurationReader.initializeExceptionListener(
				exceptionListenerName);
		return new ActiveMqExceptionListener(exceptionListener);
	}

}