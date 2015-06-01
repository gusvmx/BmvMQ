/**
 * Bursatec - BMV Apr 15, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import java.io.IOException;

import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqContext;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class ActiveMqExceptionListener extends BmvMqExceptionListenerAdapter implements
		TransportListener {

	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqExceptionListener.class);
	
	@Override
	public final void onCommand(final Object command) {
		LOGGER.debug(command.toString());
	}

	@Override
	public final void onException(final IOException error) {
		LOGGER.error("Error inrecuperable ha ocurrido en el transporte.", error);
	}

	@Override
	public final void transportInterupted() {
		BmvMqContext.getConnectionListener().messagingInterrupted();
	}

	@Override
	public final void transportResumed() {
		BmvMqContext.getConnectionListener().messagingResumed();
	}

	
}
