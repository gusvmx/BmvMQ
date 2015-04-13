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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.listener.ExceptionInjectionMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqAutoAckModeTest {
	
	/***/
	private static final int TIMEOUT = 10;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/***/
	private static final String MESSAGE = BmvMqAutoAckModeTest.class.getName() + "Message";
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final String DESTINATION = BmvMqAutoAckModeTest.class.getName();
	/**
	 * Al ser AutoAck, el mensaje acusa antes de la ejecución del message
	 * listener. Por esta razón no hay una segunda entrega del mensaje. Se envia
	 * otro mensaje y simplemente se recibe. En resumen, se recibe 1 que genera
	 * excepcion y se recibe otro procesado correctamente.
	 */
	private static final int NUMBER_OF_MESSAGES_TO_RECEIVE = 2;
	/***/
	private ExceptionInjectionMessageListener messageListener;
	/***/
	private MqTemplate mqTemplate;
	/***/
	private CountDownLatch latch;
	
	/**
	 * @throws FileNotFoundException Si no encuentra el archivo de configuración.
	 */
	@Before
	public final void start() throws FileNotFoundException {
		this.mqTemplate = new BmvMqTemplate();
	}
	
	/***/
	@After
	public final void stop() {
		this.mqTemplate.stop();
	}
	/**
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	@Test
	public final void autoAckReception() throws FileNotFoundException, InterruptedException {
		this.latch = new CountDownLatch(NUMBER_OF_MESSAGES_TO_RECEIVE);
		this.messageListener = new ExceptionInjectionMessageListener(latch);
		mqTemplate.send(DESTINATION, MESSAGE);
		mqTemplate.receive(DESTINATION, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		/*
		 * Solo se recibe 1, el que arroja la excepción.
		 */
		Assert.assertEquals(1, messageListener.getMessagesReceived());
		mqTemplate.send(DESTINATION, MESSAGE);
		Thread.sleep(SUBSCRIPTION_TIME);
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		Assert.assertEquals(NUMBER_OF_MESSAGES_TO_RECEIVE, messageListener.getMessagesReceived());
		mqTemplate.stop();
	}

}
