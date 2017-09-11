package com.cibfintech.blacklist.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resource.bean.blacklist.TlrOperateLog;
import resource.bean.pub.RoleInfo;
import resource.bean.pub.TlrBctlRel;
import resource.bean.pub.TlrInfo;
import resource.bean.pub.TlrRoleRel;
import resource.bean.report.SysTaskInfo;
import resource.dao.pub.TlrBctlRelDAO;
import resource.dao.pub.TlrRoleRelDAO;

import com.cibfintech.blacklist.userinfo.service.UserInfoService;
import com.cibfintech.blacklist.userinfo.service.UserOperateLogService;
import com.cibfintech.blacklist.util.GenerateID;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.DAOUtils;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;
import com.huateng.service.pub.PasswordService;
import com.huateng.service.pub.UserMgrService;

import edu.emory.mathcs.backport.java.util.Collections;

public class UserInfoOperation extends BaseOperation {
	public static final String ID = "UserInfoOperation";
	public static final String CMD = "CMD";
	public static final String IN_USER_INFO = "IN_USER_INFO";
	public static final String IN_ROLE_LIST = "IN_ROLELIST";
	public final static String IN_TLRNO = "IN_TLRNO";
	public final static String CMD_ADD = "add";
	public final static String CMD_DEL = "CMD_DEL";
	public final static String CMD_EDIT = "edit";
	public final static String CMD_RESETPWD = "CMD_RESETPWD";

	private static final HtLog htlog = HtLogFactory.getLogger(UserInfoOperation.class);
	private static String info = "";

	public static String getSuccessInfo() {
		return info;
	}

	public static void setSuccessInfo(String str) {
		info = str;
	}

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	private void updateTlrRoleRels(List<RoleInfo> roles, String tlrNo) throws CommonException {
		TlrRoleRelDAO dao = DAOUtils.getTlrRoleRelDAO();
		List<TlrRoleRel> list = dao.queryByCondition(" po.tlrno='" + tlrNo.trim() + "'");
		if (null == list) {
			saveTlrRoleRels(roles, tlrNo);
		} else {
			deleteTlrRoleRels(tlrNo);
			saveTlrRoleRels(roles, tlrNo);
		}
	}

	private void saveTlrRoleRels(List<RoleInfo> roles, String tlrNo) throws CommonException {
		TlrRoleRelDAO tlrRoleRelDAO = DAOUtils.getTlrRoleRelDAO();
		for (RoleInfo rl : roles) {
			TlrRoleRel tlrRoleRel = new TlrRoleRel();
			tlrRoleRel.setRoleId(rl.getId());
			tlrRoleRel.setTlrno(tlrNo);
			tlrRoleRel.setStatus(SystemConstant.VALID_FLAG_VALID);
			tlrRoleRelDAO.insert(tlrRoleRel);
		}
	}

	private void deleteTlrRoleRels(String tlrNo) throws CommonException {
		TlrRoleRelDAO tlrRoleRelDAO = DAOUtils.getTlrRoleRelDAO();
		List<TlrRoleRel> list = tlrRoleRelDAO.queryByCondition(" po.tlrno=" + tlrNo.trim());
		if (null != list) {
			for (TlrRoleRel rel : list) {
				tlrRoleRelDAO.delete(rel);
			}
		}
	}

	private void updateTlrBctlRels(String brcode, String tlrNo) throws CommonException {
		TlrBctlRelDAO dao = DAOUtils.getTlrBctlRelDAO();
		List<TlrBctlRel> list = dao.queryByCondition(" po.tlrno='" + tlrNo.trim() + "'");
		if (null == list) {
			saveTlrBctlRels(brcode, tlrNo);
		} else {
			deleteTlrBctlRels(brcode, tlrNo);
			saveTlrBctlRels(brcode, tlrNo);
		}
	}

	// 保存银行和用户的关系
	private void saveTlrBctlRels(String brcode, String tlrno) throws CommonException {
		TlrBctlRelDAO tlrBctlDAO = DAOUtils.getTlrBctlRelDAO();
		TlrBctlRel tlrBctlRel = new TlrBctlRel();

		tlrBctlRel.setBrcode(brcode);
		tlrBctlRel.setTlrno(tlrno);
		tlrBctlDAO.insert(tlrBctlRel);
	}

	// 删除银行和用户的关系
	private void deleteTlrBctlRels(String brcode, String tlrno) throws CommonException {
		TlrBctlRelDAO tlrBctlDAO = DAOUtils.getTlrBctlRelDAO();
		List<TlrBctlRel> list = tlrBctlDAO.queryByCondition(" po.tlrno = " + tlrno.trim() + " and po.brcode =" + brcode.trim());
		if (null != list) {
			for (TlrBctlRel obj : list) {
				tlrBctlDAO.delete(obj);
			}
		}
	}

	private void addTlrInfo(TlrInfo tlrInfo, GlobalInfo globalInfo, UserInfoService service) throws CommonException {
		String tlrno = "";
		String brcode = tlrInfo.getBrcode();
		String currentBrCode = globalInfo.getBrcode();
		List<TlrInfo> list = new ArrayList<TlrInfo>();
		if (null == brcode || "".equals(brcode)) {
			list = service.queryUserByHql(" from TlrInfo po where po.tlrno like '%" + currentBrCode.trim() + "%'");
			if (list.isEmpty()) {
				tlrno = currentBrCode + "001";
			} else {
				Collections.sort(list);
				tlrno = list.get(list.size() - 1).getTlrno();
				brcode = tlrno.substring(0, 5);
				int index = Integer.parseInt(tlrno.substring(5, tlrno.length()));
				index++;
				if (index < 10) {
					tlrno = brcode + "00" + String.valueOf(index);
				} else if (index < 100) {
					tlrno = brcode + "0" + String.valueOf(index);
				} else {
					tlrno = brcode + String.valueOf(index);
				}
			}
			brcode = currentBrCode;
		} else {
			tlrno = brcode + "001";
		}

		tlrInfo.setTlrno(tlrno);
		tlrInfo.setBrcode(brcode);

		tlrInfo.setStatus(SystemConstant.TLR_NO_STATE_LOGOUT);
		// 设置有效标志
		tlrInfo.setFlag(SystemConstant.FLAG_ON);

		// 设置默认操作员密码
		String sysDefaultPwd = CommonService.getInstance().getSysParamDef("PSWD", "DEFAULT_PWD", SystemConstant.DEFAULT_PASSWORD);
		String encMethod = CommonService.getInstance().getSysParamDef("PSWD", "ENC_MODE", "AES128");
		String password = PasswordService.getInstance().EncryptPassword(sysDefaultPwd, encMethod);
		tlrInfo.setPassword(password);

		// 为操作员密码错误次数付初始值
		tlrInfo.setTotpswderrcnt(new Integer(0));
		tlrInfo.setPswderrcnt(new Integer(0));
		tlrInfo.setPasswdenc(encMethod);
		tlrInfo.setCreateDate(DateUtil.getCurrentDate());
		tlrInfo.setLastUpdTime(DateUtil.getTimestamp());
		tlrInfo.setLastUpdOperId(globalInfo.getTlrno());
		tlrInfo.setEffectDate(DateUtil.getCurrentDateWithTime());
		tlrInfo.setExpireDate(DateUtil.getDayAfter100Years());
		tlrInfo.setLock(SystemConstant.FALSE);
		tlrInfo.setDel(SystemConstant.FALSE);
		tlrInfo.setSt(ReportEnum.REPORT_ST1.Y.value);

		service.addEntity(tlrInfo);

		setSuccessInfo("新建用户成功：用户 ID 为 " + tlrno + ", 默认密码为：" + sysDefaultPwd + ", 请及时登录修改密码。");
	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		TlrInfo userInfo = (TlrInfo) context.getAttribute(IN_USER_INFO);
		List<RoleInfo> roles = (List<RoleInfo>) context.getAttribute(IN_ROLE_LIST);

		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		// 调用服务类
		UserInfoService service = UserInfoService.getInstance();
		String operateType = "";
		String message = "";
		if (CMD_DEL.equals(cmd)) {
			TlrInfo bean = service.selectById(userInfo.getTlrno());
			bean.setSt(ReportEnum.REPORT_ST1.DE.value);
			bean.setStatus("0");
			bean.setLock(SystemConstant.TRUE);
			bean.setDel(SystemConstant.TRUE);
			bean.setLastUpdOperId(globalInfo.getTlrno());
			bean.setLastUpdTime(DateUtil.getCurrentDateWithTime());
			bean.setLastUpdTms(DateUtil.getCurrentDateWithTime());
			service.modEntity(bean);
			deleteTlrBctlRels(userInfo.getBrcode(), userInfo.getTlrno());
			deleteTlrRoleRels(userInfo.getTlrno());
			operateType = SystemConstant.LOG_DELEATE;
			message = "用户信息的删除";
			recordRunningLog("Delter.log", message, userInfo, service);
		} else if (CMD_ADD.equals(cmd)) {
			addTlrInfo(userInfo, globalInfo, service);
			saveTlrRoleRels(roles, userInfo.getTlrno());
			saveTlrBctlRels(userInfo.getBrcode(), userInfo.getTlrno());
			operateType = SystemConstant.LOG_ADD;
			message = "用户信息的增加";
			recordRunningLog("Adder.log", message, userInfo, service);
		} else if (CMD_EDIT.equals(cmd)) {
			TlrInfo bean = service.selectById(userInfo.getTlrno());

			bean.setBrcode(userInfo.getBrcode());
			bean.setTlrName(userInfo.getTlrName());
			bean.setLastUpdTime(DateUtil.getTimestamp());
			bean.setLastUpdOperId(globalInfo.getTlrno());
			bean.setLastUpdTime(DateUtil.getCurrentDateWithTime());
			bean.setLastUpdTms(DateUtil.getCurrentDateWithTime());
			bean.setSt(ReportEnum.REPORT__FH_ST.YES.value);

			service.modOrAddEntity(bean);
			// 更新用户和岗位的依赖
			updateTlrRoleRels(roles, userInfo.getTlrno());
			// 更新用户和银行的依赖
			updateTlrBctlRels(userInfo.getBrcode(), userInfo.getTlrno());

			operateType = SystemConstant.LOG_EDIT;
			message = "用户信息的编辑";
			recordRunningLog("Updater.log", message, userInfo, service);

		} else if (CMD_RESETPWD.equals(context.getAttribute(CMD))) {
			String tlrno = (String) context.getAttribute(IN_TLRNO);
			// 修改用户密码
			TlrInfo tlrInfo = service.selectById(tlrno);

			UserMgrService userMgrService = new UserMgrService();
			String sysDefaultPwd = CommonService.getInstance().getSysParamDef("PSWD", "DEFAULT_PWD", SystemConstant.DEFAULT_PASSWORD);

			userMgrService.updatePassword(tlrInfo.getTlrno(), sysDefaultPwd);
			message = "重置用户密码";
			operateType = SystemConstant.LOG_RESET;
			recordRunningLog("RestPwd.log", message, tlrInfo, service);
		}
		recordOperateLog(globalInfo, operateType, message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message, TlrInfo bean, UserInfoService service) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
		SysTaskInfo taskInfo;
		try {
			taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bean,
					bean.getTlrno(), bean.getSt());
			service.addTosystaskinfo(taskInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, String operateType, String message) {
		UserOperateLogService service = UserOperateLogService.getInstance();
		TlrOperateLog bean = new TlrOperateLog();
		bean.setBrcode(globalinfo.getBrcode());
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setQueryType("");
		bean.setTlrIP(globalinfo.getIp());
		bean.setTlrno(globalinfo.getTlrno());
		bean.setOperateType(operateType);
		bean.setMessage(message);
		bean.setCreateDate(DateUtil.getCurrentDateWithTime());
		try {
			service.addEntity(bean);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}
}
