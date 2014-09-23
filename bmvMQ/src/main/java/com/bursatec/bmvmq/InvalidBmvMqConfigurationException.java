/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq;

/**
 * @author gus
 *
 */
public class InvalidBmvMqConfigurationException extends RuntimeException {

	/***/
	private static final long serialVersionUID = -1446397555523523595L;

	/***/
	public InvalidBmvMqConfigurationException() { }
	
	/**
	 * @param message El mensaje que incluye el detalle del error.
	 */
	public InvalidBmvMqConfigurationException(final String message) {
		super(message);
	}
	
	/**
	 * @param message El mensaje que incluye el detalle del error.
	 * @param e La causa raíz del error.
	 */
	public InvalidBmvMqConfigurationException(final String message, final Throwable e) {
		super(message, e);
	}
	
	/**
	 * @param e La causa raíz del error.
	 */
	public InvalidBmvMqConfigurationException(final Throwable e) {
		super(e);
	}
}
