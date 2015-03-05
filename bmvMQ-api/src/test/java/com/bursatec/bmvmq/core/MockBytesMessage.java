/**
 * Bursatec - BMV Mar 5, 2015
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq.core;

import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;

/**
 * @author gus
 *
 */
public class MockBytesMessage implements BytesMessage {

	/***/
	private byte[] message;
	/* (non-Javadoc)
	 * @see javax.jms.Message#acknowledge()
	 */
	@Override
	public void acknowledge() throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#clearBody()
	 */
	@Override
	public void clearBody() throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#clearProperties()
	 */
	@Override
	public void clearProperties() throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getBooleanProperty(java.lang.String)
	 */
	@Override
	public final boolean getBooleanProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getByteProperty(java.lang.String)
	 */
	@Override
	public final byte getByteProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getDoubleProperty(java.lang.String)
	 */
	@Override
	public final double getDoubleProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getFloatProperty(java.lang.String)
	 */
	@Override
	public final float getFloatProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getIntProperty(java.lang.String)
	 */
	@Override
	public final int getIntProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSCorrelationID()
	 */
	@Override
	public final String getJMSCorrelationID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSCorrelationIDAsBytes()
	 */
	@Override
	public final byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSDeliveryMode()
	 */
	@Override
	public final int getJMSDeliveryMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSDestination()
	 */
	@Override
	public final Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSExpiration()
	 */
	@Override
	public final long getJMSExpiration() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSMessageID()
	 */
	@Override
	public final String getJMSMessageID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSPriority()
	 */
	@Override
	public final int getJMSPriority() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSRedelivered()
	 */
	@Override
	public final boolean getJMSRedelivered() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSReplyTo()
	 */
	@Override
	public final Destination getJMSReplyTo() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSTimestamp()
	 */
	@Override
	public final long getJMSTimestamp() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getJMSType()
	 */
	@Override
	public final String getJMSType() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getLongProperty(java.lang.String)
	 */
	@Override
	public final long getLongProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getObjectProperty(java.lang.String)
	 */
	@Override
	public final Object getObjectProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getPropertyNames()
	 */
	@Override
	public final Enumeration getPropertyNames() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getShortProperty(java.lang.String)
	 */
	@Override
	public final short getShortProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#getStringProperty(java.lang.String)
	 */
	@Override
	public final String getStringProperty(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#propertyExists(java.lang.String)
	 */
	@Override
	public final boolean propertyExists(final String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setBooleanProperty(java.lang.String, boolean)
	 */
	@Override
	public void setBooleanProperty(final String arg0, final boolean arg1)
			throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setByteProperty(java.lang.String, byte)
	 */
	@Override
	public void setByteProperty(final String arg0, final byte arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setDoubleProperty(java.lang.String, double)
	 */
	@Override
	public void setDoubleProperty(final String arg0, final double arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setFloatProperty(java.lang.String, float)
	 */
	@Override
	public void setFloatProperty(final String arg0, final float arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setIntProperty(java.lang.String, int)
	 */
	@Override
	public void setIntProperty(final String arg0, final int arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSCorrelationID(java.lang.String)
	 */
	@Override
	public void setJMSCorrelationID(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSCorrelationIDAsBytes(byte[])
	 */
	@Override
	public void setJMSCorrelationIDAsBytes(final byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSDeliveryMode(int)
	 */
	@Override
	public void setJMSDeliveryMode(final int arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSDestination(javax.jms.Destination)
	 */
	@Override
	public void setJMSDestination(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSExpiration(long)
	 */
	@Override
	public void setJMSExpiration(final long arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSMessageID(java.lang.String)
	 */
	@Override
	public void setJMSMessageID(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSPriority(int)
	 */
	@Override
	public void setJMSPriority(final int arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSRedelivered(boolean)
	 */
	@Override
	public void setJMSRedelivered(final boolean arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSReplyTo(javax.jms.Destination)
	 */
	@Override
	public void setJMSReplyTo(final Destination arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSTimestamp(long)
	 */
	@Override
	public void setJMSTimestamp(final long arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setJMSType(java.lang.String)
	 */
	@Override
	public void setJMSType(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setLongProperty(java.lang.String, long)
	 */
	@Override
	public void setLongProperty(final String arg0, final long arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setObjectProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setObjectProperty(final String arg0, final Object arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setShortProperty(java.lang.String, short)
	 */
	@Override
	public void setShortProperty(final String arg0, final short arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.Message#setStringProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void setStringProperty(final String arg0, final String arg1) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#getBodyLength()
	 */
	@Override
	public final long getBodyLength() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readBoolean()
	 */
	@Override
	public final boolean readBoolean() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readByte()
	 */
	@Override
	public final byte readByte() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readBytes(byte[])
	 */
	@Override
	public final int readBytes(final byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readBytes(byte[], int)
	 */
	@Override
	public final int readBytes(final byte[] arg0, final int arg1) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readChar()
	 */
	@Override
	public final char readChar() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readDouble()
	 */
	@Override
	public final double readDouble() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readFloat()
	 */
	@Override
	public final float readFloat() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readInt()
	 */
	@Override
	public final int readInt() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readLong()
	 */
	@Override
	public final long readLong() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readShort()
	 */
	@Override
	public final short readShort() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readUTF()
	 */
	@Override
	public final String readUTF() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readUnsignedByte()
	 */
	@Override
	public final int readUnsignedByte() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#readUnsignedShort()
	 */
	@Override
	public final int readUnsignedShort() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#reset()
	 */
	@Override
	public void reset() throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(final boolean arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeByte(byte)
	 */
	@Override
	public void writeByte(final byte arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeBytes(byte[])
	 */
	@Override
	public final void writeBytes(final byte[] arg0) throws JMSException {
		this.message = arg0;
	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeBytes(byte[], int, int)
	 */
	@Override
	public void writeBytes(final byte[] arg0, final int arg1, final int arg2) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeChar(char)
	 */
	@Override
	public void writeChar(final char arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeDouble(double)
	 */
	@Override
	public void writeDouble(final double arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeFloat(float)
	 */
	@Override
	public void writeFloat(final float arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeInt(int)
	 */
	@Override
	public void writeInt(final int arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeLong(long)
	 */
	@Override
	public void writeLong(final long arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeObject(java.lang.Object)
	 */
	@Override
	public void writeObject(final Object arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeShort(short)
	 */
	@Override
	public void writeShort(final short arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.jms.BytesMessage#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(final String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the message
	 */
	protected final byte[] getMessage() {
		return message;
	}

}
