/**
 * Bursatec - BMV Mar 5, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 *
 */
public class StringMessageCreatorTest {

	/***/
	private static final String MESSAGE = "message";
	
	/**
	 * @throws JMSException Al ser un mock, no se arrojará.
	 */
	@Test
	public final void test() throws JMSException {
		StringMessageCreator messageCreator = new StringMessageCreator(MESSAGE);
		TextMessage message = (TextMessage) messageCreator.createMessage(new MockSession());
		Assert.assertEquals(MESSAGE, message.getText());
		messageCreator = new StringMessageCreator(MESSAGE, "group");
		message = (TextMessage) messageCreator.createMessage(new MockSession());
		Assert.assertEquals(MESSAGE, message.getText());
	}
}
