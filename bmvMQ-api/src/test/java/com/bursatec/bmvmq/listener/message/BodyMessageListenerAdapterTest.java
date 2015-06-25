/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.message;

import javax.jms.JMSException;

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.core.MockBytesMessage;
import com.bursatec.bmvmq.core.MockObjectMessage;
import com.bursatec.bmvmq.core.MockSession;
import com.bursatec.bmvmq.core.MockTextMessage;
import com.bursatec.bmvmq.jmx.stats.JmsConsumerStats;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class BodyMessageListenerAdapterTest {

	/**
	 * @throws JMSException
	 */
	@Test
	public final void test() throws JMSException {
		BmvMqContext.setConfiguration(new BmvMq());
		BmvMqContext.getConfiguration().setAcknowledgeMode(AcknowledgeModeType.AUTO_ACKNOWLEDGE);
		CallsCounterMessageListener messageListener = new CallsCounterMessageListener();
		BodyMessageListenerAdapter adapter = new BodyMessageListenerAdapter(new MockSession(), messageListener, 
				new JmsConsumerStats(""));
		adapter.deliver(new MockBytesMessage());
		adapter.deliver(new MockObjectMessage(""));
		adapter.deliver(new MockTextMessage(""));
		final int numberOfDeliveries = 3;
		Assert.assertEquals(numberOfDeliveries, messageListener.getCounter());
	}
}
