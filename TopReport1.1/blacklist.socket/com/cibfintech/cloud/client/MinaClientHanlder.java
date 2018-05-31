package com.cibfintech.cloud.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.cibfintech.cloud.charset.FileUploadRequest;

public class MinaClientHanlder extends IoHandlerAdapter{
	private final Logger LOG = Logger.getLogger(MinaClientHanlder.class);

	/*
	 * 接收客户端发送的消息
	 */
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("message :"+message);
		
		LOG.warn("客户端收到消息：" + message);
		if (message.toString().equals("1111")) {
			// 收到心跳包
			LOG.warn("收到心跳包");
			session.write("1112");
		}
		LOG.warn(session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOG.error("客户端发生异常...", cause);
	}

}
