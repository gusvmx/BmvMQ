/**
 * Bursatec - BMV Sep 10, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author gus
 *
 */
public class SerializableMessageCreator extends AbstractMessageCreator {
	/**
	 * El mensaje a enviar.
	 */
	private Serializable serializable;
	/**
	 * @param serializable El mensaje a enviar.
	 */
	public SerializableMessageCreator(final Serializable serializable) {
		this.serializable = serializable;
	}
	/**
	 * @param serializable El mensaje a enviar.
	 * @param messageGroup El grupo al que pertenece el mensaje.
	 */
	public SerializableMessageCreator(final Serializable serializable, final String messageGroup) {
		super(messageGroup);
		this.serializable = serializable;
	}
	
	@Override
	public final Message createConcreteMessage(final Session session) throws JMSException {
		return session.createObjectMessage(serializable);
	}

}
