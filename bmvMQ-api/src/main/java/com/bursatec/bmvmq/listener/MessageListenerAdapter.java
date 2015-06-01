/**
 * Bursatec - BMV Apr 8, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.transaction.TransactionSynchronizationManager;

/**
 * Receptor de mendajes JMS que funge como adaptador para entregar los mensajes
 * al cliente de BmvMQ tomando como responsabilidad los acuses de recibo de los
 * mensajes. Esto permite que los clientes BmvMQ se olviden de la
 * responsabilidad de acusar los mensajes.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public abstract class MessageListenerAdapter implements javax.jms.MessageListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerAdapter.class);
	/** La sesion JMS para recepción de mensajes. */
	private Session session;
	/** El tipo de acuse de recibido. */
	private AcknowledgeModeType acknowledgeModeType;

	/**
	 * Constructor por default.
	 * @param session La sesión JMS para recepción de mensajes.
	 */
	protected MessageListenerAdapter(final Session session) {
		this.session = session;
		this.acknowledgeModeType = BmvMqContext.getConfiguration().getAcknowledgeMode();
	}
	
	@Override
	public final void onMessage(final Message message) {
		TransactionSynchronizationManager.bindSession(session);
		try {
			if (message instanceof TextMessage) {
				deliver((TextMessage) message);
			} else if (message instanceof BytesMessage) {
				deliver((BytesMessage) message);
			} else if (message instanceof ObjectMessage) {
				deliver((ObjectMessage) message);
			} else {
				LOGGER.warn("Se recibió un mensaje no soportado por BmvMQ: " + message.getJMSType());
			}
			acknowledgeMessage(message);
		} catch (JMSException e) {
			LOGGER.error("Ocurrió un error al recuperar el mensaje recibido.", e);
			rollback();
		} catch (RuntimeException e) {
			LOGGER.error("Se ha recibido una excepción de la aplicación cliente.", e);
			rollback();
		} finally {
			TransactionSynchronizationManager.unbindSession();
		}
	}

	/**
	 * Entrega el mensaje serializable recibido al MessageListener del cliente.
	 * 
	 * @param objectMessage
	 *            El mensaje a entregar al cliente.
	 * @throws JMSException
	 *             Si el proveedor JMS falla al obtener el mensaje.
	 */
	protected abstract void deliver(final ObjectMessage objectMessage) throws JMSException;

	/**
	 * Entrega el mensaje en forma de arreglo de bytes recibido al
	 * MessageListener del cliente.
	 * 
	 * @param bytesMessage
	 *            El mensaje a entregar al cliente.
	 * @throws JMSException
	 *             Si el proveedor JMS falla al obtener el mensaje.
	 */
	protected abstract void deliver(final BytesMessage bytesMessage) throws JMSException;

	/**
	 * Entrega el mensaje de texto recibido al MessageListener del cliente.
	 * 
	 * @param textMessage
	 *            El mensaje a entregar al cliente.
	 * @throws JMSException
	 *             Si el proveedor JMS falla al obtener el mensaje.
	 */
	protected abstract void deliver(final TextMessage textMessage) throws JMSException;

	/**
	 * Deshace la transacción.
	 */
	private void rollback() {
		if (acknowledgeModeType == AcknowledgeModeType.SESSION_TRANSACTED) {
			try {
				session.rollback();
			} catch (JMSException e) {
				LOGGER.error("Ocurrió un error al deshacer la transacción", e);
			}
		}
	}

	/**
	 * Acusa el mensaje de recibido dependiendo del tipo de acuse.
	 * 
	 * @param message
	 *            El mensaje a acusar de recibido.
	 * @throws JMSException
	 *             Si ocurre algun error interno durante el acuse.
	 */
	private void acknowledgeMessage(final Message message) throws JMSException {
		switch (acknowledgeModeType) {
		case CLIENT_ACKNOWLEDGE:
			message.acknowledge();
			break;
		case SESSION_TRANSACTED:
			session.commit();
			break;
		default:
			break;
		}
	}

}
