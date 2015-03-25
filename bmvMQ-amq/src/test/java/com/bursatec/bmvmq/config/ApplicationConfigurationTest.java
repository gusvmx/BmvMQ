/**
 * Bursatec - BMV Sep 22, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.config;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.config.bind.DestinationResolverType;

/**
 * @author gus
 *
 */
public class ApplicationConfigurationTest {

	/**
	 * @throws FileNotFoundException  Si no encuentra el archivo de configuracion
	 * 
	 */
	@Test
	public final void dynamicDestResolver() throws FileNotFoundException {
		BmvMq config = BmvMqConfigurationReader.readConfiguration("classpath:bmvMq.xml");
		ApplicationConfiguration.setConfiguration(config);
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		context.close();
		Assert.assertEquals(DestinationResolverType.DYNAMIC, config.getDestinationResolver());
	}
	
	/**
	 * @throws FileNotFoundException Si no encuentra el archivo de configuracion
	 */
	@Test
	public final void jndiDestResolver() throws FileNotFoundException {
		BmvMq config = BmvMqConfigurationReader.readConfiguration("classpath:bmvMqJNDI.xml");
		ApplicationConfiguration.setConfiguration(config);
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		context.close();
		Assert.assertEquals(DestinationResolverType.JNDI, config.getDestinationResolver());
	}
	
	/**
	 * @throws FileNotFoundException Si no encuentra el archivo de configuracion
	 */
	@Test
	public final void hybridDestResolver() throws FileNotFoundException {
		BmvMq config = BmvMqConfigurationReader.readConfiguration("classpath:bmvMqHYBRID.xml");
		ApplicationConfiguration.setConfiguration(config);
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		context.close();
		Assert.assertEquals(DestinationResolverType.HYBRID, config.getDestinationResolver());
	}
}
