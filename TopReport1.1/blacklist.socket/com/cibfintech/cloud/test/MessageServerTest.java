package com.cibfintech.cloud.test;

import com.cibfintech.cloud.server.ServerMsgProtocol;
import com.cibfintech.cloud.worker.ServerSendMsgThread;

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