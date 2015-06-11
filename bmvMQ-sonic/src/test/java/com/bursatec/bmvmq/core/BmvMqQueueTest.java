/**
 * Bursatec - BMV Sep 22, 2014
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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.jmx.MBeanFactory;
import com.bursatec.bmvmq.listener.CountdownMessageListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.MsgReceivedCounterMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqQueueTest {
	
	/***/
	private static final int TIMEOUT = 10;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/***/
	private static final String MESSAGE = BmvMqQueueTest.class.getName() + "Message";
	/***/
	private static MqTemplate template;
	/***/
	private static final int SUBSCRIPTION_TIME = 500;

	/**
	 * @throws FileNotFoundException En caso de no encontrar el archivo de configuracion por default.
	 */
	@BeforeClass
	public static final void initBroker() throws FileNotFoundException {
		template = new BmvMqTemplate();
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void sendAndReceive() throws InterruptedException {
		final int numberOfMessagesToReceive = 3;
		final String destination = "BmvMQqueuetest";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToReceive);
		CountdownMessageListener messageListener = new CountdownMessageListener(latch);
		template.receive(destination, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.send(destination, MESSAGE);
		template.send(destination, MESSAGE.getBytes());
		template.send(destination, new HashMap<Integer, String>());

		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToReceive, messageListener.getMessagesReceived());
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void exclusiveConsumer() throws InterruptedException {
		final int numberOfMessagesToSendAndReceive = 10;
		final String destination = "BmvMQExclusiveConsumerTest";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToSendAndReceive);
		MessageListener exclusiveConsumer = new CountdownMessageListener(latch);
		MsgReceivedCounterMessageListener anotherConsumer = new MsgReceivedCounterMessageListener();
		template.receiveExclusively(destination, exclusiveConsumer);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		for (int i = 0; i < numberOfMessagesToSendAndReceive; i++) {
			template.send(destination, MESSAGE);
		}
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(0, anotherConsumer.getMessagesReceived());
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void messageGrouping() throws InterruptedException {
		final int numberOfMessagesToSendPerGroup = 3;
		final String[] groups = {"A", "B", "C"};
		final String destination = "BmvMQMessageGroupingTest";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToSendPerGroup * groups.length);
		
		CountdownMessageListener receiver1 = new CountdownMessageListener(latch);
		CountdownMessageListener receiver2 = new CountdownMessageListener(latch);
		
		template.receive(destination, receiver1);
		MBeanFactory.unregisterMbeans(MBeanFactory.buildQueueName(destination));
		template.receive(destination, receiver2);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		for (int i = 0; i < numberOfMessagesToSendPerGroup; i++) {
			template.send(destination, MESSAGE, groups[i]);
			template.send(destination, MESSAGE.getBytes(), groups[i]);
			template.send(destination, new HashMap<Integer, String>(), groups[i]);
		}
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToSendPerGroup * groups.length, 
				receiver1.getMessagesReceived() + receiver2.getMessagesReceived());
		Assert.assertNotEquals(numberOfMessagesToSendPerGroup * groups.length, 
				receiver1.getMessagesReceived());
		Assert.assertNotEquals(numberOfMessagesToSendPerGroup * groups.length, 
				receiver2.getMessagesReceived());
	}
}
