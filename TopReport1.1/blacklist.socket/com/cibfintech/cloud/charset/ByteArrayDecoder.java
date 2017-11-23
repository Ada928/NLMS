package com.cibfintech.cloud.charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


/**
 * @author BruceYang
 * 字节数组解码器
 */
public class ByteArrayDecoder extends CumulativeProtocolDecoder {

  public boolean doDecode(IoSession session, IoBuffer in,
      ProtocolDecoderOutput out) throws Exception {
	  
	  byte[] b = new byte[in.limit()];   
      in.get(b);  
      out.write(b);
      return true;
      
  }
}