/**
 * Bursatec - BMV Apr 16, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.listener.exception.EmptyExceptionListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class DefaultExceptionListener implements ExceptionListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionListener.class);
	/***/
	private ExceptionListener target;
	
	/***/
	public DefaultExceptionListener() {
		this.target = new EmptyExceptionListener();
	}
	/**
	 * @param target El listener destino.
	 */
	public DefaultExceptionListener(final ExceptionListener target) {
		this.target = target;
	}

	@Override
	public final void onException(final JMSException jmsException) {
		LOGGER.error(jmsException.getMessage(), jmsException);
		target.onException(jmsException);
	}

}
