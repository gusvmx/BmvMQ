/**
 * Bursatec - BMV Apr 13, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.factory;

import java.io.FileNotFoundException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.config.BmvMqConfigurationReader;
import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.exception.ConnectionFactoryCreationFailureException;
import com.bursatec.bmvmq.listener.BmvMqExceptionListener;
import com.bursatec.bmvmq.listener.MessageListener;
import com.bursatec.bmvmq.listener.SonicMqExceptionListener;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class SonicMqComponentFactory extends JmsComponentFactory {
	
	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(SonicMqComponentFactory.class);

	/**
	 * @param configFileLocation La ubicación del archivo de configuración.
	 * @throws FileNotFoundException En caso de no encontrar el archivo de configuración.
	 */
	public SonicMqComponentFactory(final String configFileLocation)
			throws FileNotFoundException {
		super(configFileLocation);
	}

	@Override
	protected final ConnectionFactory getConnectionFactory(final BmvMq config) {
		progress.message.jclient.ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = new progress.message.jclient.ConnectionFactory();
			connectionFactory.setConnectionURLs(config.getUrl());
			connectionFactory.setClientID(config.getClientId());
			connectionFactory.setFaultTolerant(true);
			connectionFactory.setFaultTolerantReconnectTimeout(config.getReconnectionInterval());
			connectionFactory.setDefaultUser(config.getUsername());
			connectionFactory.setDefaultPassword(config.getPassword());
		} catch (JMSException e) {
			LOGGER.error("No se ha podido crear una fábrica de conexiones.\nURL: {}\nClientID: {}\nUser: {}", 
					config.getUrl(), config.getClientId(), config.getUsername(), e);
			throw new ConnectionFactoryCreationFailureException(
					"No se ha podido crear la fábrica de conexiones SonicMQ.", e);
			
		}
		LOGGER.info("Fábrica de conexiones ha sido inicializada con los siguientes valores: "
				+ "URL:{}, Username:{}, MaxConnections:{}, ClientID:{}", 
				config.getUrl(), config.getUsername(), config.getMaxConnections(), config.getClientId());
		return connectionFactory;
	}

	@Override
	public final MessageConsumer createExclusiveQueueConsumer(final String destination,
			final MessageListener messageListener) throws JMSException {
		LOGGER.warn("Esta implementación de BmvMQ no tiene implementada la funcionalidad de recepción exclusiva. "
				+ "Por lo tanto se establecerá una conexión de recepción estandar.");
		return createQueueConsumer(destination, messageListener);
	}

	@Override
	protected final void setProprietaryConnectionParams(final Connection connection)
			throws JMSException {
		progress.message.jimpl.Connection sonicConnection = (progress.message.jimpl.Connection) connection;
		String exceptionListenerName = BmvMqContext.getConfiguration().getErrorHandlerClassName();
		BmvMqExceptionListener exceptionListener = BmvMqConfigurationReader.initializeExceptionListener(
				exceptionListenerName);
		SonicMqExceptionListener exceptionListenerAdapter = new SonicMqExceptionListener(exceptionListener);
		sonicConnection.setExceptionListener(exceptionListenerAdapter);
		sonicConnection.setConnectionStateChangeListener(exceptionListenerAdapter);
	}

}