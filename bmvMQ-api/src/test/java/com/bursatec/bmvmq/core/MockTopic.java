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
import javax.jms.Topic;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class MockTopic implements Topic {

	/* (non-Javadoc)
	 * @see javax.jms.Topic#getTopicName()
	 */
	@Override
	public String getTopicName() throws JMSException {
		return null;
	}

}
