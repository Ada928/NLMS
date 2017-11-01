package com.cibfintech.cloud.test;

import com.cibfintech.cloud.client.ClientMessageProtocol;
import com.cibfintech.cloud.worker.ClientSendMsgThread;

public class MessageClientTest {
	public static void main(String[] args) {
		ClientMessageProtocol.clientStart();
		System.out.println("客户端启动成功......");
		ClientSendMsgThread csmt = new ClientSendMsgThread();
		csmt.start();

		
		// MsgPack mp = new MsgPack(); // 请求协议
		// mp.setMsgMethod(2000);
		// mp.setMsgPack(XmlClientUtils.getXml());
		// mp.setMsgLength(mp.getMsgPack().getBytes().length);
		// ClientMsgProtocol.getIoSession().write(mp);

		System.out.println("客户端工作线程启动成功......");
	}
}