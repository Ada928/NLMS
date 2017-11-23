package com.cibfintech.cloud.server;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.blacklist.internationblacklist.service.InternationalBlackListService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
import com.cibfintech.blacklist.service.BlackListSocketQueryLogService;
import com.cibfintech.cloud.domain.NewResponseBlackList;
import com.cibfintech.cloud.utils.XmlServerUtils;
import com.cibfintech.cloud.utils.seqnoutils.DefaultIdGenerator;
import com.cibfintech.cloud.utils.seqnoutils.IdGenerator;
import com.huateng.ebank.framework.exceptions.CommonException;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

public class NewServerMessageHandler extends IoHandlerAdapter{
	private Charset charset = Charset.forName("UTF-8");
	private final Logger LOG = Logger.getLogger(NewServerMessageHandler.class);
	public static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	public static ConcurrentHashMap<Long, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<Long, IoSession>();

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOG.error("服务器发生异常：" + cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("这是正常接受客户端信息：");
		IoBuffer ioBuffer = (IoBuffer)message;   
        byte[] b = new byte[ioBuffer.limit()];   
        ioBuffer.get(b);  
        String msg=new String(b,charset);
        //将测试消息会送给客户端
        //session.write(ioBuffer.flip());

		LOG.warn(" 服务器接收到客户端数据 messageReceived----------: " + msg);
		//获取客户端处理后的数据
		
		Map<String, String> clientMap = new HashMap<String, String>();
		
		//请求系统标识号:xtbs
		clientMap.put("xtbs", msg.substring(0, 4).trim());
		//证件号码:zjhm
		clientMap.put("zjhm", msg.substring(4, 68).trim());
		//客户名称：khmc
		clientMap.put("khmc", msg.substring(68, 168).trim());
		//账号
		clientMap.put("zhdh", msg.substring(168 , 232).trim());
		//卡号/折号
		clientMap.put("yhkh", msg.substring(232, 262).trim());

		//根据请求标识查询黑名单
		dealRequest(clientMap,session);
		
	}
	
	/**
	 * 根据请求标识查询黑名单
	 * @param clientMap
	 * @param session
	 */

	private void dealRequest(Map<String, String> clientMap, IoSession session) throws CommonException{
		//从商行黑名单查询==
		StringBuffer hql = new StringBuffer(" from NsBankBlackList po where 1=1");
		hql.append(" and po.del= 'F'");      
		//证件号码
		if (StringUtils.isNotBlank(clientMap.get("zjhm"))) {
			hql.append(" and po.certificateNumber = '").append(clientMap.get("zjhm")).append("'");
		}
		//客户名称
		if (StringUtils.isNotBlank(clientMap.get("khmc"))) {
			hql.append(" and po.clientName = '").append(clientMap.get("khmc")).append("'");
		}
		//账户代号（账号）
		if (StringUtils.isNotBlank(clientMap.get("zhdh"))) { 
			hql.append(" and po.accountCode = '").append(clientMap.get("zhdh")).append("'");
		}
		//卡号/折号
		if (StringUtils.isNotBlank(clientMap.get("yhkh"))) {
			hql.append(" and po.cardBkBookNo = '").append(clientMap.get("yhkh")).append("'");
		}
		List<NsBankBlackList> bankBlacklists = BankBlackListService.getInstance().getBlackListByHql(hql.toString());
		
		//从国际黑名单查询==
		StringBuffer hql2 = new StringBuffer(" from NsInternationalBlackList po where 1=1");
		hql2.append(" and po.del= 'F'");
		//证件号码
		if (StringUtils.isNotBlank(clientMap.get("zjhm"))) {
			hql2.append(" and po.certificateNumber = '").append(clientMap.get("zjhm")).append("'");
		}
		//客户名称
		if (StringUtils.isNotBlank(clientMap.get("khmc"))) {
			hql2.append(" and po.clientName = '").append(clientMap.get("khmc")).append("'");
		}
		List<NsInternationalBlackList> interBlacklists = InternationalBlackListService.getInstance().getBlackListByHql(hql2.toString());
		
		//公安部黑名单查询==
		StringBuffer hql3 = new StringBuffer(" from NsPoliceBlackList po where 1=1");
		hql3.append(" and po.del= 'F'");
		//证件号码
		if (StringUtils.isNotBlank(clientMap.get("zjhm"))) {
			hql3.append(" and po.certificateNumber = '").append(clientMap.get("zjhm")).append("'");
		}
		//客户名称
		if (StringUtils.isNotBlank(clientMap.get("khmc"))) {
			hql3.append(" and po.clientName = '").append(clientMap.get("khmc")).append("'");
		}
		//账户代号（账号）
		if (StringUtils.isNotBlank(clientMap.get("zhdh"))) {
			hql3.append(" and po.accountCode = '").append(clientMap.get("zhdh")).append("'");
		}
		//折号/卡号
		if (StringUtils.isNotBlank(clientMap.get("yhkh"))) {
			hql3.append(" and po.cardBkBookNo = '").append(clientMap.get("yhkh")).append("'");
		}
		List<NsPoliceBlackList> policeBlackLists = PoliceBlackListService.getInstance().getBlackListByHql(hql3.toString());
		
		//声明list用来存放查询到的黑名单信息
		List<NewResponseBlackList> bkList = new ArrayList<NewResponseBlackList>();
		//若商行黑名单查到：(1:是黑名单 0：不是黑名单    黑名单类型：0商行黑名单 1：国际 2：公安部)
		if (null != bankBlacklists && bankBlacklists.size() != 0) {
			for (NsBankBlackList blackList : bankBlacklists) {
				bkList.add(XmlServerUtils.packagBlackList(blackList,"1","B"));  //注：最后一个为备用字段默认（空）
			}
		}
		//国际黑名单查到
		if (null != interBlacklists && interBlacklists.size() != 0) {
			for (NsInternationalBlackList blackList : interBlacklists) {
				bkList.add(XmlServerUtils.packagBlackList(blackList,"1","I"));
			}
		}
		//公安部黑名单查到
		if (null != policeBlackLists && policeBlackLists.size() != 0) {
			for (NsPoliceBlackList blackList : policeBlackLists) {
				bkList.add(XmlServerUtils.packagBlackList(blackList, "1", "P"));
			}
		} 
		
		//对返回信息处理
		IoBuffer response;
		try {
			response = XmlServerUtils.responseBlackList(bkList);//将查询结果返回给客户端
			session.write(response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		saveLog(clientMap);
		
	}

	// 写入查询日志
	private void saveLog(Map<String, String> cliMap) throws CommonException {
		//工具类生成序号
		IdGenerator idGenerator = new DefaultIdGenerator();
		
		BlackListSocketQueryLogService service = BlackListSocketQueryLogService.getInstance();
		service.saveBlackListSocketQueryLogs(cliMap.get("xtbs"), idGenerator.next(), cliMap.get("zhdh"), cliMap.get("zjhm"),cliMap.get("khmc"), cliMap.get("yhkh"));
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
