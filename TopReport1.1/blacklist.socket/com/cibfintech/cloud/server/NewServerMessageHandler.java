package com.cibfintech.cloud.server;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.cibfintech.blacklist.util.GenerateID;
import com.cibfintech.cloud.utils.BlackListConstants;
import com.cibfintech.cloud.utils.XmlServerUtils;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.DateUtil;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.blacklist.NsPoliceBlackList;

public class NewServerMessageHandler extends IoHandlerAdapter {
	private Charset charset = Charset.forName("GBK");
	private final Logger LOG = Logger.getLogger(NewServerMessageHandler.class);
	public static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	public static ConcurrentHashMap<Long, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<Long, IoSession>();

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOG.error("服务器发生异常：" + cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.
	 * mina.core.session.IoSession, java.lang.Object)
	 */
	public void messageReceived(IoSession session, Object message) throws CommonException {
		System.out.println("这是正常接受客户端信息：");
		IoBuffer ioBuffer = (IoBuffer) message;
		byte[] b = new byte[ioBuffer.limit()];
		ioBuffer.get(b);

		String recive = new String(b, charset);
		LOG.warn(" 服务器接收到客户端原数据 messageReceived----------: " + "[" + recive + "]" + "长度：" + recive.getBytes().length);

		long t1 = System.currentTimeMillis();
		// 获取客户端处理后的数据
		// 判断报文长度是否符合要求
		String dataLenth = new String(b, 0, BlackListConstants.REQ_TRANCODE_LEN, charset);

		if (recive.getBytes().length == BlackListConstants.BLACKLIST_REQLENGTH
				&& dataLenth.equals(BlackListConstants.REQ_HEAD)) { // 截取后长度

			String msg = new String(b, BlackListConstants.DATALEN_LEN, BlackListConstants.SUB_LENGTH, charset);

			LOG.warn(" 服务器接收到客户端数据 messageReceived----------: " + msg.toString());
			LOG.warn(msg.length() + "去除数据长度后的报文内容长度----------------------");

			if (msg != null && !"".equals(msg)) { // 上送报文内容不为空
				Map<String, String> mc = new HashMap<String, String>();
				// 处理接受信息
				mc = XmlServerUtils.getRequst(b);

				String errcheck = XmlServerUtils.checkRequst(mc); // 检查上送字段

				if ("".equals(errcheck)) { // 无错误信息
					String ip = session.getRemoteAddress().toString().trim();
					// 根据请求标识查询黑名单
					dealRequest(mc, session, t1);
					// 记录日志表
					saveLog(mc, ip);

				} else { // 将错误信息返回
					String dateMsg = BlackListConstants.errMaping(errcheck);
					dealExpRequest(errcheck, dateMsg, session); // 上送信息有误
					LOG.warn(" 报文异常----------: " + dateMsg);
				}

			} else { // 报文内容为空 做异常返回
				LOG.warn("报文异常----------: " + BlackListConstants.ERR_MSG02);
				dealExpRequest(BlackListConstants.ERR_0002, BlackListConstants.ERR_MSG02, session);
			}

		} else { // 上送报文长度异常
			LOG.warn(" 服务器接收到客户端数据报文异常----------: " + BlackListConstants.ERR_MSG01);
			dealExpRequest(BlackListConstants.ERR_0001, BlackListConstants.ERR_MSG01, session);

		}

	}

	/**
	 * 报文长度异常处理
	 * 
	 * @param errMsg
	 */
	private void dealExpRequest(String errcode, String errMsg, IoSession session) throws CommonException {

		// 对返回信息处理
		IoBuffer response;
		try {
			response = XmlServerUtils.responseExpBlackList(errcode, errMsg);
			session.write(response);
		} catch (UnsupportedEncodingException e) {
			LOG.error(" 异常信息----------: " + e);
			e.printStackTrace();
		} // 将查询结果返回给客户端

		// saveLog(clientMap); 异常查询不计log表 只返回报文报错信息
	}

	/**
	 * 根据请求标识查询黑名单
	 * 
	 * @param clientMap
	 * @param session
	 */

	private void dealRequest(Map<String, String> clientMap, IoSession session, long t1) throws CommonException {

		try {
			// 从商行黑名单查询==
			StringBuffer hql = new StringBuffer(" from NsBankBlackList po where 1=1");
			hql.append(" and po.del= 'F' and po.approve='T' ");
			// 从国际黑名单查询==
			StringBuffer hql2 = new StringBuffer(" from NsInternationalBlackList po where 1=1");
			hql2.append(" and po.del= 'F' ");
			// 公安部黑名单查询==
			StringBuffer hql3 = new StringBuffer(" from NsPoliceBlackList po where 1=1");
			hql3.append(" and po.del= 'F' ");
			boolean markb = false;
			boolean flag = true;
			// 银行代码
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_BRCODE))) {
				markb = true;
				hql.append(" and po.bankCode = '").append(clientMap.get(BlackListConstants.REQ_BRCODE)).append("'");
			}
			// 证件类型
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_CERTYPE))) {
				markb = true;
				hql.append(" and po.certificateType = '").append(clientMap.get(BlackListConstants.REQ_CERTYPE))
						.append("'");
				hql2.append(" and po.certificateType = '").append(clientMap.get(BlackListConstants.REQ_CERTYPE))
						.append("'");
				hql3.append(" and po.certificateType = '").append(clientMap.get(BlackListConstants.REQ_CERTYPE))
						.append("'");
			}
			// 证件号码
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_CERNUMB))) {
				markb = true;
				hql.append(" and po.certificateNumber = '").append(clientMap.get(BlackListConstants.REQ_CERNUMB))
						.append("'");
				hql2.append(" and po.certificateNumber = '").append(clientMap.get(BlackListConstants.REQ_CERNUMB))
						.append("'");
				hql3.append(" and po.certificateNumber = '").append(clientMap.get(BlackListConstants.REQ_CERNUMB))
						.append("'");
			}
			// 客户名称
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_CLIENAME))) {
				markb = true;
				hql.append(" and po.clientName like '%").append(clientMap.get(BlackListConstants.REQ_CLIENAME))
						.append("%'");
				hql2.append(" and po.clientName like '%").append(clientMap.get(BlackListConstants.REQ_CLIENAME))
						.append("%'");
				hql3.append(" and po.clientName like '%").append(clientMap.get(BlackListConstants.REQ_CLIENAME))
						.append("%'");
			}
			// 英文名称
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_ENNAME))) {
				markb = true;
				hql.append(" and po.clientEnglishName like '%").append(clientMap.get(BlackListConstants.REQ_ENNAME))
						.append("%'");
				hql2.append(" and po.clientEnglishName like '%").append(clientMap.get(BlackListConstants.REQ_ENNAME))
						.append("%'");
				hql3.append(" and po.clientEnglishName like '%").append(clientMap.get(BlackListConstants.REQ_ENNAME))
						.append("%'");
			}
			// 账户（账号）
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_ACCNUM))) {
				markb = true;
				flag = false;
				hql.append(" and po.accountCode = '").append(clientMap.get(BlackListConstants.REQ_ACCNUM)).append("'");
				hql3.append(" and po.accountCode = '").append(clientMap.get(BlackListConstants.REQ_ACCNUM)).append("'");

			}
			// 卡号/折号
			if (StringUtils.isNotBlank(clientMap.get(BlackListConstants.REQ_CARDNO))) {
				markb = true;
				flag = false;
				hql.append(" and po.cardBkBookNo = '").append(clientMap.get(BlackListConstants.REQ_CARDNO)).append("'");
				hql3.append(" and po.cardBkBookNo = '").append(clientMap.get(BlackListConstants.REQ_CARDNO))
						.append("'");
			}
			List<NsBankBlackList> bankBlacklists = null;
			List<NsInternationalBlackList> interBlacklists = null;
			List<NsPoliceBlackList> policeBlackLists = null;
			if (markb) {
				if (flag) {
					bankBlacklists = BankBlackListService.getInstance().getBlackListByHql(hql.toString());
					interBlacklists = InternationalBlackListService.getInstance().getBlackListByHql(hql2.toString());
					policeBlackLists = PoliceBlackListService.getInstance().getBlackListByHql(hql3.toString());
				} else {
					bankBlacklists = BankBlackListService.getInstance().getBlackListByHql(hql.toString());
					policeBlackLists = PoliceBlackListService.getInstance().getBlackListByHql(hql3.toString());

				}
			}

			// 声明list用来存放查询到的黑名单信息
			List<Map<String, String>> mpList = new ArrayList<Map<String, String>>();
			// 若商行黑名单查到：(1:是黑名单 0：不是黑名单 黑名单类型：0商行黑名单 1：国际 2：公安部)
			if (null != bankBlacklists && bankBlacklists.size() != 0) {
				Map<String, String> mp = null;
				for (NsBankBlackList blackList : bankBlacklists) {
					mp = new HashMap<String, String>();
					mp.put(BlackListConstants.RES_CLITYPE, blackList.getAccountType()); // 客户类别
																						// 公
																						// C私I
					mp.put(BlackListConstants.RES_CLINAME, blackList.getClientName()); // 客户名称
					mp.put(BlackListConstants.RES_ENGNAME, blackList.getClientEnglishName()); // 客户英文名称
					mp.put(BlackListConstants.RES_CETYPE, blackList.getCertificateType()); // 证件类型
					mp.put(BlackListConstants.RES_CERNO, blackList.getCertificateNumber()); // 证件号码

					if (BlackListConstants.REQ_CARDNOFLAG.equals(clientMap.get(BlackListConstants.REQ_REQTYPE))) {
						mp.put(BlackListConstants.RES_ACCNO, blackList.getCardBkBookNo()); // 账号/卡号、折号
					} else {
						mp.put(BlackListConstants.RES_ACCNO, blackList.getAccountCode()); // 账号/卡号、折号
					}

					mp.put(BlackListConstants.RES_BLTYPE, blackList.getBlacklistType() + "0"); // 名单种类（00:白
																								// 10：黑名单
																								// 20：灰名单）
					mp.put(BlackListConstants.RES_AUTHORITY, BlackListConstants.RES_AUTHORITY_B); // 名单类型（区别数据库类型
																									// ,即是指B:商行、I:国际、P:公安部黑名单中的任意一个黑名单）
					if (null != blackList.getLastModifyDate()) {
						mp.put(BlackListConstants.RES_MODIFYDATE, DateUtil.dateToString(blackList.getLastModifyDate())); // 变更时间
					}
					mp.put(BlackListConstants.RES_DISC, blackList.getRemarks()); // 描述
					mpList.add(mp); // 注：最后一个为备用字段默认（空）
				}
			}
			// 国际黑名单查到
			if (null != interBlacklists && interBlacklists.size() != 0) {
				Map<String, String> mpi = null;
				for (NsInternationalBlackList blackList : interBlacklists) {
					mpi = new HashMap<String, String>();
					mpi.put(BlackListConstants.RES_CLITYPE, blackList.getAccountType()); // 客户类别
																							// 公
																							// C私I
					mpi.put(BlackListConstants.RES_CLINAME, blackList.getClientName()); // 客户名称
					mpi.put(BlackListConstants.RES_ENGNAME, blackList.getClientEnglishName()); // 客户英文名称
					mpi.put(BlackListConstants.RES_CETYPE, blackList.getCertificateType()); // 证件类型
					mpi.put(BlackListConstants.RES_CERNO, blackList.getCertificateNumber()); // 证件号码
					// mp.put(BlackListConstants.RES_ACCNO,
					// blackList.getAccountCode()); //客户类型
					mpi.put(BlackListConstants.RES_BLTYPE, blackList.getBlacklistType() + "0"); // 名单种类（00:白
																								// 10：黑名单
																								// 20：灰名单）
					mpi.put(BlackListConstants.RES_AUTHORITY, BlackListConstants.RES_AUTHORITY_I); // 名单类型（区别数据库类型
																									// ,即是指B:商行、I:国际、P:公安部黑名单中的任意一个黑名单）
					if (null != blackList.getLastModifyDate()) {
						mpi.put(BlackListConstants.RES_MODIFYDATE,
								DateUtil.dateToString(blackList.getLastModifyDate())); // 变更时间
					}
					mpi.put(BlackListConstants.RES_DISC, blackList.getRemarks()); // 描述
					mpList.add(mpi);
				}
			}
			// 公安部黑名单查到
			if (null != policeBlackLists && policeBlackLists.size() != 0) {
				Map<String, String> mpp = null;
				for (NsPoliceBlackList blackList : policeBlackLists) {
					mpp = new HashMap<String, String>();
					mpp.put(BlackListConstants.RES_CLITYPE, blackList.getAccountType()); // 客户类别
																							// 公
																							// C私I
					mpp.put(BlackListConstants.RES_CLINAME, blackList.getClientName()); // 客户名称
					mpp.put(BlackListConstants.RES_ENGNAME, blackList.getClientEnglishName()); // 客户英文名称
					mpp.put(BlackListConstants.RES_CETYPE, blackList.getCertificateType()); // 证件类型
					mpp.put(BlackListConstants.RES_CERNO, blackList.getCertificateNumber()); // 证件号码
					if (BlackListConstants.REQ_CARDNOFLAG.equals(clientMap.get(BlackListConstants.REQ_REQTYPE))) {
						mpp.put(BlackListConstants.RES_ACCNO, blackList.getCardBkBookNo()); // 账号/卡号、折号
					} else {
						mpp.put(BlackListConstants.RES_ACCNO, blackList.getAccountCode()); // 账号/卡号、折号
					}
					mpp.put(BlackListConstants.RES_BLTYPE, blackList.getBlacklistType() + "0"); // 名单种类（00:白
																								// 10：黑名单
																								// 20：灰名单）
					mpp.put(BlackListConstants.RES_AUTHORITY, BlackListConstants.RES_AUTHORITY_P); // 名单类型（区别数据库类型
																									// ,即是指B:商行、I:国际、P:公安部黑名单中的任意一个黑名单）
					if (null != blackList.getLastModifyDate()) {
						mpp.put(BlackListConstants.RES_MODIFYDATE,
								DateUtil.dateToString(blackList.getLastModifyDate())); // 变更时间
					}
					mpp.put(BlackListConstants.RES_DISC, blackList.getRemarks()); // 描述
					mpList.add(mpp);
				}
			}

			// 对返回信息处理
			try {
				// response =
				// XmlServerUtils.responseBlackList(mpList);//将查询结果返回给客户端
				byte[] bt = new byte[BlackListConstants.MAXL_ENGTH];
				bt = XmlServerUtils.responseBlackList(mpList);

				IoBuffer response = IoBuffer.allocate(bt.length);
				response.put(bt, 0, bt.length);
				response.flip();
				session.write(response);
				// clear方法会将position置0, limit置为capacity, 也就是remaining是capacity的值
				response.clear();
				long t2 = System.currentTimeMillis();
				System.out.println(t2 - t1 + "ms"
						+ "*****************************************************************************************");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error("返回结果处理异常----------: " + e);
			}

		} catch (Exception e) {
			// 数据操作异常
			e.printStackTrace();
			LOG.error(" 查询异常----------: " + BlackListConstants.ERR_MSG06 + e);
			dealExpRequest(BlackListConstants.ERR_0006, BlackListConstants.ERR_MSG06, session);
		}

	}

	// 写入查询日志
	private void saveLog(Map<String, String> cliMap, String ip) throws CommonException {
		// 工具类生成序号
		// IdGenerator idGenerator = new DefaultIdGenerator();
		// idGenerator.next()
		BlackListSocketQueryLogService service = BlackListSocketQueryLogService.getInstance();

		service.saveBlackListSocketQueryLogs(String.valueOf(GenerateID.getId()),
				cliMap.get(BlackListConstants.REQ_ACCNUM), cliMap.get(BlackListConstants.REQ_CERTYPE),
				cliMap.get(BlackListConstants.REQ_CERNUMB), cliMap.get(BlackListConstants.REQ_CLIENAME),
				cliMap.get(BlackListConstants.REQ_ENNAME), cliMap.get(BlackListConstants.REQ_BRCODE), ip);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		LOG.warn("messageSent: 服务端发送信息成功..." + message);
		/*
		 * System.out.println("服务器发送消息messageSent----------： "+ message);
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * String datetime = sdf.format(new Date());
		 * System.out.println(datetime+ "服务器发送消息messageSent----------： "
		 * +message.toString());
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
		LOG.warn("sessionOpened, 打开一个session id：" + session.getId() + "  空闲连接个数IdleCount：  "
				+ session.getBothIdleCount());
	}
}
