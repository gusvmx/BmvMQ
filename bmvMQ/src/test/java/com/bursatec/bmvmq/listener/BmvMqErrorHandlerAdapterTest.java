/**
 * Bursatec - BMV Sep 25, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.listener;

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.InvalidBmvMqConfigurationException;
import com.bursatec.bmvmq.config.ApplicationConfiguration;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.throughput.SimpleMessageReceiver;

/**
 * @author gus
 *
 */
public class BmvMqErrorHandlerAdapterTest {

	/***/
	@Test
	public final void noErrorHandlerTest() {
		BmvMq config = new BmvMq();
		ApplicationConfiguration.setConfiguration(config);
		BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
		adapter.handleError(new RuntimeException("Error inducido"));
	}
	
	/***/
	@Test
	public final void withErrorHandlerTest() {
		BmvMq config = new BmvMq();
		config.setErrorHandlerClassName(CustomBmvMqErrorHandler.class.getName());
		ApplicationConfiguration.setConfiguration(config);
		BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
		adapter.handleError(new RuntimeException("Error inducido"));
	}
	
	/***/
	@Test
	public final void classNotFoundTest() {
		BmvMq config = new BmvMq();
		config.setErrorHandlerClassName(CustomBmvMqErrorHandler.class.getName() + "1");
		ApplicationConfiguration.setConfiguration(config);
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una class not found exception");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(ClassNotFoundException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void cannotInstantiateTest() {
		BmvMq config = new BmvMq();
		config.setErrorHandlerClassName(SimpleMessageReceiver.class.getName());
		ApplicationConfiguration.setConfiguration(config);
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una IllegalAccessException");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(IllegalAccessException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void instantiateInterfaceTest() {
		BmvMq config = new BmvMq();
		config.setErrorHandlerClassName(MessageListener.class.getName());
		ApplicationConfiguration.setConfiguration(config);
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una InstantiationException");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(InstantiationException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void classCastException() {
		BmvMq config = new BmvMq();
		config.setErrorHandlerClassName(BmvMqExceptionListener.class.getName());
		ApplicationConfiguration.setConfiguration(config);
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter();
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una class cast exception");
		} catch (ClassCastException e) {
			Assert.assertEquals(ClassCastException.class, e.getClass());
		}
	}
	
}
