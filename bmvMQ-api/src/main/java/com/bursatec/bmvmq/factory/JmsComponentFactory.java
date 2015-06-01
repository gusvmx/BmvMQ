/**
 * Bursatec - BMV Mar 19, 2015
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
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.InvalidBmvMqConfigurationException;
import com.bursatec.bmvmq.config.BmvMqConfigurationReader;
import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.core.AbstractMessageCreator;
import com.bursatec.bmvmq.core.QueueMessageCreator;
import com.bursatec.bmvmq.core.TopicMessageCreator;
import com.bursatec.bmvmq.exception.ConnectionCreationFailureException;
import com.bursatec.bmvmq.exception.SessionCreationFailureException;
import com.bursatec.bmvmq.listener.BmvMqMessageListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.MessageListenerAdapter;
import com.bursatec.bmvmq.listener.RawMessageListener;
import com.bursatec.bmvmq.listener.message.BodyMessageListenerAdapter;
import com.bursatec.bmvmq.listener.message.RawMessageListenerAdapter;

/**
 * Fábrica de componentes JMS responsable de crear cada uno de los elementos
 * necesarios para establecer una comunicación con un broker JMS.
 * 
 * @author gus
 *
 */
public abstract class JmsComponentFactory {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsComponentFactory.class);
	/** La conexión hacia el broker JMS. */
	private Connection connection;
	/** La sesión utilizada para los productores de mensajes. */
	private Session producersSession;
	/** La sesión utilizada para la recepción de mensajes. */
	private Session consumersSession;
	
	/**
	 * Constructor por default.
	 * 
	 * @param configFileLocation
	 *            La ubicación del archivo de configuración.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar el archivo de configuración.
	 */
	public JmsComponentFactory(final String configFileLocation) throws FileNotFoundException {
		BmvMq config = BmvMqConfigurationReader.readConfiguration(configFileLocation);
		BmvMqContext.setConfiguration(config);
		
		ConnectionFactory connectionFactory = getConnectionFactory(config);
		try {
			connection = getConnection(connectionFactory, config);
			int ackMode = getAckModeValue(config.getAcknowledgeMode());
			boolean transactedSession = config.getAcknowledgeMode() == AcknowledgeModeType.SESSION_TRANSACTED;
			producersSession = connection.createSession(transactedSession, ackMode);
			consumersSession = connection.createSession(transactedSession, ackMode);
		} catch (JMSException e) {
			LOGGER.error("Ocurrió un error durante la creación de la sesión", e);
			throw new SessionCreationFailureException("Ocurrió un error durante la creación de la sesion", e);
		}
	}
	
	/**
	 * Obtiene el valor en JMS del tipo de acuse de recibido.
	 * 
	 * @param acknowledgeModeType
	 *            El tipo de acuse configurado.
	 * @return El valor del tipo de acuse.
	 */
	private int getAckModeValue(final AcknowledgeModeType acknowledgeModeType) {
		switch (acknowledgeModeType) {
		case AUTO_ACKNOWLEDGE:
			return Session.AUTO_ACKNOWLEDGE;
		case CLIENT_ACKNOWLEDGE:
			return Session.CLIENT_ACKNOWLEDGE;
		case DUPS_OK_ACKNOWLEDGE:
			return Session.DUPS_OK_ACKNOWLEDGE;
		case SESSION_TRANSACTED:
			return Session.SESSION_TRANSACTED;
		default:
			break;
		}
		throw new InvalidBmvMqConfigurationException("Modo de acuse incorrecto: " + acknowledgeModeType);
	}
	
	/**
	 * Obtiene la fábrica de conexiones hacia el broker JMS dependiendo del
	 * proveedor. Esta fábrica debe ser capaz de generar conexiones tolerantes a
	 * fallos, configurandola de acuerdo a la documentación de cada proveedor
	 * JMS.
	 * 
	 * @param config
	 *            La configuración de BmvMQ.
	 * @return La fábrica de conexiones del proveedor JMS.
	 */
	protected abstract ConnectionFactory getConnectionFactory(final BmvMq config);
	
	/**
	 * Obtiene una conexión inicializada de la fábrica de conexiones.
	 * @param connectionFactory La fábrica de conexiones.
	 * @param config La configuración de BmvMQ.
	 * @return Una conexión lista para usarse.
	 */
	private Connection getConnection(final ConnectionFactory connectionFactory, final BmvMq config) {
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			setProprietaryConnectionParams(connection);
			connection.start();
			LOGGER.info("Conexión establecida.");
		} catch (JMSException e) {
			LOGGER.error("No se ha podido crear una conexión de ActiveMQ", e);
			throw new ConnectionCreationFailureException("No se ha podido crear una conexión de ActiveMQ", e);
		}
		return connection;
	}
	
	/**
	 * Asigna parámetros propietarios del proveedor a la conexión indicada.
	 * 
	 * @param connection
	 *            La conexión hacia el broker JMS.
	 * @throws JMSException
	 *             Si ocurre algún error durante la asignación de los
	 *             parámetros.
	 */
	protected abstract void setProprietaryConnectionParams(final Connection connection) throws JMSException;
	
	/**
	 * Obtiene un creador de mensajes para la cola indicada.
	 * 
	 * @param destination
	 *            El nombre del queue donde se enviará el mensaje.
	 * @return Un message creator para el tópico indicado.
	 * @throws JMSException
	 *             Si ocurre un error interno al crear el publicador.
	 */
	public final AbstractMessageCreator createQueueMessageCreator(final String destination) throws JMSException {
		Destination queue = producersSession.createQueue(destination);
		MessageProducer sender = producersSession.createProducer(queue);
		AbstractMessageCreator messageCreator = new QueueMessageCreator(producersSession, sender, destination);
		LOGGER.info("Publicador creado hacia la cola {}", destination);
		return messageCreator;
	}
	
	/**
	 * Obtiene un creador de mensajes para el topico indicado.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se enviará el mensaje.
	 * @return Un messageCreator para el tópico indicado.
	 * @throws JMSException
	 *             Si ocurre un error interno al crear el publicador.
	 */
	public final AbstractMessageCreator createTopicMessageCreator(final String destination) throws JMSException {
		Destination topic = producersSession.createTopic(destination);
		MessageProducer publisher = producersSession.createProducer(topic);
		AbstractMessageCreator messageCreator = new TopicMessageCreator(producersSession, publisher, destination);
		LOGGER.info("Publicador creado hacia el topico {}", destination);
		return messageCreator;
	}
	
	/**
	 * Crea un consumidor de mensajes para la cola indicada.
	 * 
	 * @param destination
	 *            El nombre del queue donde se enviará el mensaje.
	 * @param messageListener
	 *            El listener donde se entregarán los mensajes.
	 * @return El adaptador que entregará el mensaje al messageListener
	 *         proporcionado.
	 * @throws JMSException
	 *             Si ocurre un error al crear el consumidor del queue.
	 */
	public final MessageConsumer createQueueConsumer(final String destination,
			final BmvMqMessageListener messageListener) throws JMSException {
		Destination queue = consumersSession.createQueue(destination);
		MessageListenerAdapter adapter = createMessageListenerAdapter(messageListener);
		MessageConsumer consumer = consumersSession.createConsumer(queue);
		consumer.setMessageListener(adapter);
		LOGGER.info("Consumidor creado hacia la cola {}", destination);
		return consumer;
	}
	
	/**
	 * Crea un adaptador de recepción de mensajes de acuerdo al MessageListener
	 * indicado por el cliente BmvMQ.
	 * 
	 * @param messageListener
	 *            El message listener donde se entregaran los mensajes.
	 * @return Un MessageListenerAdapter que entrega únicamente el cuerpo del
	 *         mensaje u otro que entrega el mensaje JMS dependiendo del tipo de
	 *         BmvMqMessageListener recibido.
	 */
	protected final MessageListenerAdapter createMessageListenerAdapter(final BmvMqMessageListener messageListener) {
		if (messageListener instanceof MessageListener) {
			return new BodyMessageListenerAdapter(consumersSession, (MessageListener) messageListener);
		} else {
			return new RawMessageListenerAdapter(consumersSession, (RawMessageListener) messageListener);
		}
	}
	
	/**
	 * Crea un consumidor exclusivo de mensajes para la cola indicada.
	 * 
	 * @param destination
	 *            El nombre del queue donde se enviará el mensaje.
	 * @param messageListener
	 *            El listener donde se entregarán los mensajes.
	 * @return El adaptador que entregará el mensaje al messageListener
	 *         proporcionado.
	 * @throws JMSException
	 *             Si ocurre un error al crear el consumidor del queue.
	 */
	public abstract MessageConsumer createExclusiveQueueConsumer(String destination, 
			BmvMqMessageListener messageListener) throws JMSException;
	
	/**
	 * Crea un consumidor de mensajes para el tópico indicado.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se publicará el mensaje.
	 * @param messageListener
	 *            El listener donde se entregarán los mensajes.
	 * @return El adaptador que entregará el mensaje al messageListener
	 *         proporcionado.
	 * @throws JMSException
	 *             Si ocurre un error al crear el consumidor del tópico.
	 */
	public final MessageConsumer createTopicConsumer(final String destination,
			final BmvMqMessageListener messageListener) throws JMSException {
		Destination topic = consumersSession.createTopic(destination);
		MessageListenerAdapter adapter = createMessageListenerAdapter(messageListener);
		MessageConsumer consumer = consumersSession.createConsumer(topic);
		consumer.setMessageListener(adapter);
		return consumer;
	}
	
	/**
	 * Crea un consumidor de mensajes durable para el tópico indicado.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se publicará el mensaje.
	 * @param durableSubscriptionName
	 *            El nombre de la suscripción durable.
	 * @param messageListener
	 *            El listener donde se entregarán los mensajes.
	 * @return El adaptador que entregará el mensaje al messageListener
	 *         proporcionado.
	 * @throws JMSException
	 *             Si ocurre un error al crear el consumidor del tópico.
	 */
	public final MessageConsumer createDurableSubscription(
			final String destination, final String durableSubscriptionName,
			final BmvMqMessageListener messageListener) throws JMSException {
		Topic topic = consumersSession.createTopic(destination);
		MessageListenerAdapter adapter = createMessageListenerAdapter(messageListener);
		MessageConsumer consumer = consumersSession.createDurableSubscriber(topic, durableSubscriptionName);
		consumer.setMessageListener(adapter);
		return consumer;
	}
	
	/**
	 * Termina todas las conexiones, sesiones, productores y consumidores.
	 */
	public void stop() {
		closeSession(consumersSession);
		closeSession(producersSession);
		try {
			connection.stop();
		} catch (JMSException e) {
			LOGGER.error("Error al terminar la conexión", e);
		}
	}
	
	/**
	 * Cierra la sesión indicada.
	 * @param session La sesion a cerrar.
	 */
	private void closeSession(final Session session) {
		try {
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
			LOGGER.error("Error al cerrar la sesion", e);
		}
	}

	/**
	 * @return the producersSession
	 */
	public final Session getProducersSession() {
		return producersSession;
	}

	/**
	 * @return the consumersSession
	 */
	protected final Session getConsumersSession() {
		return consumersSession;
	}
	
}
