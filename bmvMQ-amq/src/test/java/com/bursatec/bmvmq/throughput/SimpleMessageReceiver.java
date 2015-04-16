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

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.core.BmvMqTemplate;
import com.bursatec.bmvmq.listener.MessageReceivedCounter;
import com.bursatec.bmvmq.util.ThroughputObserver;

/**
 * @author gus
 *
 */
public final class SimpleMessageReceiver {
	
	/***/
	private SimpleMessageReceiver() { }

	/**
	 * @param args ningún argumento es requerido.
	 * @throws FileNotFoundException 
	 */
	public static void main(final String[] args) throws FileNotFoundException {
		MqTemplate mqTemplate = new BmvMqTemplate("classpath:/bmvMqThroughput.xml");
		MessageReceivedCounter messageListener = new MessageReceivedCounter();
		Thread throughputObserver = new Thread(new ThroughputObserver(messageListener), "ThroughputObserver");
		mqTemplate.receive("throughputTest", messageListener);
		throughputObserver.start();
	}
}
