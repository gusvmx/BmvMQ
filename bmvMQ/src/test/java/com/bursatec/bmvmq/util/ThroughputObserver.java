/**
 * Bursatec - BMV Sep 24, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.listener.MessageReceivedCounter;

/**
 * @author gus
 *
 */
public class ThroughputObserver implements Runnable {
	
	/***/
	private MessageReceivedCounter counter;
	/***/
	private static final int SAMPLING_RATE = 1000;
	/***/
	private Logger logger = LoggerFactory.getLogger(ThroughputObserver.class);

	/**
	 * @param counter El message listener que lleva el conteo de mensajes recibidos.
	 */
	public ThroughputObserver(final MessageReceivedCounter counter) {
		this.counter = counter;
	}

	@Override
	public final void run() {
		int stringMessagesReceived = 0;
		int stringMessagesThroughput = 0;
		
		int bytearrayMessagesReceived = 0;
		int bytearrayMessagesThroughput = 0;
		
		int serializedMessagesReceived = 0;
		int serializedMessagesThroughput = 0;
		
		while (true) {
			stringMessagesThroughput = counter.getStringMessagesCounter() - stringMessagesReceived;
			stringMessagesReceived = counter.getStringMessagesCounter();
			logThroughput("String", stringMessagesThroughput, stringMessagesReceived);
			
			bytearrayMessagesThroughput = counter.getByteMessagesCounter() - bytearrayMessagesReceived;
			bytearrayMessagesReceived = counter.getByteMessagesCounter();
			logThroughput("ByteArray", bytearrayMessagesThroughput, bytearrayMessagesReceived);
			
			serializedMessagesThroughput = counter.getSerializableMessagesCounter() - serializedMessagesReceived;
			serializedMessagesReceived = counter.getSerializableMessagesCounter();
			logThroughput("String", serializedMessagesThroughput, serializedMessagesReceived);
			
			try {
				Thread.sleep(SAMPLING_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param messageType El tipo de mensaje recibido (String, ByteArray o Serialized)
	 * @param currentThroughput La cantidad de mensajes recibidos en el último periodo de tiempo (SAMPLING_RATE)
	 * @param messagesReceived La cantidad total de mensajes recibidos.
	 */
	private void logThroughput(final String messageType, final int currentThroughput, final int messagesReceived) {
		logger.info("{} messages throughput (msg/sec): {}. Total recibidos {}", 
				messageType, currentThroughput, messagesReceived);
	}

}
