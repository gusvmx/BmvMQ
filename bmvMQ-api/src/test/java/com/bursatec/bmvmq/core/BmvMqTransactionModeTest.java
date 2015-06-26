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

import com.bursatec.bmvmq.BmvMqTemplate;
import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.listener.FwMessagesAndExceptionInjectionMessageListener;
import com.bursatec.bmvmq.listener.MsgReceivedCounterMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqTransactionModeTest {
	
	/***/
	private static final int TIMEOUT = 10;
	/***/
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/***/
	private static final String MESSAGE = BmvMqTransactionModeTest.class.getName() + "Message";
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final String DESTINATION = BmvMqTransactionModeTest.class.getName();
	/***/
	private static final String SECOND_DESTINATION = BmvMqTransactionModeTest.class.getName() + "FW";
	/**
	 * Al ser una sesion transaccional, el mensaje se entregará hasta que sea
	 * procesado correctamente. En este caso 2 veces, 1 cuando genera la
	 * excepcion y otra cuando lo procesa correctamente.
	 */
	private static final int NUMBER_OF_MESSAGES_TO_RECEIVE = 2;
	/**
	 * Solo se recibe un mensaje reenviado ya que el primero, que se encuentra
	 * en una transacción, no es enviado por haberse generado una transaccion.
	 */
	private static final int NUMBER_OF_FORWARDED_MESSAGES_RECEIVED = 1;
	/***/
	private MqTemplate mqTemplate;
	/**
	 * @throws FileNotFoundException Si no encuentra el archivo de configuración.
	 */
	@Before
	public final void start() throws FileNotFoundException {
		this.mqTemplate = BmvMqTemplate.activeMQ("classpath:/bmvMqLocalTransaction.xml");
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
	public final void receiveAndSendWithinATransaction() throws FileNotFoundException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(NUMBER_OF_MESSAGES_TO_RECEIVE);
		FwMessagesAndExceptionInjectionMessageListener messageListener = 
				new FwMessagesAndExceptionInjectionMessageListener(mqTemplate, SECOND_DESTINATION, latch);
		MsgReceivedCounterMessageListener forwardedMessagesMessageListener = new MsgReceivedCounterMessageListener();
		mqTemplate.receive(DESTINATION, messageListener);
		mqTemplate.receive(SECOND_DESTINATION, forwardedMessagesMessageListener);
		mqTemplate.send(DESTINATION, MESSAGE);
		
		Assert.assertTrue(latch.await(TIMEOUT, TIME_UNIT));
		
		Thread.sleep(SUBSCRIPTION_TIME);
		/*
		 * El mensaje se recibe 2 veces, la primera vez que arroja la excepción
		 * y la 2a que lo procesa correctamente.
		 */
		Assert.assertEquals(NUMBER_OF_MESSAGES_TO_RECEIVE, messageListener.getMessagesReceived());
		/*
		 * Al estar en una transacción, el primer mensaje reenviado no se
		 * envía por haberse generado la excepción. Sólo cuando se procesa
		 * correctamente, se realiza el commit y se entrega el mensaje.
		 */
		Assert.assertEquals(NUMBER_OF_FORWARDED_MESSAGES_RECEIVED, 
				forwardedMessagesMessageListener.getMessagesReceived());
	}

}
