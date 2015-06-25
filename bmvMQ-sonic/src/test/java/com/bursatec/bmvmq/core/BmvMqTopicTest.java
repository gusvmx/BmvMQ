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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.jmx.MBeanFactory;
import com.bursatec.bmvmq.listener.CountdownMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqTopicTest {
	
	/***/
	private static final int TIMEOUT = 10;
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/***/
	private static final String MESSAGE = BmvMqTopicTest.class.getName() + "Message";
	/***/
	private MqTemplate template;

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
	public final void publishAndSubscribe() throws InterruptedException {
		final int numberOfMessagesToReceive = 3;
		final int numberOfSubsribers = 2;
		final String destination = "publishAndSubscribe";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToReceive * numberOfSubsribers);
		CountdownMessageListener receiver1 = new CountdownMessageListener(latch);
		CountdownMessageListener receiver2 = new CountdownMessageListener(latch);
		template.subscribe(destination, receiver1);
		MBeanFactory.unregisterMbeans(MBeanFactory.buildSubscriberName(destination));
		template.subscribe(destination, receiver2);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.publish(destination, MESSAGE);
		template.publish(destination, MESSAGE.getBytes());
		template.publish(destination, new HashMap<Integer, String>());
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToReceive, receiver1.getMessagesReceived());
		Assert.assertEquals(numberOfMessagesToReceive, receiver2.getMessagesReceived());
	}
	
}
