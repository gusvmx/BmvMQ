/**
 * Bursatec - BMV Sep 9, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq;

import java.io.FileNotFoundException;

import com.bursatec.bmvmq.factory.ActiveMqComponentFactory;
import com.bursatec.bmvmq.factory.SonicMqComponentFactory;

/**
 * Fábrica de plantillas MQ.
 * 
 * @author gus
 */
public final class BmvMqFactory {

	/** Constructor por default privado para evitar instancias de está fábrica. */
	private BmvMqFactory() {
	}

	/**
	 * @return Una instancia de MqTemplate configurada con ActiveMQ.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar en el classpath el archivo de
	 *             configuración bmvMq.xml
	 */
	public static MqTemplate activeMQ() throws FileNotFoundException {
		return activeMQ(MqTemplate.DEFAULT_CONFIG_FILE_LOCATION);
	}

	/**
	 * @param configFileLocation
	 *            La ubicación del archivo de configuración.
	 * 
	 *            La ubicación del archivo puede llevar los siguientes prefijos:
	 *            classpath:, file:, jar:, zip:
	 * 
	 *            En caso de no contar con un prefijo, el archivo se buscará en
	 *            el FS.
	 * @return Una instancia de MqTemplate configurada con ActiveMQ con el
	 *         archivo de configuración proporcionado.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar el archivo de configuración en la
	 *             ubicación indicada.
	 */
	public static MqTemplate activeMQ(final String configFileLocation)
			throws FileNotFoundException {
		return new MqTemplate(new ActiveMqComponentFactory(configFileLocation));
	}

	/**
	 * @return Una instancia de MqTemplate configurada con SonicMQ.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar en el classpath el archivo de
	 *             configuración bmvMq.xml
	 */
	public static MqTemplate sonicMQ() throws FileNotFoundException {
		return sonicMQ(MqTemplate.DEFAULT_CONFIG_FILE_LOCATION);
	}

	/**
	 * @param configFileLocation
	 *            La ubicación del archivo de configuración.
	 * 
	 *            La ubicación del archivo puede llevar los siguientes prefijos:
	 *            classpath:, file:, jar:, zip:
	 * 
	 *            En caso de no contar con un prefijo, el archivo se buscará en
	 *            el FS.
	 * @return Una instancia de MqTemplate configurada con SonicMQ con el
	 *         archivo de configuración proporcionado.
	 * @throws FileNotFoundException
	 *             En caso de no encontrar el archivo de configuración en la
	 *             ubicación indicada.
	 */
	public static MqTemplate sonicMQ(final String configFileLocation)
			throws FileNotFoundException {
		return new MqTemplate(new SonicMqComponentFactory(configFileLocation));
	}

}
