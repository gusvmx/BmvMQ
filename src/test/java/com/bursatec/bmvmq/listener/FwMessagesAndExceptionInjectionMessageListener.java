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

import com.bursatec.bmvmq.MqTemplate;

/**
 * @author gus
 *
 */
public class FwMessagesAndExceptionInjectionMessageListener implements MessageListener {

	/***/
	private AtomicInteger counter;
	/***/
	private CountDownLatch latch;
	/***/
	private String destination;
	/***/
	private MqTemplate mqTemplate;
	
	/**
	 * @param template la plantilla de JMS.
	 * @param destination El destino a donde se reenviarán los mensajes.
	 * @param latch El recurso compartido que hace esperar hasta que se reciban todos los mensajes esperados.
	 */
	public FwMessagesAndExceptionInjectionMessageListener(final MqTemplate template, 
			final String destination, final CountDownLatch latch) {
		this.counter = new AtomicInteger();
		this.latch = latch;
		this.destination = destination;
		this.mqTemplate = template;
	}

	@Override
	public final void onMessage(final String message) {
		mqTemplate.send(destination, message);
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
		mqTemplate.send(destination, message);
		throwExceptionOnFirstMessageReceived();
	}

	@Override
	public final void onMessage(final Serializable message) {
		mqTemplate.send(destination, message);
		throwExceptionOnFirstMessageReceived();
	}
	
	/**
	 * @return Obtiene la cantidad de mensajes recibidos.
	 */
	public final int getMessagesReceived() {
		return counter.get();
	}

}
