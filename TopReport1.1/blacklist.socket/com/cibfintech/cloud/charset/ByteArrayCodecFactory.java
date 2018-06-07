package com.cibfintech.cloud.charset;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @author administrator
 * 字节数组编解码工厂
 */
public class ByteArrayCodecFactory implements ProtocolCodecFactory{
	private ByteArrayDecoder decoder;
    private ByteArrayEncoder encoder;
    
    
    public ByteArrayCodecFactory() {
    	this(Charset.defaultCharset());
	}

	public ByteArrayCodecFactory(Charset charSet) {
    	encoder = new ByteArrayEncoder(charSet);
        decoder = new ByteArrayDecoder(charSet);
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
}
