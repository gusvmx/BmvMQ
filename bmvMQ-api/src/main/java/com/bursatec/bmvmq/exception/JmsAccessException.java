/**
 * Bursatec - BMV Apr 13, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.exception;

/**
 * Excepción raíz para permitir manejar los distintos tipos de error encontrados
 * sin tener que saber los detalles de un proveedor JMS en particular.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public abstract class JmsAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3099570024037723424L;
	
	/**
	 * @param description La descripción de la excepción.
	 */
	public JmsAccessException(final String description) {
		super(description);
	}
	
	/**
	 * @param description La descripción de la excepción.
	 * @param cause La causa origen.
	 */
	public JmsAccessException(final String description, final Throwable cause) {
		super(description, cause);
	}

}
