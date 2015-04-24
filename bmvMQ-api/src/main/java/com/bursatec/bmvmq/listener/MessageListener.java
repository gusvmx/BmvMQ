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
 * Interface para recibir el cuerpo del mensaje JMS. Utilizada para entregar a
 * la aplicación todos cuerpos de los mensajes recibidos de JMS.
 * 
 * @author gus
 *
 */
public interface MessageListener extends BmvMqMessageListener {

	/**
	 * Callback invocado al recibir un mensaje de texto.
	 * 
	 * @param message
	 *            El mensaje recibido por JMS.
	 */
	void onMessage(String message);

	/**
	 * Callback invocado al recibir un mensaje de arreglo de bytes.
	 * 
	 * @param message
	 *            El mensaje recibido por JMS.
	 */
	void onMessage(byte[] message);

	/**
	 * Callback invocado al recibir un mensaje serializable.
	 * 
	 * @param message
	 *            El mensaje recibido por JMS.
	 */
	void onMessage(Serializable message);
}
