/**
 * Bursatec - BMV Apr 17, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.connection;

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.listener.BmvMqConnStateListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class ConnStateLoggerTest {

	/**
	 * 
	 */
	@Test
	public final void test() {
		BmvMqConnStateListener listener = new ConnStateLogger();
		listener.messagingInterrupted();
		listener.messagingResumed();

		CallsCounterConnStateListener counter = new CallsCounterConnStateListener();
		listener = new ConnStateLogger(counter);
		listener.messagingInterrupted();
		listener.messagingResumed();
		
		Assert.assertEquals(1, counter.getInterruptedCalls());
		Assert.assertEquals(1, counter.getResumedCalls());
	}
}
