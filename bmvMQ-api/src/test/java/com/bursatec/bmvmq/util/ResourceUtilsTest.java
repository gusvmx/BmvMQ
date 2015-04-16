/**
 * 
 */
package com.bursatec.bmvmq.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author gus
 *
 */
public class ResourceUtilsTest {
	
	/***/
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	/***/
	@Test
	public final void getFileFromFileSystem() {
		File resource = null;
		final String resourceLocation = "/test.test";
		try {
			resource = ResourceUtils.getFile(ResourceUtils.FILE_PREFIX + resourceLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(resource);
		Assert.assertEquals(resourceLocation, resource.getAbsolutePath());
		Assert.assertFalse(resource.exists());
	}
	
	/***/
	@Test
	public final void getFileFromFileSystemWithNoPrefix() {
		File resource = null;
		final String resourceLocation = "/test.test";
		try {
			resource = ResourceUtils.getFile(resourceLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(resource);
		Assert.assertEquals(resourceLocation, resource.getAbsolutePath());
		Assert.assertFalse(resource.exists());
	}
	
	/**
	 * @throws FileNotFoundException */
	@Test
	public final void getInexistentFileFromClasspath() throws FileNotFoundException {
		final String resourceLocation = "/test.test";
		expectedException.expect(FileNotFoundException.class);
		ResourceUtils.getFile(ResourceUtils.CLASSPATH_PREFIX + resourceLocation);
	}
	
	/***/
	@Test
	public final void getFileFromClasspath() {
		File resource = null;
		final String resourceLocation = "/com/bursatec/bmvmq/util/ResourceUtilsTest.class";
		try {
			resource = ResourceUtils.getFile(ResourceUtils.CLASSPATH_PREFIX + resourceLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
	}
}
