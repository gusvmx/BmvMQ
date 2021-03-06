/**
 * Bursatec - BMV Sep 25, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import com.bursatec.bmvmq.config.BmvMqContext;

/**
 * @author gus
 *
 */
public class BmvMqExceptionListenerAdapter implements ExceptionListener {

	@Override
	public final void onException(final JMSException exception) {
		BmvMqContext.getExceptionListener().onException(exception);
	}

}
