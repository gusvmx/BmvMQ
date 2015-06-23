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
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.jmx.MBeanFactory;
import com.bursatec.bmvmq.jmx.stats.JmsConsumerStats;
import com.bursatec.bmvmq.listener.ActiveMqExceptionListener;
import com.bursatec.bmvmq.listener.BmvMqMessageListener;
import com.bursatec.bmvmq.listener.MessageListenerAdapter;
import com.bursatec.bmvmq.util.UrlUtility;

/**
 * Fábrica de componentes JMS para ActiveMQ.
 * 
 * Responsable de configurar una fábrica de conexiones utilizando el protocolo
 * failover de ActiveMQ para poder reestablecer conexiones automáticamente con
 * el broker JMS en caso de caída de este último.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public class ActiveMqComponentFactory extends JmsComponentFactory {


	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqComponentFactory.class);
	/***/
	private ActiveMQConnectionFactory connectionFactory;
	/***/
	private static final String FAILOVER_PROTOCOL = "failover:";
	
	/**
	 * Constructor por default.
	 * @param configFileLocation La ubicación del archivo de configuración.
	 * @throws FileNotFoundException En caso de no encontrar el archivo de configuración.
	 */
	public ActiveMqComponentFactory(final String configFileLocation)
			throws FileNotFoundException {
		super(configFileLocation);
	}
	
	@Override
	protected final ConnectionFactory getConnectionFactory(final BmvMq config) {
		connectionFactory = new ActiveMQConnectionFactory();
		Map<String, String> params = new HashMap<String, String>();
		params.put("maxReconnectDelay", config.getReconnectionInterval().toString());
		params.put("jms.useCompression", config.isUseCompression().toString());
		connectionFactory.setBrokerURL(FAILOVER_PROTOCOL + "(" + config.getUrl() + ")?" 
				+ UrlUtility.encodeParameters(params));
		connectionFactory.setUserName(config.getUsername());
		connectionFactory.setPassword(config.getPassword());
		connectionFactory.setUseAsyncSend(config.isAsyncSend());
		connectionFactory.setExceptionListener(new ActiveMqExceptionListener());
		connectionFactory.setClientIDPrefix(config.getClientId());
		connectionFactory.setConnectionIDPrefix(config.getClientId() + "-conn-");
		LOGGER.info("Connection factory has been initialised with the following values. "
				+ "URL:{}, Username:{}, MaxConnections:{}", 
				config.getUrl(), config.getUsername(), config.getMaxConnections());
		return connectionFactory;
	}
	
	@Override
	public final MessageConsumer createExclusiveQueueConsumer(
			final String destination, final BmvMqMessageListener messageListener) throws JMSException {
		Session consumersSession = getConsumersSession();
		Destination queue = new ActiveMQQueue(destination + "?consumer.exclusive=true");
		JmsConsumerStats stats = new JmsConsumerStats(destination);
		MBeanFactory.createMbean(stats, MBeanFactory.buildReceiverName(destination));
		MessageListenerAdapter adapter = createMessageListenerAdapter(messageListener, stats);
		MessageConsumer consumer = consumersSession.createConsumer(queue);
		consumer.setMessageListener(adapter);
		return consumer;
	}

	@Override
	protected final void setProprietaryConnectionParams(final Connection connection) throws JMSException {
		ActiveMQConnection amqConnection = (ActiveMQConnection) connection;
		BmvMq config = BmvMqContext.getConfiguration();
		amqConnection.addTransportListener(new ActiveMqExceptionListener());
		amqConnection.setClientID(config.getClientId());
	}

}
