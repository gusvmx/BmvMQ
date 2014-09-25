/**
 * Bursatec - BMV Sep 24, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import java.io.Serializable;

/**
 * @author gus
 *
 */
public class MessageReceivedCounter implements MessageListener {
	
	/***/
	private int stringMessagesCounter;
	/***/
	private int byteMessagesCounter;
	/***/
	private int serializableMessagesCounter;

	@Override
	public final void onMessage(final String message) {
		stringMessagesCounter++;
	}

	@Override
	public final void onMessage(final byte[] message) {
		byteMessagesCounter++;
	}

	@Override
	public final void onMessage(final Serializable message) {
		serializableMessagesCounter++;
	}

	/**
	 * @return the stringMessagesCounter
	 */
	public final int getStringMessagesCounter() {
		return stringMessagesCounter;
	}

	/**
	 * @return the byteMessagesCounter
	 */
	public final int getByteMessagesCounter() {
		return byteMessagesCounter;
	}

	/**
	 * @return the serializableMessagesCounter
	 */
	public final int getSerializableMessagesCounter() {
		return serializableMessagesCounter;
	}

}
