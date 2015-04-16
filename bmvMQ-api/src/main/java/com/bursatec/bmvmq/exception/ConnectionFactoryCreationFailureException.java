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
 * Utilizada para indicar que hubo un error al crear la fábrica de conexiones JMS.
 * @author gus - Bursatec
 * @version 1.0
 */
public class ConnectionFactoryCreationFailureException extends
		JmsAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6240991865810352205L;

	/**
	 * @param description La descripción de la excepción.
	 */
	public ConnectionFactoryCreationFailureException(final String description) {
		super(description);
	}
	
	/**
	 * @param description La descripción de la excepción.
	 * @param cause La causa origen.
	 */
	public ConnectionFactoryCreationFailureException(final String description, final Throwable cause) {
		super(description, cause);
	}
}
