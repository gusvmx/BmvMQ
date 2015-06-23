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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bursatec.bmvmq.config.bind.AcknowledgeModeType;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.util.ResourceUtils;

/**
 * @author gus
 *
 */
public class BmvMqConfigurationReaderTest {

	/***/
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	/**
	 * 
	 */
	@Test
	public final void getConfigFromDefaultLocation() {
		final int defaultReconnectionInterval = 30000;
		final int defaultconnectionTimeout = 30000;
		try {
			BmvMq config = BmvMqConfigurationReader.readConfiguration(ResourceUtils.CLASSPATH_PREFIX + "/bmvMq.xml");
			Assert.assertNotNull(config);
			Assert.assertEquals(AcknowledgeModeType.AUTO_ACKNOWLEDGE, config.getAcknowledgeMode());
			Assert.assertEquals(defaultReconnectionInterval, config.getReconnectionInterval().intValue());
			Assert.assertEquals(defaultconnectionTimeout, config.getConnectionTimeout().intValue());
			Assert.assertTrue(config.isAsyncSend().booleanValue());
			Assert.assertTrue(config.isPersistentDeliveryMode().booleanValue());
			Assert.assertFalse(config.isUseCompression().booleanValue());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public final void readXmlFullyConfigured() {
		BmvMq config = null;
		try {
			config = BmvMqConfigurationReader.readConfiguration(
					ResourceUtils.CLASSPATH_PREFIX + "/fullyConfigured.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertEquals("clientId", config.getClientId());
		Assert.assertEquals("url", config.getUrl());
		Assert.assertEquals("username", config.getUsername());
		Assert.assertEquals("password", config.getPassword());
		Assert.assertEquals(AcknowledgeModeType.SESSION_TRANSACTED, config.getAcknowledgeMode());
		Assert.assertEquals(1, config.getReconnectionInterval().intValue());
		Assert.assertEquals(1, config.getConnectionTimeout().intValue());
		Assert.assertFalse(config.isAsyncSend());
		Assert.assertFalse(config.isPersistentDeliveryMode().booleanValue());
		Assert.assertTrue(config.isUseCompression().booleanValue());
	}
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public final void configurationNotFoundWithinClasspath() throws FileNotFoundException {
		expectedException.expect(FileNotFoundException.class);
		BmvMqConfigurationReader.readConfiguration("classpath:invalidFile.xml");
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
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public final void configNotFoundOnFS() throws FileNotFoundException {
		String absolutePath = new File("inexistent.xml").getAbsolutePath();
		expectedException.expect(FileNotFoundException.class);
		BmvMqConfigurationReader.readConfiguration(absolutePath);
	}
}
