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
 * Property setter por default.
 * No realiza nada pues es utilizada para seguir el patron NullObjectPattern.
 * @author gus - Bursatec
 * @version 1.0
 */
class BlankMessagePropertySetter implements MessagePropertySetter {

	@Override
	public void setProperties(final Message message) { }

}
