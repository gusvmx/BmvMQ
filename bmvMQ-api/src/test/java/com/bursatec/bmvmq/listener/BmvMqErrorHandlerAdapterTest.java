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

/**
 * @author gus
 *
 */
public class BmvMqErrorHandlerAdapterTest {

	/***/
	@Test
	public final void noErrorHandlerTest() {
		BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(null);
		adapter.handleError(new RuntimeException("Error inducido"));
	}
	
	/***/
	@Test
	public final void withErrorHandlerTest() {
		BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(CustomBmvMqErrorHandler.class.getName());
		adapter.handleError(new RuntimeException("Error inducido"));
	}
	
	/***/
	@Test
	public final void classNotFoundTest() {
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(CustomBmvMqErrorHandler.class.getName() 
					+ "1");
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una class not found exception");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(ClassNotFoundException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void cannotInstantiateTest() {
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(MockConstants.class.getName());
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una IllegalAccessException");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(IllegalAccessException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void instantiateInterfaceTest() {
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(MessageListener.class.getName());
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una InstantiationException");
		} catch (InvalidBmvMqConfigurationException e) {
			Assert.assertEquals(InstantiationException.class, e.getCause().getClass());
		}
	}
	
	/***/
	@Test
	public final void classCastException() {
		try {
			BmvMqErrorHandlerAdapter adapter = new BmvMqErrorHandlerAdapter(BmvMqExceptionListener.class.getName());
			adapter.handleError(null);
			Assert.fail("Se debio arrojar una class cast exception");
		} catch (ClassCastException e) {
			Assert.assertEquals(ClassCastException.class, e.getClass());
		}
	}
	
}
