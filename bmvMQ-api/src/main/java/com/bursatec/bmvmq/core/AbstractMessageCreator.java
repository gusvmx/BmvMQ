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

import com.bursatec.bmvmq.transaction.TransactionSynchronizationManager;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public abstract class AbstractMessageCreator {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageCreator.class);
	/***/
	private Session session;
	/***/
	private MessageProducer defaultProducer;
	/***/
	private String destinationName;
	/**
	 * @param session La sesion por default
	 * @param defaultProducer El productor de mensajes por default.
	 * @param destinationName El nombre del destino.
	 */
	public AbstractMessageCreator(final Session session, final MessageProducer defaultProducer,
			final String destinationName) {
		this.session = session;
		this.defaultProducer = defaultProducer;
		this.destinationName = destinationName;
	}
	
	/**
	 * @return El productor de mensajes que se utilizará para enviar un mensaje.
	 * @throws JMSException Si ocurre un error interno durante la creación del productor de mensajes.
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
	 * @param text El cuerpo del mensaje.
	 * @throws JMSException Si ocurre un error interno durante la creación del mensaje.
	 */
	public final void send(final String text) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createTextMessage(text);
		sendMessage(producer, message);
	}

	/**
	 * @param producer El productor de mensajes.
	 * @param message El mensaje a enviar.
	 * @throws JMSException Si ocurre algún problema al enviar el mensaje o dar commit.
	 */
	private void sendMessage(final MessageProducer producer, final Message message) throws JMSException {
		producer.send(message);
		if (session.getTransacted()) {
			if (!TransactionSynchronizationManager.isSessionBound()) {
				session.commit();
			}
		}
	}

	/**
	 * @param text El cuerpo del mensaje.
	 * @param messageGroupId El identificador del grupo del mensaje.
	 * @throws JMSException Si ocurre algun error interno durante la creación del mensaje.
	 */
	public final void send(final String text, final String messageGroupId) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createTextMessage(text);
		setGroupId(message, messageGroupId);
		sendMessage(producer, message);
	}

	/**
	 * @param message El mensaje al cual se agregará el identificador del grupo.
	 * @param messageGroupId El identificador del grupo del mensaje.
	 * @throws JMSException Si ocurre algun error interno durante la creación de la propiedad del mensaje.
	 */
	private void setGroupId(final Message message, final String messageGroupId) throws JMSException {
		message.setStringProperty("JMSXGroupID", messageGroupId);
		LOGGER.debug("The groupId {} has been set to the message", messageGroupId);
	}

	/**
	 * @param messageBytes El mensaje a enviar en forma de arreglo de bytes.
	 * @throws JMSException Si ocurre algun error interno durante la creación del mensaje.
	 */
	public final void send(final byte[] messageBytes) throws JMSException {
		MessageProducer producer = getProducer();
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(messageBytes);
		sendMessage(producer, message);
	}

	/**
	 * @param messageBytes El mensaje a enviar en forma de arreglo de bytes.
	 * @param messageGroupId El identificador del grupo del mensaje.
	 * @throws JMSException Si ocurre algun error interno durante la creación del mensaje.
	 */
	public final void send(final byte[] messageBytes, final String messageGroupId) throws JMSException {
		MessageProducer producer = getProducer();
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(messageBytes);
		setGroupId(message, messageGroupId);
		sendMessage(producer, message);
	}

	/**
	 * @param serializable El objeto serializable a enviar.
	 * @throws JMSException Si ocurre algun error interno durante la creación del mensaje.
	 */
	public final void send(final Serializable serializable) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createObjectMessage(serializable);
		sendMessage(producer, message);
	}

	/**
	 * @param serializable El objeto serializable a enviar.
	 * @param messageGroupId El identificador del grupo del mensaje.
	 * @throws JMSException Si ocurre algun error interno durante la creación del mensaje.
	 */
	public final void send(final Serializable serializable, final String messageGroupId) throws JMSException {
		MessageProducer producer = getProducer();
		Message message = session.createObjectMessage(serializable);
		setGroupId(message, messageGroupId);
		sendMessage(producer, message);
	}
	
	/**
	 * @param session La sesión con la que se creara el destino.
	 * @param destinationName El nombre del destino.
	 * @return Ya sea un Queue o un Topico dependiendo del destino deseado.
	 * @throws JMSException Si ocurre algun error interno durante la creación del destino.
	 */
	protected abstract Destination getDestination(Session session, String destinationName) throws JMSException;
	
}
