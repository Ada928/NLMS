package com.cibfintech.blacklist.operation;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import resource.bean.blacklist.RoleOperateLog;
import resource.bean.pub.RoleFuncRel;
import resource.bean.pub.RoleInfo;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.roleinfo.service.RoleFuncRelService;
import com.cibfintech.blacklist.roleinfo.service.RoleInfoService;
import com.cibfintech.blacklist.roleinfo.service.RoleOperateLogService;
import com.cibfintech.blacklist.util.GenerateID;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.DAOUtils;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

public class RoleInfoOperation extends BaseOperation {
	public static final String ID = "RoleInfoOperation";
	public static final String CMD = "CMD";
	public static final String IN_ROLE_INFO = "IN_ROLE_INFO";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_PARAM_SURE = "IN_PARAM_SURE";
	public static final String IN_ADD = "add";
	public static final String IN_EDIT = "edit";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_DEL = "CMD_DEL";
	public final static String CMD_CHGSTATUS = "CMD_CHGSTATUS";
	public final static String CMD_EDIT = "CMD_edit";
	private static final HtLog htlog = HtLogFactory.getLogger(RoleInfoOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		RoleInfo fromBean = (RoleInfo) context.getAttribute(IN_ROLE_INFO);
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		// 调用服务类
		RoleInfoService service = RoleInfoService.getInstance();
		String operateType = "";
		String message = "";
		if (CMD_DEL.equals(cmd)) {
			RoleInfo bean = service.selectById(fromBean.getId());
			bean.setSt(ReportEnum.REPORT_ST1.DE.value);
			bean.setStatus("0");
			bean.setLock(SystemConstant.TRUE);
			bean.setDel(SystemConstant.TRUE);
			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());
			service.modEntity(bean);
			operateType = SystemConstant.LOG_DELEATE;
			message = "岗位信息的删除";
			recordRunningLog("Deleter.log", message, fromBean, service);
		} else if (CMD_ADD.equals(cmd)) {
			Iterator it = DAOUtils.getHQLDAO().queryByQL("select max(id) from RoleInfo");
			int id = 100;
			if (it.hasNext()) {
				Number num = (Number) it.next();
				id = num.intValue() + 1;
			}
			fromBean.setId(id);
			// 插入或者更新
			fromBean.setSt(ReportEnum.REPORT_ST1.Y.value);
			fromBean.setStatus("1");
			fromBean.setRoleType(SystemConstant.ROLE_TYPE_QUERY);
			fromBean.setLock(SystemConstant.FALSE);
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setEffectDate(DateUtil.getCurrentDateWithTime());
			fromBean.setExpireDate(DateUtil.getDayAfter100Years());
			fromBean.setCrtDt(DateUtil.getCurrentDateWithTime());
			fromBean.setLastUpdTlr(globalInfo.getTlrno());
			fromBean.setLastUpdDate(DateUtil.getCurrentDateWithTime());

			String rolelist = fromBean.getRoleList();
			updateRoleFuncRel(id, rolelist);
			service.addEntity(fromBean);

			operateType = SystemConstant.LOG_ADD;
			message = "岗位信息的增加";
			recordRunningLog("Adder.log", message, fromBean, service);
		} else if (CMD_EDIT.equals(cmd)) {
			RoleInfo bean = service.selectById(fromBean.getId());

			String status2 = bean.getStatus();
			if (status2 == null || status2.equals("")) {
				// donothing
			} else {
				// 这儿说明是点击了有效无效按钮
				bean.setStatus(status2);
			}

			bean.setRoleName(fromBean.getRoleName());
			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());
			String rolelist = fromBean.getRoleList();
			updateRoleFuncRel(fromBean.getId(), rolelist);
			bean.setRoleList(fromBean.getRoleList());

			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "岗位信息的编辑";
			recordRunningLog("Updater.log", message, fromBean, service);
		} else if (CMD_CHGSTATUS.equals(cmd)) {
			RoleInfo bean = service.selectById(fromBean.getId());
			if (fromBean.getStatus().equals("1")) {
				bean.setStatus("0");
			} else {
				bean.setStatus("1");
			}
			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "岗位信息有效性设置";
			recordRunningLog("ChangeStatues.log", message, fromBean, service);
		}
		recordOperateLog(globalInfo, operateType, message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message, RoleInfo bean, RoleInfoService service) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
		SysTaskInfo taskInfo;
		try {
			taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bean,
					String.valueOf(bean.getId()), bean.getSt());
			service.addTosystaskinfo(taskInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, String operateType, String message) {
		RoleOperateLogService service = RoleOperateLogService.getInstance();
		RoleOperateLog bean = new RoleOperateLog();
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

	/**
	 * @desc 更新角色的权限,用,隔开的
	 * @author fubo
	 * @param rid
	 * @param funcs
	 * @return
	 * @throws CommonException
	 */
	public int updateRoleFuncRel(Integer rid, String funcs) throws CommonException {

		Hashtable<String, RoleFuncRel> oldfuncs = new Hashtable<String, RoleFuncRel>();
		Hashtable<String, String> newfuncs = new Hashtable<String, String>();

		RoleFuncRelService service = RoleFuncRelService.getInstance();
		List<RoleFuncRel> rfuncs = service.getRelByRoleId(rid);

		Iterator it = rfuncs.iterator();
		while (it.hasNext()) {
			RoleFuncRel rfr = (RoleFuncRel) it.next();
			oldfuncs.put(rfr.getFuncid().toString(), rfr);
		}

		StringTokenizer st = new StringTokenizer(funcs, ",");
		while (st.hasMoreTokens()) {
			String fid = st.nextToken();
			if (newfuncs.containsKey(fid) == false)
				newfuncs.put(fid, fid);
		}

		Iterator itnew = newfuncs.keySet().iterator();
		while (itnew.hasNext()) {
			String newfid = (String) itnew.next();
			if (oldfuncs.containsKey(newfid)) {
				oldfuncs.remove(newfid);
			} else {
				RoleFuncRel newrfr = new RoleFuncRel();
				newrfr.setId(String.valueOf(GenerateID.getId()));
				newrfr.setFuncid(newfid);
				newrfr.setRoleId(rid);
				service.addEntity(newrfr);
			}
		}
		Enumeration en = oldfuncs.keys();
		while (en.hasMoreElements()) {
			Object key_num = en.nextElement();
			RoleFuncRel oldrfr = (RoleFuncRel) oldfuncs.get(key_num);
			service.removeEntity(oldrfr);
		}

		return 0;
	}
}
