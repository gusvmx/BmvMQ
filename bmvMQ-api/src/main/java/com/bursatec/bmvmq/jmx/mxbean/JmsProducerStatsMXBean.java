/**
 * 
 */
package com.bursatec.bmvmq.jmx.mxbean;

/**
 * @author gus
 *
 */
public interface JmsProducerStatsMXBean {

	/**
	 * @return La cantidad de mensajes entregados al broker JMS.
	 */
	long getMessagesDelivered();
	
	String getDestinationName();
}
