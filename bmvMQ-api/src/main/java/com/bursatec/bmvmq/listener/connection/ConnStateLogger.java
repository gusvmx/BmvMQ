/**
 * Bursatec - BMV Apr 17, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.listener.BmvMqConnStateListener;

/**
 * Registra todos los eventos relacionados con la conexión hacia el broker y
 * notifica al connStateListener registrado.
 * 
 * @author gus - Bursatec
 * @version 1.0
 */
public class ConnStateLogger implements BmvMqConnStateListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnStateLogger.class);
	/** El callback final que recibirá la notificación de eventos. */
	private BmvMqConnStateListener targetListener;
	
	/** Constructor por default que coloca como destino un listener vacío. NullObjectPattern */
	public ConnStateLogger() {
		this.targetListener = new EmptyConnStateListener();
	}
	
	/**
	 * Constructor que recibe el listener que será notificado por cada evento recibido.
	 * @param targetListener El listener destino registrado.
	 */
	public ConnStateLogger(final BmvMqConnStateListener targetListener) {
		this.targetListener = targetListener;
	}

	@Override
	public final void messagingInterrupted() {
		LOGGER.error("Se ha interrumpido la conexión hacia el broker JMS. Se intentará reconectar automáticamente.");
		targetListener.messagingInterrupted();
	}

	@Override
	public final void messagingResumed() {
		LOGGER.info("Se ha establecido la conexión hacia el broker JMS.");
		targetListener.messagingResumed();
	}

}
