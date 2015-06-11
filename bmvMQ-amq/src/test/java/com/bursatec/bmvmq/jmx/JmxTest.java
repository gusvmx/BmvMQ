/**
 * 
 */
package com.bursatec.bmvmq.jmx;

import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.junit.Assert;
import org.junit.Test;

import com.bursatec.bmvmq.core.BmvMqTemplate;
import com.bursatec.bmvmq.listener.MsgReceivedCounterMessageListener;

/**
 * @author gus
 *
 */
public class JmxTest {

	@Test
	public final void test() throws MalformedObjectNameException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException, FileNotFoundException {
		BmvMqTemplate template = new BmvMqTemplate();
		template.receive("gus", new MsgReceivedCounterMessageListener());
		template.subscribe("gus", new MsgReceivedCounterMessageListener());
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName objectName = new ObjectName(MBeanFactory.buildQueueName("gus"));
		Assert.assertEquals(0L, mbs.getAttribute(objectName, "MessagesReceived"));
		
		objectName = new ObjectName(MBeanFactory.buildTopicName("gus"));
		Assert.assertEquals(0L, mbs.getAttribute(objectName, "MessagesReceived"));
		template.stop();
		//Tras terminar no debe existir ningún MBean.
		Set<ObjectName> mbeans = mbs.queryNames(new ObjectName(MBeanFactory.BMV_MQ_DOMAIN + "*"), null);
		Assert.assertTrue(mbeans.isEmpty());
	}
}
