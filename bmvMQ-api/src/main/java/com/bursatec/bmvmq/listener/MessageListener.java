/**
 * Bursatec - BMV Sep 5, 2014
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
public interface MessageListener {

	/**
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(String message);
	
	/**
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(byte[] message);
	
	/**
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(Serializable message);
}
