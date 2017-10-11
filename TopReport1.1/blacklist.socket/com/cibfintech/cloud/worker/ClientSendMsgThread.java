package com.cibfintech.cloud.worker;

import com.cibfintech.cloud.client.ClientMessageProtocol;
import com.cibfintech.cloud.domain.MessagePack;
import com.cibfintech.cloud.utils.XmlClientUtils;

/*
 * @see 模拟客户端发送数据
 * @author yyxyz
 * @date 2017年9月29日 10:38:59
 */
public class ClientSendMsgThread extends Thread {
	public void run() {
		while (true) {
			if (null != ClientMessageProtocol.getConnector()) {
				try {
					// ClientMsgProtocol.getIoSession().write(new
					// String("我是客户端".getBytes("UTF-8")));
					MessagePack mp = new MessagePack();
					// 请求协议
					mp.setMsgMethod(1000);
					mp.setMsgPack(XmlClientUtils.getXml());
					mp.setMsgLength(mp.getMsgPack().getBytes().length);
					ClientMessageProtocol.getIoSession().write(mp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("MinaServer.getAcceptor is null ");
			}
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}