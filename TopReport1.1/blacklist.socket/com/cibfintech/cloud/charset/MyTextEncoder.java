package com.cibfintech.cloud.charset;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MyTextEncoder implements ProtocolEncoder
{

	public void dispose(IoSession arg0) throws Exception
	{
	}

	public void encode(IoSession arg0, Object message, ProtocolEncoderOutput out)
			throws Exception
	{
		String msg = message.toString();
		byte[] bu = msg.getBytes("UTF-8");
		IoBuffer buffer = IoBuffer.wrap(bu);
//		String to = new String(bu,"utf-8");
//		System.out.println("====================to============"+to);
		out.write(buffer);
	}

}
