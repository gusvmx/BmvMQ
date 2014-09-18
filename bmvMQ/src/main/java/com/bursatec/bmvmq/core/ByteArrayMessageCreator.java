/**
 * Bursatec - BMV Sep 10, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author gus
 *
 */
public class ByteArrayMessageCreator extends AbstractMessageCreator {
	
	/**
	 * El mensaje a enviar.
	 */
	private byte[] message;

	/**
	 * @param message El mensaje a enviar.
	 */
	public ByteArrayMessageCreator(final byte[] message) {
		this.message = message;
	}
	
	/**
	 * @param message El mensaje a enviar.
	 * @param messageGroup El grupo al que pertenece el mensaje.
	 */
	public ByteArrayMessageCreator(final byte[] message, final String messageGroup) {
		super(messageGroup);
		this.message = message;
	}

	@Override
	public final Message createConcreteMessage(final Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(this.message);
		return message;
	}

}
