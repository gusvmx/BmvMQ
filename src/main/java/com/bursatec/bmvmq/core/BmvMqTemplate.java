/**
 * Bursatec - BMV Sep 9, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.transaction.jta.JtaTransactionManager;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.config.ApplicationConfiguration;
import com.bursatec.bmvmq.config.BmvMqConfigurationReader;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.listener.BmvMqErrorHandlerAdapter;
import com.bursatec.bmvmq.listener.MessageListener;

/**
 * @author gus
 *
 */
public class BmvMqTemplate implements MqTemplate {

	/***/
	public static final String DEFAULT_CONFIG_FILE_LOCATION = "classpath:bmvMq.xml";
	/***/
	private final Logger logger = LoggerFactory.getLogger(BmvMqTemplate.class);
	/**
	 * La fabrica de conexiones JMS.
	 */
	private ConnectionFactory connectionFactory;
	/***/
	private JmsTemplate jmsQueueTemplate;
	/***/
	private JmsTemplate jmsTopicTemplate;
	/***/
	private Map<String, DefaultMessageListenerContainer> queueContainers;
	/***/
	private Map<String, DefaultMessageListenerContainer> topicContainers;

	/**
	 * Constructor por default.
	 * 
	 * @throws FileNotFoundException
	 *             En caso de no encontrar en el classpath el archivo de
	 *             configuración bmvMq.xml
	 */
	public BmvMqTemplate() throws FileNotFoundException {
		this(DEFAULT_CONFIG_FILE_LOCATION);
	}
	
	/**
	 * @param configFileLocation
	 *            La ubicación del archivo de configuración.
	 * 
	 *            La ubicación del archivo puede llevar los siguientes prefijos:
	 *            classpath:, file:, jar:, zip:
	 *            
	 *            En caso de no contar con un prefijo, el archivo se buscará en el FS.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar el archivo de configuración en la
	 *             ubicación indicada.
	 */
	public BmvMqTemplate(final String configFileLocation) throws FileNotFoundException {
		BmvMq config = BmvMqConfigurationReader.readConfiguration(configFileLocation);
		ApplicationConfiguration.setConfiguration(config);
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		this.connectionFactory = context.getBean(ConnectionFactory.class);
		this.jmsQueueTemplate = (JmsTemplate) context.getBean("jmsQueueTemplate");
		this.jmsTopicTemplate = (JmsTemplate) context.getBean("jmsTopicTemplate");
		this.queueContainers = new HashMap<String, DefaultMessageListenerContainer>();
		this.topicContainers = new HashMap<String, DefaultMessageListenerContainer>();
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
		DefaultMessageListenerContainer container = createContainer(destinationName, messageListener);
		
		Queue destination = new ActiveMQQueue(destinationName + "?consumer.exclusive=true");
		container.setDestination(destination);
		
		initializeQueueContainer(container);
		logger.info("Connection established to the queue {} as exclusive consumer. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * @param container El contenedor del queue que se inicializará.
	 */
	private void initializeQueueContainer(final DefaultMessageListenerContainer container) {
		container.initialize();
		container.start();
		queueContainers.put(container.getDestinationName(), container);
	}

	@Override
	public final void receive(final String destination,
			final MessageListener messageListener) {
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
		initializeQueueContainer(container);
		logger.info("Connection established to the queue {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}

	@Override
	public final void subscribe(final String destination,
			final MessageListener messageListener) {
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
		container.setPubSubDomain(true);
		initializeTopicContainer(container);
		logger.info("Subscribed successfully to the topic {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * @param container El contenedor del queue que se inicializará.
	 */
	private void initializeTopicContainer(final DefaultMessageListenerContainer container) {
		container.initialize();
		container.start();
		topicContainers.put(container.getDestinationName(), container);
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
		container.setRecoveryInterval(ApplicationConfiguration.getConfiguration().getReconnectionInterval());
		container.setErrorHandler(new BmvMqErrorHandlerAdapter());
		configureAcknowledgeMode(container);
		logger.info("The message listener for the destination {} has been configured with {} ack mode", 
				destination, ApplicationConfiguration.getConfiguration().getAcknowledgeMode());
		return container;
	}
	
	/**
	 * Configura la modalidad de acuse de recibo.
	 * @param container El contenedor que será configurado.
	 */
	private void configureAcknowledgeMode(final DefaultMessageListenerContainer container) {
		BmvMq config = ApplicationConfiguration.getConfiguration();
		switch (config.getAcknowledgeMode()) {
		case CLIENT_ACKNOWLEDGE:
			container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
			break;
		case DUPS_OK_ACKNOWLEDGE:
			container.setSessionAcknowledgeMode(Session.DUPS_OK_ACKNOWLEDGE);
			break;
		case SESSION_TRANSACTED:
			container.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
			container.setSessionTransacted(true);
			break;
		case SESSION_XA_TRANSACTED:
			container.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
			container.setSessionTransacted(true);
			container.setTransactionManager(new JtaTransactionManager());
			break;
		case AUTO_ACKNOWLEDGE:
		default:
			container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
			break;
		}
	}

	@Override
	public final void durableSubscription(final String destination,
			final String durableSubscriptionName,
			final MessageListener messageListener) {
		BmvMq config = ApplicationConfiguration.getConfiguration();
		DefaultMessageListenerContainer container = createContainer(destination, messageListener);
		container.setPubSubDomain(true);
		container.setSubscriptionDurable(true);
		container.setClientId(config.getClientId());
		container.setDurableSubscriptionName(durableSubscriptionName);
		initializeTopicContainer(container);
		logger.info("Durable subscription established successfully to the topic {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}

	@Override
	public final void stopReceiving(final String destination) {
		stop(destination, queueContainers);
	}

	/**
	 * Finaliza el contenedor indicado.
	 * @param destination El nombre del destino que se desea detener.
	 * @param containers La lista de contenedores donde se encuentra el destino deseado.
	 */
	private void stop(final String destination,
			final Map<String, DefaultMessageListenerContainer> containers) {
		DefaultMessageListenerContainer container = containers.get(destination);
		if (container != null) {
			container.stop();
		}
	}

	@Override
	public final void unsubscribe(final String destination) {
		stop(destination, topicContainers);
	}

}
