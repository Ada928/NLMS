package com.cibfintech.blacklist.operation;

import java.io.IOException;

import resource.bean.blacklist.BctlOperateLog;
import resource.bean.pub.Bctl;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.bankinfo.service.BankInfoService;
import com.cibfintech.blacklist.bankinfo.service.BankOperateLogService;
import com.cibfintech.blacklist.util.GenerateID;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

public class BankInfoOperation extends BaseOperation {
	public static final String ID = "BankInfoOperation";
	public static final String CMD = "CMD";
	public static final String IN_BANK_INFO = "IN_BANK_INFO";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_PARAM_SURE = "IN_PARAM_SURE";
	public static final String IN_ADD = "add";
	public static final String IN_EDIT = "edit";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_DEL = "CMD_DEL";
	public final static String CMD_EDIT = "CMD_edit";
	public final static String CMD_CHGSTATUES = "CMD_CHGSTATUES";
	private static final HtLog htlog = HtLogFactory.getLogger(BankInfoOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		Bctl fromBean = (Bctl) context.getAttribute(IN_BANK_INFO);
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		// 调用服务类
		BankInfoService service = BankInfoService.getInstance();
		String operateType = "";
		String message = "";
		if (CMD_DEL.equals(cmd)) {
			Bctl bean = service.selectById(fromBean.getBrcode());
			bean.setSt(ReportEnum.REPORT_ST1.DE.value);
			bean.setStatus("0");
			bean.setLock(SystemConstant.TRUE);
			bean.setDel(SystemConstant.TRUE);
			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());
			service.modEntity(bean);
			operateType = SystemConstant.LOG_DELEATE;
			message = "银行信息的删除";
			recordRunningLog("Deleter.log", message, fromBean, service);
		} else if (CMD_ADD.equals(cmd)) {

			// 插入或者更新
			fromBean.setBrclass("1");
			fromBean.setSt(ReportEnum.REPORT_ST1.Y.value);
			fromBean.setStatus("1");
			fromBean.setLock(SystemConstant.FALSE);
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setTimestamps(DateUtil.getCurrentDateWithTime());
			fromBean.setEffectDate(DateUtil.getCurrentDateWithTime());
			fromBean.setExpireDate(DateUtil.getDayAfter100Years());
			fromBean.setLastUpdTlr(globalInfo.getTlrno());
			fromBean.setLastUpdDate(DateUtil.getCurrentDateWithTime());

			service.addEntity(fromBean);
			operateType = SystemConstant.LOG_ADD;
			message = "银行信息的增加";
			recordRunningLog("Adder.log", message, fromBean, service);
		} else if (CMD_EDIT.equals(cmd)) {
			Bctl bean = service.selectById(fromBean.getBrcode());

			bean.setBrcode(fromBean.getBrcode());
			bean.setBrno(fromBean.getBrno());
			bean.setBrname(fromBean.getBrname());
			bean.setTeleno(fromBean.getTeleno());
			bean.setAddress(fromBean.getAddress());
			bean.setPostno(fromBean.getPostno());

			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());

			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "银行信息的编辑";
			recordRunningLog("Updater.log", message, bean, service);
		} else if (CMD_CHGSTATUES.equals(cmd)) {
			Bctl bean = service.selectById(fromBean.getBrcode());
			if (fromBean.getStatus().equals("0")) {
				bean.setStatus("1");
			} else {
				bean.setStatus("0");
			}

			bean.setLastUpdTlr(globalInfo.getTlrno());
			bean.setLastUpdDate(DateUtil.getCurrentDateWithTime());
			service.modEntity(bean);
			operateType = SystemConstant.LOG_DELEATE;
			message = "银行信息有效性改变 " + bean.getStatus();
			recordRunningLog("ChangeStatues.log", message, bean, service);
		}
		recordOperateLog(globalInfo, operateType, message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message, Bctl bean, BankInfoService service) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
		SysTaskInfo taskInfo;
		try {
			taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bean,
					bean.getBrcode(), bean.getSt());
			service.addTosystaskinfo(taskInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, String operateType, String message) {
		BankOperateLogService service = BankOperateLogService.getInstance();
		BctlOperateLog bean = new BctlOperateLog();
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
