/**
 * 
 */
package com.bursatec.bmvmq.jmx.stats;

import com.bursatec.bmvmq.config.BmvMqContext;
import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.jmx.mxbean.JmsConnectionInfoMXBean;

/**
 * @author gus
 *
 */
public class JmsConnectionInfo implements JmsConnectionInfoMXBean {

	@Override
	public final String getUrl() {
		return BmvMqContext.getConfiguration().getUrl();
	}

	@Override
	public final String getClientId() {
		return BmvMqContext.getConfiguration().getClientId();
	}

	@Override
	public final AcknowledgeModeType getAcknowledgeMode() {
		return BmvMqContext.getConfiguration().getAcknowledgeMode();
	}

	@Override
	public final boolean isAsyncSend() {
		return BmvMqContext.getConfiguration().isAsyncSend();
	}

	@Override
	public final int getConnectionTimeout() {
		return BmvMqContext.getConfiguration().getConnectionTimeout();
	}

	@Override
	public final int getReconnectionInterval() {
		return BmvMqContext.getConfiguration().getReconnectionInterval();
	}

}
