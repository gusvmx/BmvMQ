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
public class DefaultExceptionListener implements BmvMqExceptionListener {

	@Override
	public void onException(final JMSException jmsException) { }

	@Override
	public void messagingInterrupted() { }

	@Override
	public void messagingResumed() { }

}
