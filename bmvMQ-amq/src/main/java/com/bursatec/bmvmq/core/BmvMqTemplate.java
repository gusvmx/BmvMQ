/**
 * Bursatec - BMV Sep 9, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.io.FileNotFoundException;

import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.factory.ActiveMqComponentFactory;

/**
 * @author gus
 *
 */
public class BmvMqTemplate extends MqTemplate {

	/**
	 * Constructor por default.
	 * 
	 * @throws FileNotFoundException
	 *             En caso de no encontrar en el classpath el archivo de
	 *             configuración bmvMq.xml
	 */
	public BmvMqTemplate() throws FileNotFoundException {
		this(MqTemplate.DEFAULT_CONFIG_FILE_LOCATION);
	}
	
	/**
	 * @param configFileLocation
	 *            La ubicación del archivo de configuración.
	 * 
	 *            La ubicación del archivo puede llevar los siguientes prefijos:
	 *            classpath:, file:, jar:, zip:
	 *            
	 *            En caso de no contar con un prefijo, el archivo se buscará en el FS.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar el archivo de configuración en la
	 *             ubicación indicada.
	 */
	public BmvMqTemplate(final String configFileLocation) throws FileNotFoundException {
		super(new ActiveMqComponentFactory(configFileLocation));
	}

}
