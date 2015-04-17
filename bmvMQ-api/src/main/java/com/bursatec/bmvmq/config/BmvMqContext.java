/**
 * 
 */
package com.bursatec.bmvmq.config;

import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.listener.BmvMqExceptionListener;
import com.bursatec.bmvmq.listener.DefaultExceptionListener;

/**
 * @author gus
 *
 */
public final class BmvMqContext {
	
	/**
	 * La configuracion de BmvMQ.
	 */
	private static BmvMq configuration;
	/***/
	private static BmvMqExceptionListener exceptionListener = new DefaultExceptionListener();

	/***/
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
	public static BmvMqExceptionListener getExceptionListener() {
		return exceptionListener;
	}
	/**
	 * @param exceptionListener the exceptionListener to set
	 */
	public static void setExceptionListener(
			final BmvMqExceptionListener exceptionListener) {
		BmvMqContext.exceptionListener = exceptionListener;
	}

}
