/**
 * 
 */
package com.bursatec.bmvmq.jmx.stats;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.config.bind.BmvMq;

/**
 * @author gus
 *
 */
public class JmsConnectionInfoTest {

	@Before
	public final void setUp() {
		BmvMq config = new BmvMq();
		config.setUrl("url");
		config.setClientId("clientId");
		config.setAcknowledgeMode(AcknowledgeModeType.AUTO_ACKNOWLEDGE);
		config.setAsyncSend(true);
		config.setConnectionTimeout(30000);
		config.setReconnectionInterval(30000);
		BmvMqContext.setConfiguration(config);
	}
	@Test
	public final void test() {
		JmsConnectionInfo connInfo = new JmsConnectionInfo();
		Assert.assertEquals("url", connInfo.getUrl());
		Assert.assertEquals("clientId", connInfo.getClientId());
		Assert.assertEquals(AcknowledgeModeType.AUTO_ACKNOWLEDGE, connInfo.getAcknowledgeMode());
		Assert.assertTrue(connInfo.isAsyncSend());
		Assert.assertEquals(30000, connInfo.getConnectionTimeout());
		Assert.assertEquals(30000, connInfo.getReconnectionInterval());
	}
	
	@After
	public final void tearDown() {
		BmvMqContext.setConfiguration(null);
	}

}
