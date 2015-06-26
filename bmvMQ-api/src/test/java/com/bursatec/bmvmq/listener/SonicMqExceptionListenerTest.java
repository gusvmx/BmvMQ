/**
 * Bursatec - BMV Jun 26, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import progress.message.jclient.Constants;

import com.bursatec.bmvmq.config.BmvMqContext;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class SonicMqExceptionListenerTest {

	/***/
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();
	/***/
	private BmvMqConnStateListener originalListener;
	/***/
	private BmvMqConnStateListener mockedListener = context.mock(BmvMqConnStateListener.class);
	/***/
	@Before
	public final void setup() {
		originalListener = BmvMqContext.getConnectionListener();
		BmvMqContext.setConnectionListener(mockedListener);
	}
	
	/***/
	@After
	public final void tierDown() {
		BmvMqContext.setConnectionListener(originalListener);
	}
	
	/***/
	@Test
	public final void test() {
		context.checking(new Expectations() { {
			oneOf(mockedListener).messagingResumed();
			oneOf(mockedListener).messagingInterrupted();
		} });
		SonicMqExceptionListener listener = new SonicMqExceptionListener();
		listener.connectionStateChanged(Constants.RECONNECTING);
		listener.connectionStateChanged(Constants.ACTIVE);
		listener.connectionStateChanged(Constants.FAILED);
		listener.connectionStateChanged(Constants.CLOSED);
	}
}
