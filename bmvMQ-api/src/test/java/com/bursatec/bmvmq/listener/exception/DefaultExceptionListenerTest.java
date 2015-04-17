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

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.listener.DefaultExceptionListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class DefaultExceptionListenerTest {

	/***/
	@Test
	public final void test() {
		ExceptionListener exceptionListener = new DefaultExceptionListener();
		exceptionListener.onException(new JMSException("Testing"));
		
		CallsCounterExceptionListener counter = new CallsCounterExceptionListener();
		exceptionListener = new DefaultExceptionListener(counter);
		exceptionListener.onException(new JMSException("Testing"));
		Assert.assertEquals(1, counter.getOnExceptionCalls());
	}
}
