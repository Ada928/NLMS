package com.cibfintech.cloud.worker;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.cibfintech.cloud.domain.MessagePack;
import com.cibfintech.cloud.server.ServerMsgProtocol;
import com.cibfintech.cloud.utils.XmlServerUtils;

/* @see 服务器端发送数据
 * @author yyxyz
 * @date 2017年9月29日 10:38:59
 */
public class ServerSendMsgThread extends Thread {

	public void run() {
		while (true) {
			if (null != ServerMsgProtocol.getAcceptor()) {
				System.out.println("MinaServer.getAcceptor().getManagedSessionCount() is " + ServerMsgProtocol.getAcceptor().getManagedSessionCount());

				Map<Long, IoSession> map = ServerMsgProtocol.getAcceptor().getManagedSessions();
				for (Long key : map.keySet()) {
					IoSession is = map.get(key);
					// SocketAddress sa=is.getRemoteAddress();
					// InetSocketAddress isa=(InetSocketAddress)sa;
					// is.write("我是中文测试"+"session id is "+key+"  hostName:"+isa.getHostName()+"   address:"+isa.getAddress()+"   port:"+isa.getPort()+"        isa.toString:"+isa.toString());
					MessagePack mp = new MessagePack();
					// 请求协议
					mp.setMsgMethod(1000);
					// mp.setMsgPack("我是服务端");
					String str = "";

					//str += XmlServerUtils.tranToXML();
					str += "开发中.......";
					
					mp.setMsgPack(str);
					mp.setMsgLength(mp.getMsgPack().getBytes().length);
					try {
						is.write(mp);
					} catch (Exception e) {
						e.printStackTrace();
					}
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