/**
 * Bursatec - BMV Apr 21, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.message;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.bursatec.bmvmq.jmx.stats.JmsConsumerStats;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.MessageListenerAdapter;

/**
 * Receptor de mensajes que obtiene el cuerpo de los mensajes recibidos y los
 * entrega al MesageListener registrado por el cliente de BmvMQ.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public class BodyMessageListenerAdapter extends MessageListenerAdapter {

	/** El message listener de la aplicación cliente. */
	private MessageListener messageListener;
	
	/**
	 * Constructor por default.
	 * 
	 * @param session
	 *            La sesión JMS para recepción de mensajes.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes.
	 * @param stats
	 *            El recoelctor de estadísticas de consumidor.
	 */
	public BodyMessageListenerAdapter(final Session session, final MessageListener messageListener, 
			final JmsConsumerStats stats) {
		super(session, stats);
		this.messageListener = messageListener;
	}

	@Override
	protected final void deliver(final ObjectMessage objectMessage) throws JMSException {
		messageListener.onMessage(objectMessage.getObject());
	}

	@Override
	protected final void deliver(final BytesMessage bytesMessage) throws JMSException {
		byte[] content = new byte[(int) bytesMessage.getBodyLength()];
		bytesMessage.readBytes(content);
		messageListener.onMessage(content);
	}

	@Override
	protected final void deliver(final TextMessage textMessage) throws JMSException {
		messageListener.onMessage(textMessage.getText());
	}

}
