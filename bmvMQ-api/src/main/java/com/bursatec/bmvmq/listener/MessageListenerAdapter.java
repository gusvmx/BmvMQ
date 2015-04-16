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
 * @author gus - Bursatec
 * @version 1.0
 */
public class MessageListenerAdapter implements javax.jms.MessageListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerAdapter.class);
	/** La sesion JMS para recepción de mensajes. */
	private Session session;
	/** El message listener de la aplicación cliente. */
	private MessageListener messageListener;
	/** El tipo de acuse de recibido. */
	private AcknowledgeModeType acknowledgeModeType;

	/**
	 * @param session La sesión JMS para recepción de mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes.
	 */
	public MessageListenerAdapter(final Session session,
			final MessageListener messageListener) {
		this.session = session;
		this.messageListener = messageListener;
		this.acknowledgeModeType = BmvMqContext.getConfiguration().getAcknowledgeMode();
	}

	@Override
	public final void onMessage(final Message message) {
		TransactionSynchronizationManager.bindSession(session);
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				messageListener.onMessage(textMessage.getText());
			} else if (message instanceof BytesMessage) {
				BytesMessage bytesMessage = (BytesMessage) message;
				byte[] content = new byte[(int) bytesMessage.getBodyLength()];
				bytesMessage.readBytes(content);
				messageListener.onMessage(content);
			} else if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				messageListener.onMessage(objectMessage.getObject());
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
	 * @param message El mensaje a acusar de recibido.
	 * @throws JMSException Si ocurre algun error interno durante el acuse.
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
