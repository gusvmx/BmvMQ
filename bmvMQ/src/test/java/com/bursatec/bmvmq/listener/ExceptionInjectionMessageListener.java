/**
 * Bursatec - BMV Sep 23, 2014
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
public class ExceptionInjectionMessageListener implements MessageListener {

	/***/
	private AtomicInteger counter;
	/***/
	private CountDownLatch latch;
	
	/**
	 * @param latch El recurso compartido que hace esperar hasta que se reciban todos los mensajes esperados.
	 */
	public ExceptionInjectionMessageListener(final CountDownLatch latch) {
		this.counter = new AtomicInteger();
		this.latch = latch;
	}

	@Override
	public final void onMessage(final String message) {
		throwExceptionOnFirstMessageReceived();
	}
	
	/***/
	private void throwExceptionOnFirstMessageReceived() {
		latch.countDown();
		if (counter.incrementAndGet() == 1) {
			throw new ArithmeticException();
		}
	}

	@Override
	public final void onMessage(final byte[] message) {
		throwExceptionOnFirstMessageReceived();
	}

	@Override
	public final void onMessage(final Serializable message) {
		throwExceptionOnFirstMessageReceived();
	}
	
	/**
	 * @return Obtiene la cantidad de mensajes recibidos.
	 */
	public final int getMessagesReceived() {
		return counter.get();
	}

}
