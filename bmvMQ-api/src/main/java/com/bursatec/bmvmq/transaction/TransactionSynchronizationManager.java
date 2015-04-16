/**
 * Bursatec - BMV Apr 8, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.transaction;

import javax.jms.Session;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public final class TransactionSynchronizationManager {

	/***/
	private static final ThreadLocal<Session> LOCAL_SESSION = new ThreadLocal<Session>();
	/***/
	private TransactionSynchronizationManager() { }

	
	/**
	 * @param session La sesión que se asignará al hilo actual.
	 */
	public static void bindSession(final Session session) {
		if (LOCAL_SESSION.get() != null) {
			throw new RuntimeException("Ya existe una sesion actualmente asignada al hilo actual");
		}
		LOCAL_SESSION.set(session);
	}
	
	/**
	 * Desasigna la sesión del hilo actual.
	 */
	public static void unbindSession() {
		LOCAL_SESSION.set(null);
	}
	
	/**
	 * @return Indica si existe una sesión asociada o no.
	 */
	public static boolean isSessionBound() {
		return LOCAL_SESSION.get() != null;
	}
	
	/**
	 * @return La sesión asociada o <code>null</code> en caso de no haber en el hilo actual.
	 */
	public static Session getBoundSession() {
		return LOCAL_SESSION.get();
	}
}
