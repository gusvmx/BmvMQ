/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.config;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.util.ResourceUtils;

/**
 * @author gus
 *
 */
public class BmvMqConfigurationReaderTest {

	/**
	 * 
	 */
	@Test
	public final void getConfigFromDefaultLocation() {
		try {
			BmvMq config = BmvMqConfigurationReader.readConfiguration(ResourceUtils.CLASSPATH_PREFIX + "/bmvMq.xml");
			Assert.assertNotNull(config);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	/**
	 * 
	 */
	@Test
	public final void configurationNotFoundWithinClasspath() {
		try {
			BmvMq config = BmvMqConfigurationReader.readConfiguration("classpath:invalidFile.xml");
			Assert.fail("Se debio haber lanzado una excepcion por no encontrar el archivo " + config);
		} catch (FileNotFoundException e) {
			Assert.assertNotNull(e);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public final void getConfigFromFS() {
		String absolutePath = new File("target/test-classes/bmvMq.xml").getAbsolutePath();
		try {
			BmvMq config = BmvMqConfigurationReader.readConfiguration(absolutePath);
			Assert.assertNotNull(config);
			
			config = BmvMqConfigurationReader.readConfiguration("file:" + absolutePath);
			Assert.assertNotNull(config);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail("Se debio haber cargado la configuracion desde FS");
		}
	}
	
	/**
	 * 
	 */
	@Test
	public final void configNotFoundOnFS() {
		String absolutePath = new File("inexistent.xml").getAbsolutePath();
		try {
			BmvMq config = BmvMqConfigurationReader.readConfiguration(absolutePath);
			Assert.fail("Se debio haber lanzado una excepcion por no encontrar el archivo: " + config);
		} catch (FileNotFoundException e) {
			Assert.assertNotNull(e);
		}
	}
}
