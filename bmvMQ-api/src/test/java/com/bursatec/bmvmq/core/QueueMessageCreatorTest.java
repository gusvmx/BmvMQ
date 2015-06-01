/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class QueueMessageCreatorTest {

	/**
	 * @throws JMSException
	 */
	@Test
	public final void test() throws JMSException {
		Session session = new MockSession();
		QueueMessageCreator messageCreator = new QueueMessageCreator(session, new MockMessageProducer(), "");
		Assert.assertTrue(messageCreator.getDestination(session, "") instanceof Queue);
	}
}
