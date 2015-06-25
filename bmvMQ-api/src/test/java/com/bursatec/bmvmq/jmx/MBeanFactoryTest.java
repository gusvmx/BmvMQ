/**
 * 
 */
package com.bursatec.bmvmq.jmx;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bursatec.bmvmq.JmsProvider;
import com.bursatec.bmvmq.jmx.stats.JmsProducerStats;

/**
 * @author gus
 *
 */
public class MBeanFactoryTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@After
	public final void tearDown() {
		MBeanFactory.unregisterMbeans(MBeanFactory.QUERY_ALL_BMVMQ_BEANS);
	}
	
	@Test
	public final void generalInfo() throws MalformedObjectNameException, InstanceNotFoundException {
		String name = "com.bursatec.bmvmq:type=bmvmq-junit,name=Info";
		Object mbean = new JmsProducerStats("");
		Object expectedMbean = MBeanFactory.createMbean(mbean, name);
		Object registeredMbean = null;
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = new ObjectName(name);
		try {
			registeredMbean = mbs.getObjectInstance(objectName);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertEquals(expectedMbean, registeredMbean);
		MBeanFactory.unregisterMbeans(name);
		
		expectedException.expect(InstanceNotFoundException.class);
		registeredMbean = mbs.getObjectInstance(objectName);
		Assert.fail("Se debió arrojar una excepción por no encontrar el mbean.");
	}
	
	@Test
	public final void testInvalidName() {
		String name = "com.bursate.bmvmq";
		expectedException.expect(JMXException.class);
		MBeanFactory.createMbean(new JmsProducerStats(""), name);
	}
	
	@Test
	public final void testDuplicatedMBeanName() {
		String name = "com.bursatec.bmvmq:type=duplicated";
		MBeanFactory.createMbean(new JmsProducerStats(""), name);
		expectedException.expect(JMXException.class);
		MBeanFactory.createMbean(new JmsProducerStats(""), name);
		MBeanFactory.unregisterMbeans(name);
	}
	
	@Test
	public final void testBuildMbeanName() {
		Assert.assertEquals("com.bursatec.bmvmq.consumer:type=queue,name=gus", MBeanFactory.buildReceiverName("gus"));
		Assert.assertEquals("com.bursatec.bmvmq.producer:type=queue,name=gus", MBeanFactory.buildSenderName("gus"));
		
		Assert.assertEquals("com.bursatec.bmvmq.consumer:type=topic,name=gus", MBeanFactory.buildSubscriberName("gus"));
		Assert.assertEquals("com.bursatec.bmvmq.producer:type=topic,name=gus", MBeanFactory.buildPublisherName("gus"));
		
		Assert.assertEquals("com.bursatec.bmvmq:type=" + JmsProvider.ACTIVE_MQ, 
				MBeanFactory.buildBrokerName(JmsProvider.ACTIVE_MQ));
	}
	
	@Test
	public final void testUnregisterSubdomains() throws MalformedObjectNameException {
		MBeanFactory.createMbean(new JmsProducerStats(""), MBeanFactory.buildReceiverName("gus"));
		MBeanFactory.createMbean(new JmsProducerStats(""), MBeanFactory.buildSenderName("gus"));
		MBeanFactory.createMbean(new JmsProducerStats(""), MBeanFactory.buildPublisherName("gus"));
		MBeanFactory.createMbean(new JmsProducerStats(""), MBeanFactory.buildSubscriberName("gus"));
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = new ObjectName(MBeanFactory.QUERY_ALL_BMVMQ_BEANS);
		Set<ObjectName> mbeanNames = mbs.queryNames(objectName, null);
		Assert.assertFalse(mbeanNames.isEmpty());
		Assert.assertEquals(4, mbeanNames.size());
		
		MBeanFactory.unregisterMbeans(MBeanFactory.QUERY_ALL_BMVMQ_BEANS);
		mbeanNames = mbs.queryNames(objectName, null);
		Assert.assertTrue(mbeanNames.isEmpty());
	}
}
