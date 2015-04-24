/**
 * Bursatec - BMV Apr 20, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.message;

import javax.jms.Message;

/**
 * Callback utilizado para personalizar un mensaje JMS.
 * 
 * Este callback es implementado por el cliente cuando desee agregar propiedades
 * al mensaje JMS antes de que sea enviado.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public interface MessagePropertySetter {

	/** Property setter por default que no hace nada. NullObjectPattern */
	MessagePropertySetter BLANK_MESSAGE_PROPERTY_SETTER = new BlankMessagePropertySetter();
	
	/**
	 * Personaliza el mensaje JMS pudiendo asignar cualquier propiedad deseada
	 * al mensaje o alguna operación extra que JMS permita realizar.
	 * 
	 * @param message
	 *            El mensaje que se personalizará.
	 */
	void setProperties(Message message);
	
}
