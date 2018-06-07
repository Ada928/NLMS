package com.cibfintech.cloud.charset;


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyTextFactory implements ProtocolCodecFactory
{

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception
	{
		return new MyTextDecoder();
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception
	{
		return new MyTextEncoder();
	}

}
