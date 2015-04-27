/**
 * 
 */
package com.bursatec.bmvmq.config;

import javax.jms.ExceptionListener;

import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.listener.BmvMqConnStateListener;
import com.bursatec.bmvmq.listener.DefaultExceptionListener;
import com.bursatec.bmvmq.listener.connection.ConnStateLogger;

/**
 * Contenedor del contexto de BmvMQ.
 * 
 * Este contenedor almacenará atributos que pueden ser utilizados durante todo
 * el ciclo de vida de la aplicación.
 * 
 * @author gus
 *
 */
public final class BmvMqContext {
	
	/**
	 * La configuracion de BmvMQ.
	 */
	private static BmvMq configuration;
	/** El callback donde se notificarán los errores ocurridos durante la mensajería. */
	private static ExceptionListener exceptionListener = new DefaultExceptionListener();
	/** El callback donde se notificarán los eventos ocurridos relacionados con la conexión al broker. */
	private static BmvMqConnStateListener connectionListener = new ConnStateLogger();

	/** Constructor privado para evitar que se generen instancias de este contenedor. */
	private BmvMqContext() { }
	
	/**
	 * @param configuration the configuration to set
	 */
	public static void setConfiguration(final BmvMq configuration) {
		BmvMqContext.configuration = configuration;
	}

	/**
	 * @return the configuration
	 */
	public static BmvMq getConfiguration() {
		return configuration;
	}
	/**
	 * @return the exceptionListener
	 */
	public static ExceptionListener getExceptionListener() {
		return exceptionListener;
	}
	/**
	 * @param exceptionListener the exceptionListener to set
	 */
	public static void setExceptionListener(
			final ExceptionListener exceptionListener) {
		BmvMqContext.exceptionListener = new DefaultExceptionListener(exceptionListener);
	}
	/**
	 * @return the connectionListener
	 */
	public static BmvMqConnStateListener getConnectionListener() {
		return connectionListener;
	}
	/**
	 * @param connectionListener the connectionListener to set
	 */
	public static void setConnectionListener(final BmvMqConnStateListener connectionListener) {
		BmvMqContext.connectionListener = new ConnStateLogger(connectionListener);
	}

}
