/**
 * Bursatec - BMV Apr 17, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.connection;

import com.bursatec.bmvmq.listener.BmvMqConnStateListener;



/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class CallsCounterConnStateListener implements BmvMqConnStateListener {

	/***/
	private int interruptedCalls;
	/***/
	private int resumedCalls;
	
	@Override
	public final void messagingInterrupted() {
		interruptedCalls++;
	}

	@Override
	public final void messagingResumed() {
		resumedCalls++;
	}

	/**
	 * @return the interruptedCalls
	 */
	public final int getInterruptedCalls() {
		return interruptedCalls;
	}

	/**
	 * @return the resumedCalls
	 */
	public final int getResumedCalls() {
		return resumedCalls;
	}

}
