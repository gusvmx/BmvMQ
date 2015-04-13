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
 * Utilizada para indicar que hubo un error al crear una conexión JMS.
 * @author gus - Bursatec
 * @version 1.0
 */
public class ConnectionCreationFailureException extends JmsAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7929515719509875663L;

	/**
	 * @param description La descripción del error.
	 */
	public ConnectionCreationFailureException(final String description) {
		super(description);
	}
	
	/**
	 * @param description La descripción del error.
	 * @param cause La causa del error.
	 */
	public ConnectionCreationFailureException(final String description, final Throwable cause) {
		super(description, cause);
	}
}
