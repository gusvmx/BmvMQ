/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 *
 */
public class InvalidBmvMqConfigurationExceptionTest {

	/**
	 * 
	 */
	@Test
	public final void test() {
		InvalidBmvMqConfigurationException exception = new InvalidBmvMqConfigurationException();
		Assert.assertNull(exception.getCause());
		
		exception = new InvalidBmvMqConfigurationException("Configuracion invalida");
		Assert.assertNull(exception.getCause());
		Assert.assertNotNull(exception.getMessage());
		
		exception = new InvalidBmvMqConfigurationException(new FileNotFoundException());
		Assert.assertNotNull(exception.getCause());
		Assert.assertEquals(FileNotFoundException.class.getName(), exception.getMessage());
		
		exception = new InvalidBmvMqConfigurationException("Configuracion invalida", new FileNotFoundException());
		Assert.assertNotNull(exception.getCause());
		Assert.assertNotNull(exception.getMessage());
	}
}
