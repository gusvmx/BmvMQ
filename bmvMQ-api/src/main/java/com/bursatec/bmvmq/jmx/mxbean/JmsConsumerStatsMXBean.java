/**
 * 
 */
package com.bursatec.bmvmq.jmx.mxbean;

/**
 * @author gus
 *
 */
public interface JmsConsumerStatsMXBean {

	/**
	 * @return El nombre del destino donde se consumen los mensajes.
	 */
	String getDestinationName();
	/**
	 * @return La cantidad de mensajes recibidos.
	 */
	long getMessagesReceived();
	
}
