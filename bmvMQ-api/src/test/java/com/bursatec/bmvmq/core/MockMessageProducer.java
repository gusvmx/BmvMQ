/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class MockMessageProducer implements MessageProducer {

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#setDisableMessageID(boolean)
	 */
	@Override
	public void setDisableMessageID(final boolean value) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getDisableMessageID()
	 */
	@Override
	public final boolean getDisableMessageID() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#setDisableMessageTimestamp(boolean)
	 */
	@Override
	public void setDisableMessageTimestamp(final boolean value) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getDisableMessageTimestamp()
	 */
	@Override
	public final boolean getDisableMessageTimestamp() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#setDeliveryMode(int)
	 */
	@Override
	public void setDeliveryMode(final int deliveryMode) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getDeliveryMode()
	 */
	@Override
	public final int getDeliveryMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#setPriority(int)
	 */
	@Override
	public void setPriority(final int defaultPriority) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getPriority()
	 */
	@Override
	public final int getPriority() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#setTimeToLive(long)
	 */
	@Override
	public void setTimeToLive(final long timeToLive) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getTimeToLive()
	 */
	@Override
	public final long getTimeToLive() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#getDestination()
	 */
	@Override
	public final Destination getDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#close()
	 */
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#send(javax.jms.Message)
	 */
	@Override
	public void send(final Message message) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#send(javax.jms.Message, int, int, long)
	 */
	@Override
	public void send(final Message message, final int deliveryMode, final int priority,
			final long timeToLive) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#send(javax.jms.Destination, javax.jms.Message)
	 */
	@Override
	public void send(final Destination destination, final Message message)
			throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageProducer#send(javax.jms.Destination, javax.jms.Message, int, int, long)
	 */
	@Override
	public void send(final Destination destination, final Message message,
			final int deliveryMode, final int priority, final long timeToLive)
			throws JMSException {
		// TODO Auto-generated method stub

	}

}
