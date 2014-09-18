/**
 * Bursatec - BMV Sep 10, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

/**
 * @author gus
 *
 */
public abstract class AbstractMessageCreator implements MessageCreator {

	/**
	 * Las propiedades del mensaje.
	 */
	private String messageGroup;

	/**
	 * Constructor por default.
	 */
	public AbstractMessageCreator() { }
	/**
	 * @param messageGroup El grupo al que pertenece el mensaje.
	 */
	public AbstractMessageCreator(final String messageGroup) {
		this.messageGroup = messageGroup;
	}

	@Override
	public final Message createMessage(final Session session) throws JMSException {
		Message message = createConcreteMessage(session);
		if (messageGroup != null) {
			message.setStringProperty("JMSXGroupID", this.messageGroup);
		}
		return message;
	}
	
	/**
	 * Crea el mensaje a ser enviado.
	 * @param session La sesión JMS que se usará para crear el mensaje.
	 * @return El mensaje a ser enviado.
	 * @throws JMSException Arrojada al usar algún método de JMS.
	 */
	protected abstract Message createConcreteMessage(Session session) throws JMSException;

}
