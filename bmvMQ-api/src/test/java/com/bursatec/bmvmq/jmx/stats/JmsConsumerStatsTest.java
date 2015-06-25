package com.bursatec.bmvmq.jmx.stats;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 */
public class JmsConsumerStatsTest {

	/**
	 * Verifica la cantidad de mensajes recibidos.
	 */
	@Test
	public final void test() {
		JmsConsumerStats stats = new JmsConsumerStats("consumerId");
		Assert.assertEquals(0, stats.getMessagesReceived());
		stats.messageReceived();
		Assert.assertEquals("consumerId", stats.getDestinationName());
		Assert.assertEquals(1, stats.getMessagesReceived());
	}
	
}
