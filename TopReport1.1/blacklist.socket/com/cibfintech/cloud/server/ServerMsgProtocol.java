package com.cibfintech.cloud.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/*
 * @see 服务器启动类,字符串消息测试类
 * @author yyxyz
 * @date 2017年9月26日 09:23:31
 * @file MinaServer.java
 * @package com.cibfintech.cloud.server
 * @project blacklist
 * @version 1.0
 * @since jdk1.6,mina 2.0
 */
public class ServerMsgProtocol {

	// 30秒后超时
	private static final int IDELTIMEOUT = 30;
	// 15秒发送一次心跳包
	private static final int HEARTBEATRATE = 15;

	private static SocketAcceptor acceptor;

	private ServerMsgProtocol() {
	}

	public static SocketAcceptor getAcceptor() {
		if (null == acceptor) {
			// 创建非阻塞的server端的Socket连接
			acceptor = new NioSocketAcceptor();
		}
		return acceptor;
	}

	public static boolean serverStart() {
		DefaultIoFilterChainBuilder filterChain = getAcceptor().getFilterChain();
		// 添加编码过滤器 处理乱码、编码问题
		// filterChain.addLast("codec", new ProtocolCodecFilter(new
		// MessageProtocolFactory()));
		filterChain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		LoggingFilter loggingFilter = new LoggingFilter();
		loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
		loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
		// 添加日志过滤器
		filterChain.addLast("loger", loggingFilter);
		// 设置核心消息业务处理器
		getAcceptor().setHandler(new ServerMessageHandler());
		// KeepAliveMessageFactory heartBeatFactory = new
		// KeepAliveMessageFactoryImpl();
		// KeepAliveRequestTimeoutHandler heartBeatHandler = new
		// KeepAliveRequestTimeoutHandlerImpl();
		// KeepAliveFilter heartBeat = new
		// KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE,
		// heartBeatHandler);
		// 是否回发
		// heartBeat.setForwardEvent(false);
		// 发送频率
		// heartBeat.setRequestInterval(HEARTBEATRATE);
		// getAcceptor().getFilterChain().addLast("heartbeat", heartBeat);
		getAcceptor().getSessionConfig().setReceiveBufferSize(2048 * 5000);// 接收缓冲区1M
		getAcceptor().getSessionConfig().setBothIdleTime(30);
		// getAcceptor().getSessionConfig().setKeepAlive(true);
		// 设置session配置，30秒内无操作进入空闲状态
		getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIMEOUT);
		try {
			// 绑定端口3456
			getAcceptor().bind(new InetSocketAddress(8888));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}