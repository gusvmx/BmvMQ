package com.bursatec.bmvmq.core;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author gus
 *
 */
public class MockTextMessage implements TextMessage {

	/***/
	private String message;
	/**
	 * @param message El mensaje.
	 */
	public MockTextMessage(final String message) {
		this.message = message;
	}

	@Override
	public void acknowledge() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBody() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearProperties() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public final boolean getBooleanProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final byte getByteProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final double getDoubleProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final float getFloatProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final int getIntProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final String getJMSCorrelationID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final int getJMSDeliveryMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final long getJMSExpiration() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final String getJMSMessageID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final int getJMSPriority() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final boolean getJMSRedelivered() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final Destination getJMSReplyTo() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final long getJMSTimestamp() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final String getJMSType() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final long getLongProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final Object getObjectProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final Enumeration getPropertyNames() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final short getShortProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final String getStringProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final boolean propertyExists(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBooleanProperty(final String arg0, final boolean arg1)
			throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setByteProperty(final String arg0, final byte arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleProperty(final String arg0, final double arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloatProperty(final String arg0, final float arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIntProperty(final String arg0, final int arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSCorrelationID(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSCorrelationIDAsBytes(final byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSDeliveryMode(final int arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSDestination(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSExpiration(final long arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSMessageID(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSPriority(final int arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSRedelivered(final boolean arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSReplyTo(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSTimestamp(final long arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSType(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLongProperty(final String arg0, final long arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setObjectProperty(final String arg0, final Object arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortProperty(final String arg0, final short arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStringProperty(final String arg0, final String arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public final String getText() throws JMSException {
		return message;
	}

	@Override
	public void setText(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

}
