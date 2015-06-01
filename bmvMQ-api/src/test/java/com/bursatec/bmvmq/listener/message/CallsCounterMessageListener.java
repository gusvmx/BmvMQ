/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.message;

import java.io.Serializable;

import com.bursatec.bmvmq.listener.MessageListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class CallsCounterMessageListener implements MessageListener {

	/***/
	private int counter;
	@Override
	public final void onMessage(final String message) {
		counter++;
	}

	@Override
	public final void onMessage(final byte[] message) {
		counter++;
	}

	@Override
	public final void onMessage(final Serializable message) {
		counter++;
	}

	/**
	 * @return the counter
	 */
	public final int getCounter() {
		return counter;
	}

}
