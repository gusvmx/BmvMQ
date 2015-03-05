/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gus
 *
 */
public class MsgReceivedCounterMessageListener implements MessageListener {
	
	/***/
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public final void onMessage(final String message) {
		counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final byte[] message) {
		counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final Serializable message) {
		counter.incrementAndGet();
	}

	/**
	 * @return the counter
	 */
	public final int getMessagesReceived() {
		return counter.get();
	}

}
