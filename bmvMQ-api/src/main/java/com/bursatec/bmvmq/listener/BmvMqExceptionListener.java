/**
 * Bursatec - BMV Apr 16, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.JMSException;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public interface BmvMqExceptionListener {

	/**
	 * @param jmsException
	 *            Un problema de mensajería.
	 */
	void onException(final JMSException jmsException);

	/**
	 * Callback que indica que la mensajería ha sido interrumpida porque se ha
	 * perdido la conexión con el broker JMS.
	 * 
	 * A partir de este momento se intentará resuscribir cada cierto tiempo,
	 * dependiendo del valor configurado en reconnectionInterval, hasta que se
	 * reestablezca la conexión
	 */
	void messagingInterrupted();

	/**
	 * Callback que indica que la conexión con el broker ha sido establecida por
	 * lo que la mensajería continua en operación.
	 */
	void messagingResumed();
}
