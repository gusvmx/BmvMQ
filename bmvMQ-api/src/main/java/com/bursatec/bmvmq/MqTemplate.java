/**
 * Bursatec - BMV Sep 5, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.core.AbstractMessageCreator;
import com.bursatec.bmvmq.exception.ConsumerCreationFailureException;
import com.bursatec.bmvmq.exception.MessageCreatorCreationFailureException;
import com.bursatec.bmvmq.exception.SendMessageFailureException;
import com.bursatec.bmvmq.factory.JmsComponentFactory;
import com.bursatec.bmvmq.jmx.MBeanFactory;
import com.bursatec.bmvmq.listener.BmvMqConnStateListener;
import com.bursatec.bmvmq.listener.BmvMqMessageListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.RawMessageListener;
import com.bursatec.bmvmq.message.MessageConstants;
import com.bursatec.bmvmq.message.MessagePropertySetter;

/**
 * Interface principal de BmvMQ que ofrece métodos para enviar/recibir y
 * publicar/suscribir mensajes a queues y tópicos respectivamente.
 * 
 * @author Gustavo Vargas
 *
 */
public final class MqTemplate {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(MqTemplate.class);
	/***/
	public static final String DEFAULT_CONFIG_FILE_LOCATION = "classpath:/bmvMq.xml";
	/** La fabrica de componentes JMS. */
	private JmsComponentFactory componentFactory;
	/***/
	private Map<String, AbstractMessageCreator> queueMessageCreators = new HashMap<String, AbstractMessageCreator>();
	/***/
	private Map<String, AbstractMessageCreator> topicMessageCreators = new HashMap<String, AbstractMessageCreator>();
	/***/
	private Map<String, MessageConsumer> queueMessageConsumers = new HashMap<String, MessageConsumer>();
	/***/
	private Map<String, MessageConsumer> topicMessageConsumers = new HashMap<String, MessageConsumer>();
	
	/**
	 * Constructor por default.
	 * @param componentFactory La fábrica de componentes JMS.
	 */
	protected MqTemplate(final JmsComponentFactory componentFactory) {
		this.componentFactory = componentFactory;
	}
	
	/**
	 * Publica el objeto serializable hacia el topico indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se publicará el mensaje.
	 * @param message
	 *            El objeto serializable que se publicará.
	 */
	public final void publish(final String destination, final Serializable message) {
		publish(destination, message, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia el tópico indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se publicará el mensaje.
	 * @param message
	 *            El objeto serializable que se publicará.
	 * @param messagePropertySetter
	 *            Asignador de propiedades para el mensaje.
	 */
	public final void publish(final String destination, final Serializable message, 
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getTopicMessageCreator(destination);
		try {
			messageCreator.send(message, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible publicar el mensaje al topico " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("Serializable object message published to the topic {}", destination);
	}
	
	/**
	 * Obtiene el creador de mensajes para el tópico indicado.
	 * 
	 * @param destination El nombre del tópico hacia donde se publicaran los mensajes.
	 * @return Un message creator para el tópico indicado.
	 */
	private AbstractMessageCreator getTopicMessageCreator(final String destination) {
		AbstractMessageCreator messageCreator = topicMessageCreators.get(destination);
		if (messageCreator == null) {
			try {
				messageCreator = componentFactory.createTopicMessageCreator(destination);
			} catch (JMSException e) {
				String desc = "No ha podido ser posible crear un creador de mensajes del topico " + destination;
				LOGGER.error(desc, e);
				throw new MessageCreatorCreationFailureException(desc, e);
			}
			topicMessageCreators.put(destination, messageCreator);
		}
		return messageCreator;
	}
	
	/**
	 * Publica el mensaje de texto hacia el topico indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param text El mensaje a publicar.
	 */
	public final void publish(final String destination, final String text) {
		publish(destination, text, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia el tópico indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param text El mensaje a publicar.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void publish(final String destination, final String text, 
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getTopicMessageCreator(destination);
		try {
			messageCreator.send(text, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible publicar el mensaje al topico " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("String message published to the topic {}", destination);
	}
	
	/**
	 * Publica el arreglo de bytes hacia el tópico indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre del tópico donde se publicará el mensaje.
	 * @param message
	 *            El mensaje en su representación de arreglo de bytes a
	 *            publicar.
	 */
	public final void publish(final String destination, final byte[] message) {
		publish(destination, message, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia el tópico indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a publicar.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void publish(final String destination, final byte[] message, 
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getTopicMessageCreator(destination);
		try {
			messageCreator.send(message, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible publicar el mensaje al topico " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("Byte array message published to the topic {}", destination);
	}
	
	/**
	 * Envía el objeto serializable hacia el queue indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 */
	public final void send(final String destination, final Serializable message) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, 
				MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Envía el objeto serializable hacia el queue indicado con el id de grupo indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 */
	public final void send(final String destination, final Serializable message, final String messageGroup) {
		send(destination, message, messageGroup, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final Serializable message, 
			final MessagePropertySetter messagePropertySetter) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada asignando el grupo indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final Serializable message, final String messageGroup,
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getQueueMessageCreator(destination);
		try {
			messageCreator.send(message, messageGroup, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible enviar el mensaje al queue " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("Serializable object message sent to the queue {}", destination);
	}
	
	/**
	 * Crea un creador de mensajes para el queue indicado.
	 * @param destination El nombre del queue.
	 * @return Un message creator para el queue indicado.
	 */
	private AbstractMessageCreator getQueueMessageCreator(final String destination) {
		AbstractMessageCreator messageCreator = queueMessageCreators.get(destination);
		if (messageCreator == null) {
			try {
				messageCreator = componentFactory.createQueueMessageCreator(destination);
			} catch (JMSException e) {
				String desc = "No ha podido ser posible crear un creador de mensajes del queue " + destination;
				LOGGER.error(desc, e);
				throw new MessageCreatorCreationFailureException(desc, e);
			}
			queueMessageCreators.put(destination, messageCreator);
		}
		return messageCreator;
	}
	
	/**
	 * Envía el mensaje hacia el queue indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre de la cola donde se enviará el mensaje.
	 * @param message
	 *            El mensaje a enviar.
	 */
	public final void send(final String destination, final String message) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, 
				MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Envía el mensaje hacia el queue indicado con el id de grupo indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre de la cola donde se enviará el mensaje.
	 * @param message
	 *            El mensaje a enviar.
	 * @param messageGroup
	 *            Identificador del grupo al que pertenece el mensaje.
	 */
	public final void send(final String destination, final String message, final String messageGroup) {
		send(destination, message, messageGroup, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje a enviar.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final String message,
			final MessagePropertySetter messagePropertySetter) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada asignando el grupo indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje a enviar.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final String message, final String messageGroup,
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getQueueMessageCreator(destination);
		try {
			messageCreator.send(message, messageGroup, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible enviar el mensaje al queue " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("String message sent to the queue {}", destination);
	}
	
	/**
	 * Envía el arreglo de bytes hacia el queue indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre de la cola donde se enviará el mensaje.
	 * @param message
	 *            El mensaje en su representación de arreglo de bytes a enviar.
	 */
	public final void send(final String destination, final byte[] message) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, 
				MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Envía el arreglo de bytes hacia el queue indicado con el id de grupo
	 * indicado.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination
	 *            El nombre de la cola donde se enviará el mensaje.
	 * @param message
	 *            El mensaje en su representación de arreglo de bytes a enviar.
	 * @param messageGroup
	 *            Identificador del grupo al que pertenece el mensaje.
	 */
	public final void send(final String destination, final byte[] message, final String messageGroup) {
		send(destination, message, messageGroup, MessagePropertySetter.BLANK_MESSAGE_PROPERTY_SETTER);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a enviar.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final byte[] message, 
			final MessagePropertySetter messagePropertySetter) {
		send(destination, message, MessageConstants.DEFAULT_GROUP_ID, messagePropertySetter);
	}
	
	/**
	 * Publica el mensaje hacia la cola indicada asignando el grupo indicado.
	 * 
	 * Adicionalmente ejecuta el callback {@link MessagePropertySetter} para
	 * personalizar el mensaje JMS a enviar.
	 * 
	 * Este método puede bloquear al hilo actual si no existe una conexión
	 * establecida con el broker JMS.
	 * 
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a enviar.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 * @param messagePropertySetter Asignador de propiedades para el mensaje.
	 */
	public final void send(final String destination, final byte[] message, final String messageGroup,
			final MessagePropertySetter messagePropertySetter) {
		AbstractMessageCreator messageCreator = getQueueMessageCreator(destination);
		try {
			messageCreator.send(message, messageGroup, messagePropertySetter);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible enviar el mensaje al queue " + destination;
			LOGGER.error(desc, e);
			throw new SendMessageFailureException(desc, e);
		}
		LOGGER.debug("Byte array message sent to the queue {}", destination);
	}
	
	/**
	 * Recibe exclusivamente del queue indicado. Por lo tanto, nadie más podrá
	 * recibir de ese Queue hasta que el actual desista.
	 * 
	 * @param destinationName
	 *            El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes recibidos.
	 */
	public final void receiveExclusively(final String destinationName,
			final MessageListener messageListener) {
		createExclusiveQueueConsumer(destinationName, messageListener);
	}
	
	/**
	 * Recibe exclusivamente del queue indicado. Por lo tanto, nadie más podrá
	 * recibir de ese Queue hasta que el actual desista.
	 * 
	 * @param destinationName
	 *            El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes en crudo (JMS) recibidos.
	 */
	public final void receiveExclusively(final String destinationName,
			final RawMessageListener messageListener) {
		createExclusiveQueueConsumer(destinationName, messageListener);
	}
	
	/**
	 * Crea el receptor de mensajes de forma exclusiva para el queue indicado.
	 * @param destinationName El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos.
	 */
	private void createExclusiveQueueConsumer(final String destinationName, 
			final BmvMqMessageListener messageListener) {
		try {
			MessageConsumer consumer = componentFactory.createExclusiveQueueConsumer(destinationName, messageListener);
			queueMessageConsumers.put(destinationName, consumer);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible crear un consumidor exclusivo del queue " + destinationName;
			LOGGER.error(desc, e);
			throw new ConsumerCreationFailureException(desc, e);
		}
		LOGGER.info("Connection established to the queue {} as exclusive consumer. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destinationName, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * Comienza a recibir mensajes del Queue indicado.
	 * 
	 * @param destination El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos.
	 */
	public final void receive(final String destination,
			final MessageListener messageListener) {
		createQueueConsumer(destination, messageListener);
	}
	
	/**
	 * Comienza a recibir mensajes del Queue indicado.
	 * 
	 * @param destination El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes crudos (JMS) recibidos.
	 */
	public final void receive(final String destination,
			final RawMessageListener messageListener) {
		createQueueConsumer(destination, messageListener);
	}
	
	/**
	 * @param destination El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos.
	 */
	private void createQueueConsumer(final String destination, final BmvMqMessageListener messageListener) {
		try {
			MessageConsumer consumer = componentFactory.createQueueConsumer(destination, messageListener);
			queueMessageConsumers.put(destination, consumer);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible crear un consumidor del queue " + destination;
			LOGGER.error(desc, e);
			throw new ConsumerCreationFailureException(desc, e);
		}
		LOGGER.info("Connection established to the queue {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * Se suscribe al topico indicado y comienza a recibir mensajes.
	 * 
	 * @param destination El nombre del tópico al que se realizará la suscripción.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos en la suscripción.
	 */
	public final void subscribe(final String destination,
			final MessageListener messageListener) {
		createTopicConsumer(destination, messageListener);
	}
	
	/**
	 * Se suscribe al topico indicado y comienza a recibir mensajes.
	 * 
	 * @param destination El nombre del tópico al que se realizará la suscripción.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos en la suscripción.
	 */
	public final void subscribe(final String destination,
			final RawMessageListener messageListener) {
		createTopicConsumer(destination, messageListener);
	}
	
	/**
	 * @param destination El nombre del tópico al que se realizará la suscripción.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos en la suscripción.
	 */
	private void createTopicConsumer(final String destination, final BmvMqMessageListener messageListener) {
		MessageConsumer consumer = null;
		try {
			consumer = componentFactory.createTopicConsumer(destination, messageListener);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible crear un consumidor del topico " + destination;
			LOGGER.error(desc, e);
			throw new ConsumerCreationFailureException(desc, e);
		}
		topicMessageConsumers.put(destination, consumer);
		LOGGER.info("Subscribed successfully to the topic {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * Se suscribe al tópico indicado de manera durable. Esto significa que el
	 * cliente se registra en el broker JMS con un único identificador. Esto
	 * hace que el broker almacene todos los mensajes que pudieran llegar
	 * mientras el suscriptor este fuera de línea. En cuanto el suscriptor
	 * vuelva a estar en línea, todos los mensajes que fluyeron en el tópico les
	 * serán entregados.
	 * 
	 * 
	 * Las suscripciones durables cuentan con las siguientes limitaciones. Un
	 * consumidor se crea con un único clientId y nombre de suscripción durable.
	 * Para cumplir con la especificación JMS, sólo una conexión puede estar
	 * activa a la vez para un clientId y sólo un consumidor puede estar activo
	 * para un clientId y nombre de suscripción. En otras palabras, sólo un hilo
	 * puede estar activamente consumiendo de un tópico.
	 * 
	 * 
	 * Como resultado, 2 consumidores no pueden suscribirse a la misma
	 * suscripción durable. Tener multiples consumidores para balanceo de carga,
	 * multi-threading o disponibilidad no está permitido por la especificación
	 * JMS.
	 * 
	 * 
	 * *Reemplazar suscripciones durables con queues y tópicos virtuales* Un
	 * tópico virtual es creado y mapeado hacia uno o más queues. Esto permite
	 * el balanceo de carga para los mensajes provenientes de ese tópico. Además
	 * ofrece alta disponibilidad, si los consumidores se caen, los mensajes
	 * publicados hacia el tópico están disponibles debido a la naturaleza
	 * persistente del queue, por lo tanto, los mensajes no se pierden.
	 * 
	 * @param destination
	 *            El nombre del tópico al que se realizará la suscripción
	 *            durable.
	 * @param durableSubscriptionName
	 *            El nombre de la suscripción durable.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes recibidos en la
	 *            suscripción.
	 */
	public final void durableSubscription(final String destination, final String durableSubscriptionName,
			final MessageListener messageListener) {
		createDurableSubscription(destination, durableSubscriptionName, messageListener);
	}
	
	/**
	 * Se suscribe al tópico indicado de manera durable. Esto significa que el
	 * cliente se registra en el broker JMS con un único identificador. Esto
	 * hace que el broker almacene todos los mensajes que pudieran llegar
	 * mientras el suscriptor este fuera de línea. En cuanto el suscriptor
	 * vuelva a estar en línea, todos los mensajes que fluyeron en el tópico les
	 * serán entregados.
	 * 
	 * @param destination
	 *            El nombre del tópico al que se realizará la suscripción
	 *            durable.
	 * @param durableSubscriptionName
	 *            El nombre de la suscripción durable.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes recibidos en la
	 *            suscripción.
	 */
	public final void durableSubscription(final String destination, final String durableSubscriptionName,
			final RawMessageListener messageListener) {
		createDurableSubscription(destination, durableSubscriptionName, messageListener);
	}
	
	/**
	 * Establece la suscripción durable asociado con el message listener
	 * indicado.
	 * 
	 * @param destination
	 *            El nombre del tópico al que se realizará la suscripción
	 *            durable.
	 * @param durableSubscriptionName
	 *            El nombre de la suscripción durable.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes recibidos en la
	 *            suscripción.
	 */
	private void createDurableSubscription(final String destination, final String durableSubscriptionName,
			final BmvMqMessageListener messageListener) {
		MessageConsumer consumer = null;
		try {
			consumer = componentFactory.createDurableSubscription(destination, durableSubscriptionName, 
					messageListener);
		} catch (JMSException e) {
			String desc = "No ha podido ser posible crear un suscriptor durable del topico " + destination;
			LOGGER.error(desc, e);
			throw new ConsumerCreationFailureException(desc, e);
		}
		topicMessageConsumers.put(destination, consumer);
		LOGGER.info("Durable subscription established successfully to the topic {}. "
				+ "Messages will be delivered to the instance of the class {} with name {}", 
				destination, messageListener.getClass().getName(), messageListener.toString());
	}
	
	/**
	 * Detiene la recepción de mensajes del queue indicado.
	 * 
	 * @param destination El nombre del queue del que se dejará de recibir mensajes.
	 */
	public final void stopReceiving(final String destination) {
		MessageConsumer queueConsumer = queueMessageConsumers.get(destination);
		stop(queueConsumer, destination);
		MBeanFactory.unregisterMbeans(MBeanFactory.buildReceiverName(destination));
	}
	
	/**
	 * Finaliza el consumidor indicado.
	 * @param consumer El consumidor que se cerrará.
	 * @param destinationName El nombre del destino.
	 */
	private void stop(final MessageConsumer consumer, final String destinationName) {
		if (consumer != null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				LOGGER.error("Ocurrió un error al cerrar el consumidor del destino {}.", destinationName, e);
			}
			LOGGER.info("Se ha dejado de recibir mensajes del queue {}", destinationName);
		} else {
			LOGGER.warn("Se intento dejar de recibir de un consumidor de mensajes para el queue {}.", destinationName);
		}
	}
	
	/**
	 * Detiene la suscripción de mensajes del topico indicado.
	 * 
	 * @param destination El nombre del topico del que se desuscribirá para dejar de recibir mensajes.
	 */
	public final void unsubscribe(final String destination) {
		MessageConsumer topicConsumer = topicMessageConsumers.get(destination);
		stop(topicConsumer, destination);
		MBeanFactory.unregisterMbeans(MBeanFactory.buildSubscriberName(destination));
	}
	
	/**
	 * Termina todas las conexiones, sesiones, productores y consumidores.
	 */
	public final void stop() {
		componentFactory.stop();
		MBeanFactory.unregisterMbeans(MBeanFactory.QUERY_ALL_BMVMQ_BEANS);
	}
	
	/**
	 * @param exceptionListener El listener donde se notificaran los errores.
	 */
	public final void setExceptionListener(final ExceptionListener exceptionListener) {
		if (exceptionListener == null) {
			throw new NullPointerException("Se requiere una instancia de BmvMqExceptionListener");
		}
		BmvMqContext.setExceptionListener(exceptionListener);
	}
	
	/**
	 * @param connectionListener El listener donde se notificarán los eventos de conexión.
	 */
	public final void setConnectionStateListener(final BmvMqConnStateListener connectionListener) {
		if (connectionListener == null) {
			throw new NullPointerException("Se requiere una instancia de BmvMqConnStateListener");
		}
		BmvMqContext.setConnectionListener(connectionListener);
	}

}
