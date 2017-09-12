package com.cibfintech.blacklist.operation;

import java.io.IOException;

import resource.bean.blacklist.NsBankBLOperateLog;
import resource.bean.blacklist.NsPoliceBlackList;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
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

public class PoliceBlackListOperation extends BaseOperation {
	public static final String ID = "BankBlackListOperation";
	public static final String CMD = "CMD";
	public static final String IN_PARAM = "IN_PARAM";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_MOD = "CMD_MOD";
	public final static String CMD_DEL = "CMD_DEL";
	private static final HtLog htlog = HtLogFactory.getLogger(PoliceBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		NsPoliceBlackList fromBean = (NsPoliceBlackList) context.getAttribute(IN_PARAM);
		// 调用服务类
		PoliceBlackListService service = PoliceBlackListService.getInstance();
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();

		String operateType = "";
		String message = "";
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(fromBean);
			NsPoliceBlackList bean = service.selectById(fromBean.getId());
			bean.setOperateState(ReportEnum.REPORT_ST1.DE.value);
			bean.setDel(SystemConstant.TRUE);
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_DELEATE;
			message = "公安部黑名单的删除";
			recordRunningLog("Deleter.log", message, bean, service);
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			fromBean.setOperateState(ReportEnum.REPORT_ST1.CR.value);
			fromBean.setCreateDate(DateUtil.getCurrentDate());
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			fromBean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			fromBean.setLastModifyDate(DateUtil.getCurrentDate());

			service.addEntity(fromBean);

			operateType = SystemConstant.LOG_ADD;
			message = "公安部黑名单的增加";
			recordRunningLog("Adder.log", message, fromBean, service);
		} else if (CMD_MOD.equals(cmd)) {
			NsPoliceBlackList bean = service.selectById(fromBean.getId());
			if (fromBean.getBankCode().trim() == "") {
				bean.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			} else {
				bean.setBankCode(fromBean.getBankCode());
			}
			bean.setOperateState(ReportEnum.REPORT_ST1.ET.value);
			bean.setAccountType(fromBean.getAccountType());
			bean.setAccountCode(fromBean.getAccountCode());
			bean.setCertificateType(fromBean.getCertificateType());
			bean.setCertificateNumber(fromBean.getCertificateNumber());
			bean.setClientName(fromBean.getClientName());
			bean.setClientEnglishName(fromBean.getClientEnglishName());
			bean.setBlacklistType(fromBean.getBlacklistType());
			bean.setValid(fromBean.getValid());
			bean.setValidDate(fromBean.getValidDate());
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "公安部黑名单的编辑";
			recordRunningLog("Updater.log", message, bean, service);
		}

		recordOperateLog(globalInfo, operateType, message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message, NsPoliceBlackList bean, PoliceBlackListService service) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
		SysTaskInfo taskInfo;
		try {
			taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_200499.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bean,
					bean.getId(), bean.getOperateState());
			service.addTosystaskinfo(taskInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, String operateType, String message) {
		BankBlackListOperateLogService service = BankBlackListOperateLogService.getInstance();
		NsBankBLOperateLog bean = new NsBankBLOperateLog();
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
