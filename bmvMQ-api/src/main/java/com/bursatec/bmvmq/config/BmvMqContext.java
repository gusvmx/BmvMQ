/**
 * 
 */
package com.bursatec.bmvmq.config;

import com.bursatec.bmvmq.config.bind.BmvMq;

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

}
