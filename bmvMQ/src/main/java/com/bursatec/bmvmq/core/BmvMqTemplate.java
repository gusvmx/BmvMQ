/**
 * Bursatec - BMV Sep 9, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.config.ApplicationConfiguration;
import com.bursatec.bmvmq.listener.MessageListener;

/**
 * @author gus
 *
 */
public class BmvMqTemplate implements MqTemplate {
	
	/**
	 * 
	 */
	private final Logger logger = LoggerFactory.getLogger(BmvMqTemplate.class);

	/**
	 * La fabrica de conexiones JMS.
	 */
	private ConnectionFactory connectionFactory;
	/**
	 * 
	 */
	private JmsTemplate jmsQueueTemplate;
	/**
	 * 
	 */
	private JmsTemplate jmsTopicTemplate;

	/**
	 * Constructor por default.
	 */
	public BmvMqTemplate() {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		this.connectionFactory = context.getBean(ConnectionFactory.class);
		this.jmsQueueTemplate = (JmsTemplate) context.getBean("jmsQueueTemplate");
		this.jmsTopicTemplate = (JmsTemplate) context.getBean("jmsTopicTemplate");
		context.close();
	}

	@Override
	public final void publish(final String destination,
			final Serializable message) {
		MessageCreator messageCreator = new SerializableMessageCreator(message);
		jmsTopicTemplate.send(destination, messageCreator);
		logger.debug("Serializable object message published to the topic {}", destination);
	}

	@Override
	public final void publish(final String destination, final String message) {
		MessageCreator messageCreator = new StringMessageCreator(message);
		jmsTopicTemplate.send(destination, messageCreator);
		logger.debug("String message published to the topic {}", destination);
	}

	@Override
	public final void publish(final String destination, final byte[] message) {
		MessageCreator messageCreator = new ByteArrayMessageCreator(message);
		jmsTopicTemplate.send(destination, messageCreator);
		logger.debug("Byte array message published to the topic {}", destination);
	}

	@Override
	public final void send(final String destination, final Serializable message) {
		MessageCreator messageCreator = new SerializableMessageCreator(message);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("Serializable object message sent to the queue {}", destination);
	}
	
	@Override
	public final void send(final String destination, final Serializable message, final String messageGroup) {
		MessageCreator messageCreator = new SerializableMessageCreator(message, messageGroup);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("Serializable object message sent to the queue {} to the message group {}", 
				destination, messageGroup);
	}

	@Override
	public final void send(final String destination, final String message) {
		MessageCreator messageCreator = new StringMessageCreator(message);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("String message sent to the queue {}", destination);
	}
	
	@Override
	public final void send(final String destination, final String message, final String messageGroup) {
		MessageCreator messageCreator = new StringMessageCreator(message, messageGroup);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("String message sent to the queue {} to the message group {}", destination, messageGroup);
	}

	@Override
	public final void send(final String destination, final byte[] message) {
		MessageCreator messageCreator = new ByteArrayMessageCreator(message);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("Byte array message sent to the queue {}", destination);
	}
	
	@Override
	public final void send(final String destination, final byte[] message, final String messageGroup) {
		MessageCreator messageCreator = new ByteArrayMessageCreator(message, messageGroup);
		jmsQueueTemplate.send(destination, messageCreator);
		logger.debug("Byte array message sent to the queue {} to the message group {}", destination, messageGroup);
	}
	
	@Override
	public final void receiveExclusively(final String destinationName,
			final MessageListener messageListener) {
		Queue destination = new ActiveMQQueue(destinationName + "?consumer.exclusive=true");
		MessageListenerAdapter adapter = new MessageListenerAdapter(messageListener);
		adapter.setDefaultListenerMethod("onMessage");

		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setMessageListener(adapter);
		container.setConnectionFactory(connectionFactory);
		container.setDestination(destination);
		container.initialize();
		container.start();
		logger.info("Connection established to the queue {} as exclusive consumer. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}

	@Override
	public final void receive(final String destination,
			final MessageListener messageListener) {
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
//		container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		container.setSessionTransacted(true);
//		container.setTransactionManager(new JmsTransactionManager(connectionFactory));
//		container.setTransactionManager(new JtaTransactionManager());
		container.initialize();
		container.start();
		logger.info("Connection established to the queue {}"
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}

	@Override
	public final void subscribe(final String destination,
			final MessageListener messageListener) {
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
		container.setPubSubDomain(true);
		container.initialize();
		container.start();
		logger.info("Subscribed successfully to the topic {}"
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}

	/**
	 * @param destination
	 *            El destino: nombre del queue o nombre del tópico.
	 * @param messageListener
	 *            El message listener de la aplicación donde se entregarán los
	 *            mensajes.
	 * @return El message listener adapter apuntando hacia el destino indicado.
	 */
	private DefaultMessageListenerContainer createContainer(
			final String destination, final MessageListener messageListener) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(messageListener);
		adapter.setDefaultListenerMethod("onMessage");

		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setMessageListener(adapter);
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(destination);
		return container;
	}

	@Override
	public final void durableSubscription(final String destination,
			final String clientId, final String durableSubscriptionName,
			final MessageListener messageListener) {
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
		container.setPubSubDomain(true);
		container.setSubscriptionDurable(true);
		container.setClientId(clientId);
		container.setDurableSubscriptionName(durableSubscriptionName);
		// TODO Ver si es necesario colocar un ExceptionListener
		container.initialize();
		container.start();
	}

}
