/**
 * 
 */
package com.bursatec.bmvmq.jmx;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.bmvmq.JmsProvider;

/**
 * @author gus
 *
 */
public final class MBeanFactory {
	
	/***/
	private static final Logger LOGGER = LoggerFactory.getLogger(MBeanFactory.class);
	/** El dominio a utilizar para los objetos JMX de este componente. */
	private static final String BMV_MQ_DOMAIN = "com.bursatec.bmvmq";
	/** Criterio de búsqueda para todos los MBeans registrados bajo el dominio com.bursatec.bmvmq. */
	public static final String QUERY_ALL_BMVMQ_BEANS = BMV_MQ_DOMAIN + "*:*";
	
	/** Privado para evitar instancias de está fábrica. */
	private MBeanFactory() { }

	/**
	 * Registra en la consola de monitoreo JMX el objeto mbean recibido.
	 * @param mbean El mbean a registrar.
	 * @param name El nombre único para la consola de monitoreo del mbean.
	 * @return La instancia mbean registrada en JMX.
	 */
	public static ObjectInstance createMbean(final Object mbean, final String name) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName objectName = new ObjectName(name);
			return mbs.registerMBean(mbean, objectName);
		} catch (MalformedObjectNameException e) {
			throw new JMXException("Nombre incorrecto para el MBean", e);
		} catch (InstanceAlreadyExistsException e) {
			throw new JMXException("Ya existe un MBean con el nombre indicado: " + name, e);
		} catch (MBeanRegistrationException e) {
			throw new JMXException("Imposible registrar el MBean", e);
		} catch (NotCompliantMBeanException e) {
			throw new JMXException("El MBean a registrar no cumple con los requisitos.", e);
		}
	}

	/**
	 * @param name El patrón para buscar los mbeans a desregistrar.
	 */
	public static void unregisterMbeans(final String name) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName objectName = new ObjectName(name);
			Set<ObjectName> mbeanNames = mbs.queryNames(objectName, null);
			for (ObjectName mbeanName : mbeanNames) {
				unregisterMBean(mbeanName);
			}
		} catch (MalformedObjectNameException e) {
			LOGGER.error("El patron {} para eliminar los mbeans es incorrecto", name);
			throw new JMXException("El patron para eliminar los MBeans es incorrecto: " + name, e);
		}
	}
	
	/**
	 * Desregistra el mbean indicado.
	 * @param mbeanName El nombre del mbean a eliminar.
	 */
	private static void unregisterMBean(final ObjectName mbeanName) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(mbeanName);
		} catch (MBeanRegistrationException e) {
			LOGGER.error("Ocurrió un error al desregistrar el mbean {}", mbeanName, e);
			throw new JMXException("Ocurrío un error al desregistrar el mbean " + mbeanName, e);
		} catch (InstanceNotFoundException e) {
			LOGGER.warn("El mbean {} no se encuentra registrado. Por lo tanto no se puede eliminar.",  mbeanName);
		}
	}

	/**
	 * @param destinationName El nombre del queue.
	 * @return El nombre para el MBean que mostrará estadísticas del queue indicado.
	 */
	public static String buildReceiverName(final String destinationName) {
		return BMV_MQ_DOMAIN + ".consumer:type=queue,name=" + destinationName;
	}
	
	/**
	 * @param destinationName El nombre del queue.
	 * @return El nombre para el MBean que mostrará estadísticas del queue indicado.
	 */
	public static String buildSenderName(final String destinationName) {
		return BMV_MQ_DOMAIN + ".producer:type=queue,name=" + destinationName;
	}

	/**
	 * @param destinationName El nombre del tópico.
	 * @return El nombre para el MBean que mostrará estadísticas del queue indicado.
	 */
	public static String buildSubscriberName(final String destinationName) {
		return BMV_MQ_DOMAIN + ".consumer:type=topic,name=" + destinationName;
	}
	
	/**
	 * @param destinationName El nombre del tópico.
	 * @return El nombre para el MBean que mostrará estadísticas del queue indicado.
	 */
	public static String buildPublisherName(final String destinationName) {
		return BMV_MQ_DOMAIN + ".producer:type=topic,name=" + destinationName;
	}

	/**
	 * @param provider El proveedor de JMS utilizado
	 * @return El nombre del Mbean para las información del broker.
	 */
	public static String buildBrokerName(final JmsProvider provider) {
		return BMV_MQ_DOMAIN + ":type=" + provider;
	}
}
