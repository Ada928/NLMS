package com.cibfintech.cloud.charset;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cibfintech.cloud.domain.MessagePack;

public class MessageProtocolEncoder extends ProtocolEncoderAdapter {
	private Charset charset = null;

	public MessageProtocolEncoder(Charset charset) {
		this.charset = charset;
	}

	// 在此处实现对MsgProtocolEncoder包的编码工作，并把它写入输出流中
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if (message instanceof MessagePack) {
			MessagePack mp = (MessagePack) message;
			IoBuffer buf = IoBuffer.allocate(mp.getMsgLength());
			buf.order(ByteOrder.LITTLE_ENDIAN);
			buf.setAutoExpand(true);
			// 设置消息内容的长度
			buf.putInt(mp.getMsgLength());
			// 设置消息的功能函数
			buf.putInt(mp.getMsgMethod());
			if (null != mp.getMsgPack()) {
				buf.put(mp.getMsgPack().getBytes(charset));
			}
			buf.flip();
			out.write(buf);
			out.flush();
			buf.free();
		}
	}

	public void dispose() throws Exception {
	}

	public void encode0(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2) throws Exception {
		if (!(arg1 instanceof Serializable)) {
			throw new NotSerializableException();
		}
		IoBuffer buf = IoBuffer.allocate(64);
		buf.setAutoExpand(true);
		buf.putObject(arg1);

		int objectSize = buf.position() - 4;
		if (objectSize > 1024) {
			throw new IllegalArgumentException("The encoded object is too big: " + objectSize + " (> " + 1024 + ')');
		}

		buf.flip();
		arg2.write(buf);
	}

}