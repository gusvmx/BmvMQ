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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gus
 *
 */
public class CountdownMessageListener implements MessageListener {
	
	/***/
	private CountDownLatch latch;
	/***/
	private AtomicInteger counter;

	/**
	 * @param latch el recurso compartido.
	 */
	public CountdownMessageListener(final CountDownLatch latch) {
		this.latch = latch;
		this.counter = new AtomicInteger();
	}

	@Override
	public final void onMessage(final String message) {
		this.latch.countDown();
		this.counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final byte[] message) {
		this.latch.countDown();
		this.counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final Serializable message) {
		this.latch.countDown();
		this.counter.incrementAndGet();
	}
	
	/**
	 * @return La cantidad de mensajes recibidos.
	 */
	public final int getMessagesReceived() {
		return this.counter.get();
	}

}
