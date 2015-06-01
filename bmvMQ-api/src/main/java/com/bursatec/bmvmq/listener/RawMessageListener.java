/**
 * Bursatec - BMV Sep 5, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import javax.jms.BytesMessage;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * Interface para recepción de mensajes en crudo (JMS) del cliente.
 * @author gus
 *
 */
public interface RawMessageListener extends BmvMqMessageListener {

	/**
	 * Callback invocado cada que se recibe un mensaje de texto.
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(TextMessage message);
	
	/**
	 * Callback invocado cada que se recibe un mensaje de arreglo de bytes.
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(BytesMessage message);
	
	/**
	 * Callback invocado cada que se recibe un mensaje de serializable.
	 * @param message El mensaje recibido por JMS.
	 */
	void onMessage(ObjectMessage message);
}
