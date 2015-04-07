/**
 * 
 */
package com.bursatec.bmvmq.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gus
 *
 */
public final class ResourceUtils {

	/***/
	public static final String CLASSPATH_PREFIX = "classpath:";
	/***/
	public static final String FILE_PREFIX = "file:";
	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);
	
	/***/
	private ResourceUtils() { }
	
	/**
	 * @param resourceLocation
	 *            La ubicación del archivo. Puede contener como prefijo file: o
	 *            classpath: indicando que el archivo se obtendra del file
	 *            system o del classpath respectivamente.
	 * @return El correspondiente objeto File.
	 * @throws FileNotFoundException
	 *             Si la URL no se puede resolver hacia un archivo en el sistema
	 *             de archivos.
	 */
	public static File getFile(final String resourceLocation) throws FileNotFoundException {
		String resourceLocationWithNoPrefix = resourceLocation;
		if (resourceLocation.startsWith(FILE_PREFIX)) {
			resourceLocationWithNoPrefix = resourceLocation.substring(FILE_PREFIX.length());
			return new File(resourceLocationWithNoPrefix);
		} else {
			if (resourceLocation.startsWith(CLASSPATH_PREFIX)) {
				resourceLocationWithNoPrefix = resourceLocation.substring(CLASSPATH_PREFIX.length());
			}
			URI resource = null;
			try {
				URL resourceUrl = ResourceUtils.class.getResource(resourceLocationWithNoPrefix);
				if (resourceUrl != null) {
					resource = resourceUrl.toURI();
				} else {
					throw new FileNotFoundException("No se ha podido encontrar el archivo " + resourceLocation 
							+ " dentro del classpath");
				}
			} catch (URISyntaxException e) {
				LOGGER.error("No se pudo obtener la URI del recurso \"" + resourceLocation + "\"", e);
				throw new FileNotFoundException(e.getMessage());
			}
			return new File(resource);
		}
	}
}
