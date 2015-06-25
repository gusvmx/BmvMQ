/**
 * 
 */
package com.bursatec.bmvmq.jmx.stats;

import com.bursatec.bmvmq.jmx.mxbean.JmsConsumerStatsMXBean;

/**
 * @author gus
 *
 */
public class JmsConsumerStats implements JmsConsumerStatsMXBean {

	/** La cantidad de mensajes recibidos por el consumidor. */
	private long messagesReceived;
	/** El nombre del destino donde se consumen los mensajes. */
	private String destinationName;
	
	/**
	 * @param destinationName El nombre del destino donde se consumen los mensajes.
	 */
	public JmsConsumerStats(final String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * Incrementa en 1 la cantidad de mensajes recibidos.
	 */
	public final void messageReceived() {
		this.messagesReceived++;
	}

	@Override
	public final long getMessagesReceived() {
		return messagesReceived;
	}

	@Override
	public final String getDestinationName() {
		return destinationName;
	}

}
