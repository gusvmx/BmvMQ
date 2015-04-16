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

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.listener.ExceptionInjectionMessageListener;

/**
 * @author gus
 *
 */
public class BmvMqDupsOkAckModeTest {
	
	/***/
	private static final String MESSAGE = BmvMqDupsOkAckModeTest.class.getName() + "Message";
	/***/
	private static final int SUBSCRIPTION_TIME = 500;
	/***/
	private static final String DESTINATION = "BmvMQDupsOkTest";
	

	/**
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	@Test
	public final void dupsOkAckReception() throws FileNotFoundException, InterruptedException {
		MqTemplate mqTemplate = new BmvMqTemplate("classpath:/bmvMqDupsOk.xml");
		ExceptionInjectionMessageListener messageListener = 
				new ExceptionInjectionMessageListener(new CountDownLatch(0));
		mqTemplate.send(DESTINATION, MESSAGE);
		mqTemplate.receive(DESTINATION, messageListener);
		
		Thread.sleep(SUBSCRIPTION_TIME);
		
		/*
		 * Puede recibir 1 o 2 mensajes ya que, dependiendo del tiempo en el que
		 * se genera el acuse, antes o después de la excepción, puede provocar
		 * una reentrega del mensaje.
		 * 
		 * Entonces sólo se puede validar que se haya recibido 1 o 2 veces el
		 * mensaje.
		 */
		Assert.assertTrue(messageListener.getMessagesReceived() == 1 || messageListener.getMessagesReceived() == 2);
		System.out.println(messageListener.getMessagesReceived());
	}

}
