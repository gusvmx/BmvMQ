/**
 * Bursatec - BMV Jan 21, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.listener.CountdownMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqStopTest {

	/***/
	private MqTemplate template;
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final int DELIVERY_TIME = 500;
	/***/
	private static final String MESSAGE = BmvMqStopTest.class.getName() + "Message";
	/***/
	private static final int TIMEOUT = 3;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/**
	 * @throws FileNotFoundException Si no encuentra el archivo de configuración.
	 */
	@Before
	public final void start() throws FileNotFoundException {
		this.template = new BmvMqTemplate("classpath:/bmvMqDupsOk.xml");
	}
	
	/***/
	@After
	public final void stop() {
		this.template.stop();
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void stopReceiving() throws InterruptedException {
		final int numberOfMessagesToSend = 3;
		final int numberOfMessagesTeReceive = 1;
		final String destination = "receiveAndStop";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToSend);
		CountdownMessageListener messageListener = new CountdownMessageListener(latch);
		template.receive(destination, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.send(destination, MESSAGE);
		
		Thread.sleep(DELIVERY_TIME);
		template.stopReceiving(destination);
		
		template.send(destination, MESSAGE.getBytes());
		template.send(destination, new HashMap<Integer, String>());

		Assert.assertFalse(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesTeReceive, messageListener.getMessagesReceived());
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void unsubscribe() throws InterruptedException {
		final int numberOfMessagesToPublish = 3;
		final int numberOfMessagesToReceive = 1;
		final String destination = "unsubscribe";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToPublish);
		CountdownMessageListener messageListener = new CountdownMessageListener(latch);
		template.subscribe(destination, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.publish(destination, MESSAGE);
		
		Thread.sleep(DELIVERY_TIME);
		template.unsubscribe(destination);
		
		template.publish(destination, MESSAGE.getBytes());
		template.publish(destination, new HashMap<Integer, String>());

		Assert.assertFalse(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToReceive, messageListener.getMessagesReceived());
	}
	
	/**
	 * 
	 */
	@Test
	public final void unsubscribeWhenDestinationDoesNotExist() {
		template.unsubscribe("unsubscribe");
		template.stopReceiving("stopReceiving");
	}
}
