/**
 * Bursatec - BMV 19/09/2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.bursatec.bmvmq.InvalidBmvMqConfigurationException;
import com.bursatec.bmvmq.config.bind.BmvMq;
import com.bursatec.bmvmq.listener.BmvMqExceptionListener;
import com.bursatec.bmvmq.listener.DefaultExceptionListener;
import com.bursatec.bmvmq.util.ResourceUtils;

/**
 * @author Gustavo Vargas
 */
public final class BmvMqConfigurationReader {

	/***/
	private static final String SCHEMA_FILE_NAME = "bmvMq.xsd";
	/***/
	private static final String CONF_PACKAGE_NAME = "com.bursatec.bmvmq.config.bind";
	/***/
	private BmvMqConfigurationReader() { }

	/**
	 * @param configFileLocation La ubicación del archivo de configuración.
	 * @return La configuración en su representación de objetos simples Java.
	 * @throws FileNotFoundException Si el archivo indicado no se encuentra en la ubicación indicada.
	 */
	public static BmvMq readConfiguration(final String configFileLocation) throws FileNotFoundException {
		File configFile = ResourceUtils.getFile(configFileLocation);
		if (!configFile.exists()) {
			throw new FileNotFoundException("El archivo" + configFileLocation + " no existe.");
		}
		try {
			JAXBContext jaxbContext = null;
			Unmarshaller unmarshaller = null;
			jaxbContext = JAXBContext.newInstance(CONF_PACKAGE_NAME);
			
			unmarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			URI uri = BmvMqConfigurationReader.class.getClassLoader().getResource(SCHEMA_FILE_NAME).toURI();
			Schema schema = sf.newSchema(new Source[] { new StreamSource(uri.toString()) });
			unmarshaller.setSchema(schema);
			
			BmvMq config = (BmvMq) unmarshaller.unmarshal(configFile);
			if (config.isAsyncSend() == null) {
				config.setAsyncSend(true);
			}
			setExceptionListener(config);
			return config;
		} catch (SAXException e) {
			throw new InvalidBmvMqConfigurationException(e);
		} catch (URISyntaxException e) {
			throw new InvalidBmvMqConfigurationException("No es posible obtener la URI del archivo de configuración.",
					e);
		} catch (JAXBException e) {
			throw new InvalidBmvMqConfigurationException("El archivo de configuración es inválido", e);
		}
	}

	/**
	 * @param config Asigna el exception listener por default.
	 */
	private static void setExceptionListener(final BmvMq config) {
		if (config.getErrorHandlerClassName() == null) {
			config.setErrorHandlerClassName(DefaultExceptionListener.class.getName());
		}
	}
	
	/**
	 * @param exceptionListenerName El nombre del exception listener a instanciar.
	 * @return La instancia del exception listener proporcionado
	 */
	public static BmvMqExceptionListener initializeExceptionListener(final String exceptionListenerName) {
		try {
			@SuppressWarnings("unchecked")
			Class<BmvMqExceptionListener> exceptionListener = 
				(Class<BmvMqExceptionListener>) Class.forName(exceptionListenerName);
			return exceptionListener.newInstance();
		} catch (ClassNotFoundException e) {
			throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new InvalidBmvMqConfigurationException(e.getMessage(), e);
		}
	}

}
