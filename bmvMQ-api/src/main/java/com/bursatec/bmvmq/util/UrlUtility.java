package com.bursatec.bmvmq.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author gus
 *
 */
public final class UrlUtility {

	/** Default constructor. */
	private UrlUtility() { }
	
	/**
	 * @param params El mapa que contiene los parámetros a colocar.
	 * @return Una cadena con los parámetros en el siguiente formato llave=valor, cada uno separado por &.
	 */
	public static String encodeParameters(final Map<String, String> params) {
		StringBuilder formattedParameters = new StringBuilder();
		Set<Entry<String, String>> entrySet = params.entrySet();
		int size = entrySet.size();
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			formattedParameters.append(String.format("%1$s=%2$s", entry.getKey(), entry.getValue()));
			if (++i < size) {
				formattedParameters.append("&");
			}
		}
		return formattedParameters.toString();
	}

	
}
