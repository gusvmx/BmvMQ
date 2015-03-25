/**
 * Bursatec - BMV Sep 25, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ErrorHandler;

import com.bursatec.bmvmq.InvalidBmvMqConfigurationException;

/**
 * @author gus
 *
 */
public class BmvMqErrorHandlerAdapter implements ErrorHandler {

	/***/
	private Logger logger = LoggerFactory.getLogger(BmvMqErrorHandlerAdapter.class);
	/**
	 * El manejador de errores del cliente de BmvMQ.
	 */
	private BmvMqErrorHandler errorHandler;
	
	/**
	 * @param errorHandlerClassName El nombre de la clase.
	 */
	public BmvMqErrorHandlerAdapter(final String errorHandlerClassName) {
		if (errorHandlerClassName != null) {
			try {
				@SuppressWarnings("unchecked")
				Class<BmvMqErrorHandler> loadedClass = (Class<BmvMqErrorHandler>) 
						ClassUtils.forName(errorHandlerClassName, BmvMqErrorHandler.class.getClassLoader());
				this.errorHandler = loadedClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
			} catch (LinkageError e) {
				throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
			} catch (InstantiationException e) {
				throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
			}
		}
	}

	@Override
	public final void handleError(final Throwable exception) {
		logger.error(exception.getMessage(), exception);
		if (this.errorHandler != null) {
			this.errorHandler.onException(exception);
		}
	}

}
