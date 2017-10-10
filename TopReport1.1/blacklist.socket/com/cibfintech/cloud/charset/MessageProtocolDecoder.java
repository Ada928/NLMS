package com.cibfintech.cloud.charset;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.cibfintech.cloud.domain.MessagePack;

/*
 * @see 协议解码
 * @author Herman.Xiong
 * @date 2014年6月11日 16:47:24
 */
public class MessageProtocolDecoder extends CumulativeProtocolDecoder {
	private Charset charset = null;

	public MessageProtocolDecoder() {
		this(Charset.defaultCharset());
	}

	public MessageProtocolDecoder(Charset charset) {
		this.charset = charset;
	}

	public void decode1(IoSession is, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		buf.order(ByteOrder.LITTLE_ENDIAN);
		MessagePack mp = new MessagePack();
		// 获取消息的内容长度
		mp.setMsgLength(buf.getInt());
		// 获取消息的功能函数
		mp.setMsgMethod(buf.getInt());
		byte[] msg = new byte[mp.getMsgLength()];
		buf.get(msg);
		mp.setMsgPack(new String(msg, charset));
		buf.flip();
		out.write(mp);
	}

	public void dispose(IoSession arg0) throws Exception {

	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}

	public void decode0(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {
		int limit = arg1.limit();
		byte[] bytes = new byte[limit];
		arg1.get(bytes);
		arg2.write(bytes);
	}

	protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput out) throws Exception {
		ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
		MessagePack mp = (MessagePack) session.getAttribute("nac-msg-pack"); // 从session对象中获取“xhs-upload”属性值
		if (null == mp) {
			if (ioBuffer.remaining() >= 8) {
				// 取消息体长度
				int msgLength = ioBuffer.getInt();
				int msgMethod = ioBuffer.getInt();
				mp = new MessagePack();
				mp.setMsgLength(msgLength);
				mp.setMsgMethod(msgMethod);
				session.setAttribute("nac-msg-pack", mp);
				return true;
			}
			return false;
		}
		if (ioBuffer.remaining() >= mp.getMsgLength()) {
			byte[] msgPack = new byte[mp.getMsgLength()];
			ioBuffer.get(msgPack);
			mp.setMsgPack(new String(msgPack, charset));
			session.removeAttribute("nac-msg-pack");
			out.write(mp);
			return true;
		}
		return false;
	}

}