package com.cibfintech.cloud.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.blacklist.internationblacklist.service.InternationalBlackListService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
import com.cibfintech.blacklist.service.BlackListSocketQueryLogService;
import com.cibfintech.cloud.domain.MessagePack;
import com.cibfintech.cloud.domain.ReponseBlacklist;
import com.cibfintech.cloud.domain.Request;
import com.cibfintech.cloud.domain.RequestBody;
import com.cibfintech.cloud.domain.RequestHeader;
import com.cibfintech.cloud.utils.JaxbUtil;
import com.cibfintech.cloud.utils.XmlServerUtils;
import com.huateng.ebank.framework.exceptions.CommonException;

/*
 * @see 处理服务器端消息
 * @author yyxyz
 * @date 2017-9-26 下午01:12:34
 * @file ServerMessageHandler.java
 * @package com.cibfintech.cloud.server
 * @project blacklist
 * @version 1.0
 * @since jdk1.6,mina 2.0
 */
public class ServerMessageHandler extends IoHandlerAdapter {

	private final Logger LOG = Logger.getLogger(ServerMessageHandler.class);

	public static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	public static ConcurrentHashMap<Long, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<Long, IoSession>();

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOG.error("服务器发生异常：" + cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		MessagePack mp = (MessagePack) message;
		LOG.warn(" 服务器接收到客户端数据-- messageReceived----------: " + mp.toString());

		Request request = JaxbUtil.converyToJavaBean(mp.getMsgPack(), Request.class);
		dealRequest(request, session);

	}

	private void dealRequest(Request request, IoSession session) throws CommonException {
		MessagePack mp = new MessagePack();
		List<ReponseBlacklist> list = new ArrayList<ReponseBlacklist>();
		RequestHeader header = request.getMsgHeader();
		RequestBody body = request.getReqBody();

		StringBuffer hql = new StringBuffer(" from NsBankBlackList po where 1=1");
		hql.append(" and po.del= 'F'");

		if (StringUtils.isNotBlank(body.getAccountCode().toString())) {
			hql.append(" and po.accountCode = '").append(body.getAccountCode()).append("'");
		}
		if (StringUtils.isNotBlank(body.getCertificateType())) {
			hql.append(" and po.certificateType = '").append(body.getCertificateType()).append("'");
		}
		if (StringUtils.isNotBlank(body.getCertificateNumber())) {
			hql.append(" and po.certificateNumber = '").append(body.getCertificateNumber()).append("'");
		}

		List<NsBankBlackList> bankBlacklists = BankBlackListService.getInstance().getBlackListByHql(hql.toString());

		StringBuffer hql2 = new StringBuffer(" from NsInternationalBlackList po where 1=1");
		hql2.append(" and po.del= 'F'");

		if (StringUtils.isNotBlank(body.getCertificateType())) {
			hql2.append(" and po.certificateType = '").append(body.getCertificateType()).append("'");
		}
		if (StringUtils.isNotBlank(body.getCertificateNumber())) {
			hql2.append(" and po.certificateNumber = '").append(body.getCertificateNumber()).append("'");
		}

		List<NsInternationalBlackList> interBlacklists = InternationalBlackListService.getInstance().getBlackListByHql(hql2.toString());

		StringBuffer hql3 = new StringBuffer(" from NsPoliceBlackList po where 1=1");
		hql3.append(" and po.del= 'F'");

		if (StringUtils.isNotBlank(body.getCertificateType())) {
			hql3.append(" and po.certificateType = '").append(body.getCertificateType()).append("'");
		}
		if (StringUtils.isNotBlank(body.getCertificateNumber())) {
			hql3.append(" and po.certificateNumber = '").append(body.getCertificateNumber()).append("'");
		}

		List<NsPoliceBlackList> policeBlackLists = PoliceBlackListService.getInstance().getBlackListByHql(hql3.toString());

		if (null != bankBlacklists && bankBlacklists.size() != 0) {
			for (NsBankBlackList blackList : bankBlacklists) {
				list.add(XmlServerUtils.groupBlacklist(blackList, "00000", "from bank, success", body.getSeqNo()));
			}
		} else {
			list.add(XmlServerUtils.groupBlacklist(new NsBankBlackList(), "11111", "from bank, failue", body.getSeqNo()));
		}
		if (null != interBlacklists && interBlacklists.size() != 0) {
			for (NsInternationalBlackList blackList : interBlacklists) {
				list.add(XmlServerUtils.groupBlacklist(blackList, "00000", "from internation, success", body.getSeqNo()));
			}
		} else {
			list.add(XmlServerUtils.groupBlacklist(new NsInternationalBlackList(), "11111", "from internation, failue", body.getSeqNo()));
		}
		if (null != policeBlackLists && policeBlackLists.size() != 0) {
			for (NsPoliceBlackList blackList : policeBlackLists) {
				list.add(XmlServerUtils.groupBlacklist(blackList, "00000", "from police, success", body.getSeqNo()));
			}
		} else {
			list.add(XmlServerUtils.groupBlacklist(new NsPoliceBlackList(), "11111", "from police, failue", body.getSeqNo()));
		}

		// 请求协议
		mp.setMsgMethod(Integer.parseInt(header.getTranCode()));
		mp.setMsgPack(XmlServerUtils.tranToXML(header, list));
		mp.setMsgLength(mp.getMsgPack().getBytes().length);
		session.write(mp);

		saveLog(request);
	}

	// 写入查询日志
	private void saveLog(Request request) throws CommonException {
		RequestHeader header = request.getMsgHeader();
		RequestBody body = request.getReqBody();
		BlackListSocketQueryLogService service = BlackListSocketQueryLogService.getInstance();
		service.saveBlackListSocketQueryLog(header.getSysNo(), header.getTranCode(), body.getSeqNo(), body.getAccountCode(), body.getCertificateType(),
				body.getCertificateNumber());
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		LOG.warn("messageSent: 服务端发送信息成功..." + message);
		/*
		 * System.out.println("服务器发送消息messageSent----------： "+ message);
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * String datetime = sdf.format(new Date());
		 * System.out.println(datetime+
		 * "服务器发送消息messageSent----------： "+message.toString());
		 */
	}

	public void sessionClosed(IoSession session) throws Exception {
		LOG.warn("sessionClosed. 服务端关闭session" + session.getId() + session.getRemoteAddress());
		session.closeOnFlush();
		sessions.remove(session);
		sessionsConcurrentHashMap.remove(session.getAttribute("id"));
	}

	public void sessionCreated(IoSession session) throws Exception {
		LOG.warn("创建一个新连接: [" + session.getRemoteAddress().toString() + "] connected. " + "  id:  " + session.getId());

		sessions.add(session);
		Long time = System.currentTimeMillis();
		session.setAttribute("id", time);
		sessionsConcurrentHashMap.put(time, session);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		LOG.warn("session idle, 服务端进入空闲状态: " + session.getRemoteAddress() + status);
	}

	public void sessionOpened(IoSession session) throws Exception {
		LOG.warn("sessionOpened, 打开一个session id：" + session.getId() + "  空闲连接个数IdleCount：  " + session.getBothIdleCount());
	}
}