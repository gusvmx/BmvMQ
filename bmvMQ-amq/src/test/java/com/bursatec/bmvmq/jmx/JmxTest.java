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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bursatec.bmvmq.BmvMqTemplate;
import com.bursatec.bmvmq.JmsProvider;
import com.bursatec.bmvmq.MqTemplate;
import com.bursatec.bmvmq.listener.MsgReceivedCounterMessageListener;

/**
 * @author gus
 *
 */
public class JmxTest {
	
	/***/
	private MqTemplate template;
	
	@Before
	public final void setUp() throws FileNotFoundException {
		template = BmvMqTemplate.activeMQ();
	}
	
	@After
	public final void tearDown() {
		template.stop();
	}

	@Test
	public final void test() throws MalformedObjectNameException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException, FileNotFoundException {
		template.receive("gus", new MsgReceivedCounterMessageListener());
		template.subscribe("gus", new MsgReceivedCounterMessageListener());
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName objectName = new ObjectName(MBeanFactory.buildReceiverName("gus"));
		Assert.assertEquals(0L, mbs.getAttribute(objectName, "MessagesReceived"));
		
		objectName = new ObjectName(MBeanFactory.buildSubscriberName("gus"));
		Assert.assertEquals(0L, mbs.getAttribute(objectName, "MessagesReceived"));
		template.stop();
		//Tras terminar no debe existir ningún MBean.
		Set<ObjectName> mbeans = mbs.queryNames(new ObjectName(MBeanFactory.QUERY_ALL_BMVMQ_BEANS), null);
		Assert.assertTrue(mbeans.isEmpty());
	}
	
	@Test
	public final void testConnInfoMBean() throws MalformedObjectNameException, FileNotFoundException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = new ObjectName(MBeanFactory.buildBrokerName(JmsProvider.ACTIVE_MQ));
		Assert.assertTrue(mbs.isRegistered(objectName));
	}
}
