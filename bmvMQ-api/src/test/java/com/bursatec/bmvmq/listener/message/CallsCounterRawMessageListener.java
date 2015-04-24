/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.message;

import javax.jms.BytesMessage;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.bursatec.bmvmq.listener.RawMessageListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class CallsCounterRawMessageListener implements RawMessageListener {

	/***/
	private int counter;

	/**
	 * @return the counter
	 */
	public final int getCounter() {
		return counter;
	}

	@Override
	public final void onMessage(final TextMessage message) {
		counter++;
	}

	@Override
	public final void onMessage(final BytesMessage message) {
		counter++;
	}

	@Override
	public final void onMessage(final ObjectMessage message) {
		counter++;
	}

}
