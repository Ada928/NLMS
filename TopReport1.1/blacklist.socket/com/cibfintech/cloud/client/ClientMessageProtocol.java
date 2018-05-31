package com.cibfintech.cloud.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cibfintech.cloud.charset.CmccSipcCodecFactory;
import com.cibfintech.cloud.charset.MessageProtocolFactory;
import com.cibfintech.cloud.utils.NewClientUtils;

/*
 * @see 模拟客户端；
 * 用于连接服务端，并向服务端发送消息
 * @author yyxyz
 * @date 2017年9月26日 11:27:50
 * @version 1.0
 * @serial jdk 1.6
 */
public class ClientMessageProtocol{

	private static NioSocketConnector connector;

	private static IoSession is;

	public static NioSocketConnector getConnector() {
		if (null == connector) {
			// 创建非阻塞的server端的Socket连接
			connector = new NioSocketConnector();
		}
		return connector;
	}

	public static IoSession getIoSession() {
		return is;
	}

	public static void clientStart() {
		// 创建客户端连接器
		NioSocketConnector connector = getConnector();
		 //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageProtocolFactory()));
		//connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		//connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CmccSipcCodecFactory(Charset.forName("UTF-8"))));
		LoggingFilter loggingFilter = new LoggingFilter();
		loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
		loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
		connector.getFilterChain().addLast("logger", loggingFilter);
		connector.getSessionConfig().setReceiveBufferSize(2048 * 5000);// 接收缓冲区1M
		connector.setConnectTimeoutMillis(30000); // 设置连接超时
		connector.setHandler(new TimeClientHandler());// 设置消息处理器
		ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 8889));// 建立连接
		cf.awaitUninterruptibly();// 等待连接创建完成
		try {
			is = cf.getSession();
			//is.write(new String("我是客户端".getBytes("GBK")));
			is.write(new String("abc".getBytes("UTF-8")));
			
			           
			         

			// getIoSession().write(new
			// String(XmlUtils.getXml().getBytes("UTF-8")));// 发送消息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}