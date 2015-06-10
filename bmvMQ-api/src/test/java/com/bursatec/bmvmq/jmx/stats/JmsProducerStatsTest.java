package com.bursatec.bmvmq.jmx.stats;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 *
 */
public class JmsProducerStatsTest {

	/**
	 * Cantidad de mensajes entregados al broker.
	 */
	@Test
	public final void test() {
		JmsProducerStats mbean = new JmsProducerStats("producerId");
		Assert.assertEquals(0, mbean.getMessagesDelivered());
		mbean.messageDelivered();
		Assert.assertEquals(1L, mbean.getMessagesDelivered());
		Assert.assertEquals("producerId", mbean.getDestinationName());
	}
	
}
