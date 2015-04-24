/**
 * Bursatec - BMV Apr 22, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.exception;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus - Bursatec
 * @version 1.0
 */
public class BmvMqExceptionTest {

	/***/
	@Test
	public final void test() {
		JmsAccessException exception = null; 
		exception = new ConnectionCreationFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new ConnectionCreationFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
		
		exception = new ConnectionFactoryCreationFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new ConnectionFactoryCreationFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
		
		exception = new ConsumerCreationFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new ConsumerCreationFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
		
		exception = new MessageCreatorCreationFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new MessageCreatorCreationFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
		
		exception = new SendMessageFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new SendMessageFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
		
		exception = new SessionCreationFailureException("");
		Assert.assertTrue(exception instanceof RuntimeException);
		exception = new SessionCreationFailureException("", new FileNotFoundException());
		Assert.assertTrue(exception instanceof RuntimeException);
	}
}
