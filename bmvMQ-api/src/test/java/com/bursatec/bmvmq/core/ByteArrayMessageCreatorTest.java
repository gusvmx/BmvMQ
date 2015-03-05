package com.bursatec.bmvmq.core;

import javax.jms.JMSException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 *
 */
public class ByteArrayMessageCreatorTest {

	/***/
	private static final String MESSAGE = "message";
	
	/**
	 * @throws JMSException Al ser un mock, no se arrojará.
	 */
	@Test
	public final void test() throws JMSException {
		ByteArrayMessageCreator messageCreator = new ByteArrayMessageCreator(MESSAGE.getBytes());
		MockBytesMessage message = (MockBytesMessage) messageCreator.createMessage(new MockSession());
		Assert.assertEquals(MESSAGE, new String(message.getMessage()));
		messageCreator = new ByteArrayMessageCreator(MESSAGE.getBytes(), "group");
		message = (MockBytesMessage) messageCreator.createMessage(new MockSession());
		Assert.assertEquals(MESSAGE, new String(message.getMessage()));
	}
}
