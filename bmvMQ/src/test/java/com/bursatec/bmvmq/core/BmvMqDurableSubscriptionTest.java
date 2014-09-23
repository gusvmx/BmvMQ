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
import com.bursatec.bmvmq.listener.CountdownMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqDurableSubscriptionTest {
	
	/***/
	private static final int TIMEOUT = 10;
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/***/
	private static final String MESSAGE = BmvMqDurableSubscriptionTest.class.getName() + "Message";
	/***/
	private static MqTemplate template;
	/***/
	private static final String CLIENT_ID = "bmvMqClientId";
	/***/
	private static final String SUBSCRIPTION_NAME = "bmvMqSubscriptionName";
	/***/
	private static final String DESTINATION = BmvMqDurableSubscriptionTest.class.getName();

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
	public final void establishDurableSubscription() throws InterruptedException {
		final int numberOfMessagesToPublish = 3;
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToPublish);
		CountdownMessageListener messageListener = new CountdownMessageListener(latch);
		template.durableSubscription(DESTINATION, CLIENT_ID, SUBSCRIPTION_NAME, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.publish(DESTINATION, MESSAGE);
		template.publish(DESTINATION, MESSAGE.getBytes());
		template.publish(DESTINATION, new HashMap<Integer, String>());

		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToPublish, messageListener.getMessagesReceived());
	}
	
}
