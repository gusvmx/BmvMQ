/**
 * Bursatec - BMV Apr 21, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.message;

/**
 * Constantes de mensajería.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public final class MessageConstants {

	/** Evita que se generen instancias de esta clase de constantes. */
	private MessageConstants() { }

	/**
	 * Indica el valor por default para los grupos. Bien es cierto que esta
	 * constante se asigna a null y esta se utiliza como parámetro en
	 * {@link MqTemplate}, yendo en contra de los lineamientos sobre no pasar
	 * nulos como argumentos, se tuvo que realizar de este modo porque no hay un
	 * valor por default para el identificador del grupo.
	 */
	public static final String DEFAULT_GROUP_ID = null;
	
}
