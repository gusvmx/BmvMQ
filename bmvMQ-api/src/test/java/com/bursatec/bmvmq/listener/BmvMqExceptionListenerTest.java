/**
 * Bursatec - BMV Sep 25, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.JMSException;

import org.junit.Test;

/**
 * @author gus
 *
 */
public class BmvMqExceptionListenerTest {

	/***/
	@Test
	public final void test() {
		BmvMqExceptionListener listener = new DefaultExceptionListener();
		listener.onException(new JMSException("Error inducido"));
		listener.messagingInterrupted();
		listener.messagingResumed();
	}
}
