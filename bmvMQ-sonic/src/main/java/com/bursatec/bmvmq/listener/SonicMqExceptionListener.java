/**
 * Bursatec - BMV Apr 15, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqContext;

import progress.message.jclient.ConnectionStateChangeListener;
import progress.message.jclient.Constants;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class SonicMqExceptionListener extends BmvMqExceptionListenerAdapter implements ConnectionStateChangeListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(SonicMqExceptionListener.class);
	
	@Override
	public final void connectionStateChanged(final int state) {
		BmvMqExceptionListener exceptionListener = BmvMqContext.getExceptionListener();
		switch (state) {
		case Constants.RECONNECTING:
			LOGGER.info("Intentando reestablecer la conexión hacia el broker JMS");
			break;
		case Constants.ACTIVE:
			LOGGER.info("Se ha establecido la conexión hacia el broker JMS.");
			exceptionListener.messagingResumed();
			break;
		case Constants.FAILED:
			LOGGER.error("Se ha interrumpido la conexión hacia el broker JMS. "
					+ "Se intentará reconectar automáticamente.");
			exceptionListener.messagingInterrupted();
			break;
		default:
			LOGGER.warn("Cambio de estado registrado: [{}]", state);
			break;
		}
	}

}
