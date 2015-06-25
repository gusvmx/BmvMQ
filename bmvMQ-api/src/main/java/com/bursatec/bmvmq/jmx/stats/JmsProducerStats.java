/**
 * 
 */
package com.bursatec.bmvmq.jmx.stats;

import com.bursatec.bmvmq.jmx.mxbean.JmsProducerStatsMXBean;

/**
 * @author gus
 *
 */
public class JmsProducerStats implements JmsProducerStatsMXBean {

	/** La cantidad de mensajes entregados al broker. */
	private long messagesDelivered;
	/** El nombre del destino. */
	private String destinationName;
	
	/**
	 * @param destinationName El nombre del destino.
	 */
	public JmsProducerStats(final String destinationName) {
		this.destinationName = destinationName;
	}
	
	@Override
	public final long getMessagesDelivered() {
		return messagesDelivered;
	}
	
	/**
	 * Incrementa en 1 la cantidad de mensajes entregados al broker.
	 */
	public final void messageDelivered() {
		this.messagesDelivered++;
	}

	@Override
	public final String getDestinationName() {
		return destinationName;
	}

}
