/**
 * Bursatec - BMV Sep 25, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

/**
 * @author Gustavo Vargas
 *
 */
public interface BmvMqErrorHandler {

	/**
	 * @param exception La excepción que provocó el error.
	 */
	void onException(Throwable exception);
}
