/**
 * Bursatec - BMV Apr 8, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.jmx.stats.JmsProducerStats;
import com.bursatec.bmvmq.message.MessageConstants;
import com.bursatec.bmvmq.message.MessagePropertySetter;
import com.bursatec.bmvmq.transaction.TransactionSynchronizationManager;

/**
 * Despachador de mensajes JMS.
 * @author gus - Bursatec
 * @version 1.0
 */
public abstract class AbstractMessageCreator {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageCreator.class);
	/** La sesión con la que se crearán los mensajes. */
	private Session session;
	/** El productor de mensajes. */
	private MessageProducer defaultProducer;
	/** El nombre del destino, ya sea cola o tópico. */
	private String destinationName;
	/** Las estadísticas del publicador. */
	private JmsProducerStats stats;
	
	/**
	 * Constructor por default.
	 * 
	 * @param session
	 *            La sesion por default
	 * @param defaultProducer
	 *            El productor de mensajes por default.
	 * @param destinationName
	 *            El nombre del destino.
	 */
	public AbstractMessageCreator(final Session session, final MessageProducer defaultProducer,
			final String destinationName) {
		this.session = session;
		this.defaultProducer = defaultProducer;
		this.destinationName = destinationName;
	}
	
	/**
	 * Obtiene el productor de mensajes de acuerdo a las condiciones actuales.
	 * 
	 * Si existe alguna transacción iniciada por un receptor de mensajes, se
	 * creará un productor de mensajes con la sesión de ese receptor. De otro
	 * modo se utilizará el productor de mensajes por default.
	 * 
	 * @return El productor de mensajes que se utilizará para enviar un mensaje.
	 * @throws JMSException
	 *             Si ocurre un error interno durante la creación del productor
	 *             de mensajes.
	 */
	private MessageProducer getProducer() throws JMSException {
		MessageProducer producer = defaultProducer;
		if (TransactionSynchronizationManager.isSessionBound()) {
			Session currentSession = TransactionSynchronizationManager.getBoundSession();
			Destination destination = getDestination(session, destinationName);
			producer = currentSession.createProducer(destination);
		}
		return producer;
	}

	/**
	 * Envía un mensaje de texto.
	 * 
	 * @param text
	 *            El cuerpo del mensaje.
	 * @param messageGroupId
	 *            El identificador del grupo del mensaje.
	 * @param messagePropertySetter
	 *            El callback asignador de propiedades del mensaje.
	 * @throws JMSException
	 *             Si ocurre un error interno durante la creación del mensaje.
	 */
	public final void send(final String text, final String messageGroupId, 
			final MessagePropertySetter messagePropertySetter) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createTextMessage(text);
		sendMessage(producer, message, messageGroupId, messagePropertySetter);
	}

	/**
	 * Envía un mensaje JMS asignandole el identificador de grupo y ejecutando
	 * el messagePropertySetter.
	 * 
	 * Adicionalmente, si la sesión es transaccional y no existe una transacción
	 * en curso (de un receptor) se confirma el envío.
	 * 
	 * @param producer
	 *            El productor de mensajes.
	 * @param message
	 *            El mensaje a enviar.
	 * @param messageGroupId
	 *            El identificador del grupo del mensaje.
	 * @param messagePropertySetter
	 *            El callback asignador de propiedades del mensaje.
	 * @throws JMSException
	 *             Si ocurre algún problema al enviar el mensaje o dar commit.
	 */
	private void sendMessage(final MessageProducer producer, final Message message,
			final String messageGroupId, final MessagePropertySetter messagePropertySetter) throws JMSException {
		setGroupId(message, messageGroupId);
		messagePropertySetter.setProperties(message);
		producer.send(message);
		stats.messageDelivered();
		if (session.getTransacted()) {
			if (!TransactionSynchronizationManager.isSessionBound()) {
				session.commit();
			}
		}
	}

	/**
	 * Asigna el identificador de grupo para el mensaje.
	 * 
	 * @param message
	 *            El mensaje al cual se agregará el identificador del grupo.
	 * @param messageGroupId
	 *            El identificador del grupo del mensaje.
	 * @throws JMSException
	 *             Si ocurre algun error interno durante la creación de la
	 *             propiedad del mensaje.
	 */
	private void setGroupId(final Message message, final String messageGroupId) throws JMSException {
		if (messageGroupId != MessageConstants.DEFAULT_GROUP_ID) {
			message.setStringProperty("JMSXGroupID", messageGroupId);
			LOGGER.debug("The groupId {} has been set to the message", messageGroupId);
		}
	}

	/**
	 * Envía un mensaje de arreglo de bytes.
	 * 
	 * @param messageBytes
	 *            El mensaje a enviar en forma de arreglo de bytes.
	 * @param messageGroupId
	 *            El identificador del grupo del mensaje.
	 * @param messagePropertySetter
	 *            El callback asignador de propiedades del mensaje.
	 * @throws JMSException
	 *             Si ocurre algun error interno durante la creación del
	 *             mensaje.
	 */
	public final void send(final byte[] messageBytes, final String messageGroupId,
			final MessagePropertySetter messagePropertySetter) throws JMSException {
		MessageProducer producer = getProducer();
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(messageBytes);
		sendMessage(producer, message, messageGroupId, messagePropertySetter);
	}

	/**
	 * Envía un mensaje serializable.
	 * 
	 * @param serializable
	 *            El objeto serializable a enviar.
	 * @param messageGroupId
	 *            El identificador del grupo del mensaje.
	 * @param messagePropertySetter
	 *            El callback asignador de propiedades del mensaje.
	 * @throws JMSException
	 *             Si ocurre algun error interno durante la creación del
	 *             mensaje.
	 */
	public final void send(final Serializable serializable, final String messageGroupId,
			final MessagePropertySetter messagePropertySetter) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createObjectMessage(serializable);
		sendMessage(producer, message, messageGroupId, messagePropertySetter);
	}
	
	/**
	 * Crea el destino, queue o topico dependiendo de la clase concreta, con el
	 * nombre indicado.
	 * 
	 * @param session
	 *            La sesión con la que se creara el destino.
	 * @param destinationName
	 *            El nombre del destino.
	 * @return Ya sea un Queue o un Topico dependiendo del destino deseado.
	 * @throws JMSException
	 *             Si ocurre algun error interno durante la creación del
	 *             destino.
	 */
	protected abstract Destination getDestination(Session session, String destinationName) throws JMSException;

	/**
	 * @param stats the stats to set
	 */
	protected final void setStats(final JmsProducerStats stats) {
		this.stats = stats;
	}
	
}
