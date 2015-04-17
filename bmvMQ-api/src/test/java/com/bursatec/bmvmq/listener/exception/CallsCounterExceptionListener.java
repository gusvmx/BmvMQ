/**
 * Bursatec - BMV Apr 17, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.exception;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class CallsCounterExceptionListener implements ExceptionListener {

	/***/
	private int onExceptionCalls;
	
	@Override
	public final void onException(final JMSException exception) {
		onExceptionCalls++;
	}

	/**
	 * @return the onExceptionCalls
	 */
	public final int getOnExceptionCalls() {
		return onExceptionCalls;
	}

}
