/**
 * 
 */
package com.bursatec.bmvmq.jmx.mxbean;

import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;

/**
 * @author gus
 *
 */
public interface JmsConnectionInfoMXBean {

	/**
	 * @return Obtiene la URL de conexión.
	 */
	String getUrl();

	/**
	 * @return Obtiene el clientId.
	 */
	String getClientId();

	/**
	 * @return Obtiene el tipo de acuse de recibo.
	 */
	AcknowledgeModeType getAcknowledgeMode();

	/**
	 * @return Indica si envía mensajes asíncronamente.
	 */
	boolean isAsyncSend();

	/**
	 * @return El tiempo de espera de la conexión.
	 */
	int getConnectionTimeout();

	/**
	 * @return El tiempo de reconexión.
	 */
	int getReconnectionInterval();
}
