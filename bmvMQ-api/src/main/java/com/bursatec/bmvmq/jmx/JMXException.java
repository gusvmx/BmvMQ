/**
 * Bursatec - BMV Jun 10, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.jmx;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class JMXException extends RuntimeException {

	/***/
	private static final long serialVersionUID = -4977206217497262343L;

	/**
	 * @param description La descripción de la causa del error.
	 */
	public JMXException(final String description) {
		super(description);
	}
	
	/**
	 * @param description La descripción de la causa del error.
	 * @param cause La causa origen.
	 */
	public JMXException(final String description, final Throwable cause) {
		super(description, cause);
	}
	
}
