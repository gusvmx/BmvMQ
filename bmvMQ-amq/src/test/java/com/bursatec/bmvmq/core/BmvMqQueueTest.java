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
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.exception.SendMessageFailureException;
import com.bursatec.bmvmq.listener.CountdownMessageListener;
import com.bursatec.bmvmq.listener.MessagePropertyMessageListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.MsgReceivedCounterMessageListener;
import com.bursatec.bmvmq.message.MessagePropertySetter;

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
	private MqTemplate template;
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	public static final String CUSTOM_PROPERTY_NAME = "customPropertyName";

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
	 * Flujo de mensajería básica para verificar el flujo.
	 * 
	 * @throws InterruptedException
	 *             Si alguien interrumpe a este hilo mientras se espera por la
	 *             recepcion de mensajes.
	 */
	@Test
	public final void messaging() throws InterruptedException {
		final StringBuilder result = new StringBuilder();
		final byte[] byteArrayReceived = new byte[MESSAGE.getBytes().length];
		final HashMap<String, String> receivedMap = new HashMap<String, String>();
		final String destination = "messaging";
		final CountDownLatch latch = new CountDownLatch(3);
		
		template.receive(destination, new MessageListener() {
			@Override
			public void onMessage(final Serializable message) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) message;
				receivedMap.putAll(map);
				latch.countDown();
			}
			@Override
			public void onMessage(final byte[] message) {
				System.arraycopy(message, 0, byteArrayReceived, 0, message.length);
				latch.countDown();
			}
			@Override
			public void onMessage(final String message) {
				result.append(message);
				latch.countDown();
			}
		});
		
		template.send(destination, MESSAGE);
		template.send(destination, MESSAGE.getBytes());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MESSAGE, MESSAGE);
		template.send(destination, map);
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		//Valida que el contenido de los mensajes sea el mismo
		Assert.assertEquals(MESSAGE, result.toString());
		Assert.assertTrue(Arrays.equals(MESSAGE.getBytes(), byteArrayReceived));
		Assert.assertEquals(MESSAGE, receivedMap.get(MESSAGE));
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void sendAndReceive() throws InterruptedException {
		final int numberOfMessagesToReceive = 3;
		final String destination = "sendAndReceive";
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
		final String destination = "exclusiveConsumer";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToSendAndReceive);
		MessageListener exclusiveConsumer = new CountdownMessageListener(latch);
		MsgReceivedCounterMessageListener anotherConsumer = new MsgReceivedCounterMessageListener();
		template.receiveExclusively(destination, exclusiveConsumer);
		template.receive(destination, anotherConsumer);
		
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
		final String destination = "messageGrouping";
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToSendPerGroup * groups.length);
		
		CountdownMessageListener receiver1 = new CountdownMessageListener(latch);
		CountdownMessageListener receiver2 = new CountdownMessageListener(latch);
		
		template.receive(destination, receiver1);
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
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void sendAndReceiveWithProperties() throws InterruptedException {
		final int numberOfMessagesToReceive = 1;
		final String destination = "sendAndReceiveWithProperties";
		final String customProperty = String.valueOf(Math.random());
		CountDownLatch latch = new CountDownLatch(numberOfMessagesToReceive);
		MessagePropertyMessageListener messageListener = new MessagePropertyMessageListener(latch);
		template.receive(destination, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		template.send(destination, MESSAGE, new MessagePropertySetter() {
			
			@Override
			public void setProperties(final Message message) throws JMSException {
				message.setStringProperty(CUSTOM_PROPERTY_NAME, customProperty);
			}
		});

		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(numberOfMessagesToReceive, messageListener.getMessagesReceived());
		Assert.assertEquals(customProperty, messageListener.getCustomProperty());
	}
	
	/**
	 * @throws InterruptedException */
	@Test
	public final void faultWhileSettingProperties() throws InterruptedException {
		final String destination = "sendAndReceiveWithProperties";

		try {
			template.send(destination, MESSAGE, new MessagePropertySetter() {
				
				@Override
				public void setProperties(final Message message) throws JMSException {
					throw new JMSException("Excepcion inducida");
				}
			});
			Assert.fail();
		} catch (SendMessageFailureException e) {
			Assert.assertNotNull(e);
		}
	}
}
