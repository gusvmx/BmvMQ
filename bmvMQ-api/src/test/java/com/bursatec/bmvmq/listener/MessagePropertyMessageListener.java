/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.bursatec.bmvmq.core.BmvMqQueueTest;

/**
 * @author gus
 *
 */
public class MessagePropertyMessageListener implements RawMessageListener {
	
	/***/
	private CountDownLatch latch;
	/***/
	private AtomicInteger counter;
	/***/
	private String customProperty;
	/**
	 * @param latch el recurso compartido.
	 */
	public MessagePropertyMessageListener(final CountDownLatch latch) {
		this.latch = latch;
		this.counter = new AtomicInteger();
	}

	@Override
	public final void onMessage(final TextMessage message) {
		try {
			customProperty = message.getStringProperty(BmvMqQueueTest.CUSTOM_PROPERTY_NAME);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.latch.countDown();
		this.counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final BytesMessage message) {
		this.latch.countDown();
		this.counter.incrementAndGet();
	}

	@Override
	public final void onMessage(final ObjectMessage message) {
		this.latch.countDown();
		this.counter.incrementAndGet();
	}
	
	/**
	 * @return La cantidad de mensajes recibidos.
	 */
	public final int getMessagesReceived() {
		return this.counter.get();
	}

	/**
	 * @return the customProperty
	 */
	public final String getCustomProperty() {
		return customProperty;
	}

	/**
	 * @param customProperty the customProperty to set
	 */
	public final void setCustomProperty(final String customProperty) {
		this.customProperty = customProperty;
	}

}
