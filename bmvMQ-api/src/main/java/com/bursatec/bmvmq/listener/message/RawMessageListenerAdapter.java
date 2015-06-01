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

import com.bursatec.bmvmq.listener.MessageListenerAdapter;
import com.bursatec.bmvmq.listener.RawMessageListener;

/**
 * Receptor de mensajes que entrega tal cual los mensajes recibidos al
 * MesageListener registrado por el cliente de BmvMQ.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public class RawMessageListenerAdapter extends MessageListenerAdapter {

	/***/
	private RawMessageListener rawMessageListener;

	/**
	 * Constructor por default.
	 * 
	 * @param session
	 *            La sesión JMS para recepción de mensajes.
	 * @param rawMessageListener
	 *            El callback donde se entregarán los mensajes.
	 */
	public RawMessageListenerAdapter(final Session session,
			final RawMessageListener rawMessageListener) {
		super(session);
		this.rawMessageListener = rawMessageListener;
	}

	@Override
	protected final void deliver(final ObjectMessage objectMessage)
			throws JMSException {
		rawMessageListener.onMessage(objectMessage);
	}

	@Override
	protected final void deliver(final BytesMessage bytesMessage)
			throws JMSException {
		rawMessageListener.onMessage(bytesMessage);
	}

	@Override
	protected final void deliver(final TextMessage textMessage)
			throws JMSException {
		rawMessageListener.onMessage(textMessage);
	}

}
