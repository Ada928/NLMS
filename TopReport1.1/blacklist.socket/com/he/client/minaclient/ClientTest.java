package com.he.client.minaclient;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.he.bean.PhoneMessageDto;

/**
 * @function：基于mina框架的客户端，结构和mina服务端一直
 * @date：2016-9-27 下午03:45:30
 * @author:He.
 * @notice：
 */
public class ClientTest {
	private static Logger logger = Logger.getLogger(ClientTest.class);
	private static String HOST = "127.0.0.1";
	private static int PORT = 8888;
	private static int TIME_OUT = 30000;

	public static void main(String[] args) throws InterruptedException {

		// 创建客户端连接器. 创建一个非阻塞的客户端程序
		NioSocketConnector connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeout(TIME_OUT);
		// 设置过滤器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		// 设置编码过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		// 设置事件处理器, 添加业务逻辑处理器类
		connector.setHandler(new ClientHandler());
		IoSession session = null;
		try {
			// 创建连接
			ConnectFuture cf = connector.connect(new InetSocketAddress(HOST, PORT));// 建立连接
			cf.awaitUninterruptibly(); // 等待连接创建完成
			session = cf.getSession(); // 获得session

			PhoneMessageDto sendMes = new PhoneMessageDto();
			sendMes.setSendPhone("13681803609"); // 当前发送人的手机号码
			sendMes.setReceivePhone("13721427169"); // 接收人手机号码
			sendMes.setMessage("测试发送短信，这个是短信信息哦，当然长度是有限制的哦....");

			session.write(sendMes);// 发送消息
			// cf.getSession().closeOnFlush();
			// cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开
			// connector.dispose();
		} catch (Exception e) {
			logger.error("客户端链接异常...", e);
		}

	}
}