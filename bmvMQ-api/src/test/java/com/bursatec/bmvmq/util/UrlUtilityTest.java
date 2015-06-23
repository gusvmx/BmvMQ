/**
 * 
 */
package com.bursatec.bmvmq.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author gus
 *
 */
public class UrlUtilityTest {

	/** Excepción a esperar. */
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	/***/
	@Test
	public final void paramsTest() {
		Map<String, String> params = new HashMap<String, String>();
		Assert.assertEquals("", UrlUtility.encodeParameters(params));
		
		params.put("maxReconnectDelay", "3");
		Assert.assertEquals("maxReconnectDelay=3", UrlUtility.encodeParameters(params));
		
		params.put("jms.useCompression", "true");
		Assert.assertEquals("maxReconnectDelay=3&jms.useCompression=true", UrlUtility.encodeParameters(params));
		
		expectedException.expect(NullPointerException.class);
		UrlUtility.encodeParameters(null);
	}
}
