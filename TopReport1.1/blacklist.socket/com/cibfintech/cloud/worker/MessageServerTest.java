package com.cibfintech.cloud.worker;

import com.cibfintech.cloud.server.ServerMsgProtocol;

public class MessageServerTest {
	public static void main(String[] args) {
		if (ServerMsgProtocol.serverStart()) {
			System.out.println("服务器启动成功......");
			ServerSendMsgThread ssmt = new ServerSendMsgThread();
			ssmt.start();
			System.out.println("工作线程启动成功......");
		}
	}
}