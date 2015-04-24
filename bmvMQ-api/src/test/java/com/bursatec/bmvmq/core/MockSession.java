package com.bursatec.bmvmq.core;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class MockSession implements Session {

	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public final QueueBrowser createBrowser(final Queue arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final QueueBrowser createBrowser(final Queue arg0, final String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final BytesMessage createBytesMessage() throws JMSException {
		return new MockBytesMessage();
	}

	@Override
	public final MessageConsumer createConsumer(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final MessageConsumer createConsumer(final Destination arg0, final String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final MessageConsumer createConsumer(final Destination arg0, final String arg1,
			final boolean arg2) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TopicSubscriber createDurableSubscriber(final Topic arg0, final String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TopicSubscriber createDurableSubscriber(final Topic arg0, final String arg1,
			final String arg2, final boolean arg3) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final MapMessage createMapMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final Message createMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final ObjectMessage createObjectMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final ObjectMessage createObjectMessage(final Serializable arg0)
			throws JMSException {
		return new MockObjectMessage(arg0);
	}

	@Override
	public final MessageProducer createProducer(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final Queue createQueue(final String arg0) throws JMSException {
		return new MockQueue();
	}

	@Override
	public final StreamMessage createStreamMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TemporaryQueue createTemporaryQueue() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TemporaryTopic createTemporaryTopic() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TextMessage createTextMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final TextMessage createTextMessage(final String arg0) throws JMSException {
		return new MockTextMessage(arg0);
	}

	@Override
	public final Topic createTopic(final String arg0) throws JMSException {
		return new MockTopic();
	}

	@Override
	public final int getAcknowledgeMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final MessageListener getMessageListener() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final boolean getTransacted() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recover() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMessageListener(final MessageListener arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

}
