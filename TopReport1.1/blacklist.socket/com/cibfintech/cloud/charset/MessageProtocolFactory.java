package com.cibfintech.cloud.charset;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/*
 * @see 自定义协议
 * @author yyxyz
 * @date 2017年9月29日 10:30:40
 */
public class MessageProtocolFactory implements ProtocolCodecFactory {
	private static final Charset charset = Charset.forName("UTF-8");

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return new MessageProtocolDecoder(charset);
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new MessageProtocolEncoder(charset);
	}
}