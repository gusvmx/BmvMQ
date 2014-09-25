/**
 * Bursatec - BMV Sep 24, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.throughput;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.core.BmvMqTemplate;

/**
 * @author gus
 *
 */
public final class SimpleMessageSender {
	
	/***/
	private static Logger logger = LoggerFactory.getLogger(SimpleMessageSender.class);
	/***/
	private SimpleMessageSender() { }
	/**
	 * @param args ningún argumento es requerido.
	 * @throws FileNotFoundException 
	 */
	public static void main(final String[] args) throws FileNotFoundException {
		MqTemplate mqTemplate = new BmvMqTemplate("classpath:bmvMqThroughput.xml");
		final int numberOfMessagesToSend = 1000000;
		final int printMessagesSentEvery = 1000;
		int messagesSent = 0;
		
		while (messagesSent < numberOfMessagesToSend) {
			mqTemplate.send("throughputTest", "Message " + messagesSent++);
			if (messagesSent % printMessagesSentEvery == 0) {
				logger.info("Se han enviado {} mensajes", messagesSent);
			}
		}
		logger.info("Se han enviado {} mensajes", messagesSent);
	}

}
