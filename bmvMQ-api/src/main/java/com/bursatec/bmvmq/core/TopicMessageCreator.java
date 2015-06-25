/**
 * Bursatec - BMV Apr 8, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.bursatec.bmvmq.jmx.MBeanFactory;
import com.bursatec.bmvmq.jmx.stats.JmsProducerStats;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class TopicMessageCreator extends AbstractMessageCreator {

	/**
	 * @param session
	 *            La sesión que se utlizará para crear los mensajes.
	 * @param defaultProducer
	 *            El productor por default de la sesión.
	 * @param destinationName
	 *            El nombre del Queue a donde se enviarán los mensajes.
	 */
	public TopicMessageCreator(final Session session,
			final MessageProducer defaultProducer, final String destinationName) {
		super(session, defaultProducer, destinationName);
		JmsProducerStats stats = new JmsProducerStats(destinationName);
		MBeanFactory.createMbean(stats, MBeanFactory.buildPublisherName(destinationName));
		setStats(stats);
	}

	@Override
	protected final Destination getDestination(final Session session, final String destinationName)
			throws JMSException {
		return session.createTopic(destinationName);
	}

}
