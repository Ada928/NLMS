package com.cibfintech.blacklist.operation;

import java.io.IOException;

import resource.bean.blacklist.NsBankBLOperateLog;
import resource.bean.blacklist.NsBankBlackList;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
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

public class BankBlackListOperation extends BaseOperation {
	public static final String ID = "BankBlackListOperation";
	public static final String CMD = "CMD";
	public static final String IN_BANK_BLACK_LIST = "IN_BANK_BLACK_LIST";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_PARAM_SURE = "IN_PARAM_SURE";
	public static final String IN_ADD = "add";
	public static final String IN_EDIT = "edit";
	public static final String IN_APPROVE = "approve";
	public static final String IN_VERIFY = "verify";
	public static final String IN_SHARE = "share";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_DEL = "CMD_DEL";
	public final static String CMD_EDIT = "CMD_edit";
	public final static String CMD_VERIFY = "CMD_verify";
	public final static String CMD_APPROVE = "CMD_approve";
	public final static String CMD_SHARE = "CMD_share";
	private static final HtLog htlog = HtLogFactory.getLogger(BankBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		NsBankBlackList fromBean = (NsBankBlackList) context.getAttribute(IN_BANK_BLACK_LIST);
		BankBlackListService service = BankBlackListService.getInstance();
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		String operateType = "";
		String message = "";

		if (CMD_DEL.equals(cmd)) {
			// 删除
			NsBankBlackList bean = service.selectById(fromBean.getId());
			bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.N.value);
			bean.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			bean.setValid(ReportEnum.REPORT_TRUE_FALSE.F.value);
			bean.setDel(SystemConstant.TRUE);
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_DELEATE;
			message = "商行黑名单的删除";
			recordRunningLog("Deleter.log", message, bean, service);
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			fromBean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.ED.value);
			fromBean.setCreateDate(DateUtil.getCurrentDate());
			fromBean.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			fromBean.setBlacklistedDate(DateUtil.getCurrentDate());
			fromBean.setBlacklistedOperator(GlobalInfo.getCurrentInstance().getTlrno());
			fromBean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			fromBean.setLastModifyDate(DateUtil.getCurrentDate());
			if (fromBean.getValidDate() == null || fromBean.getValidDate().toString() == "") {
				fromBean.setValidDate(DateUtil.getDayAfter100Years());
			}

			service.addEntity(fromBean);

			operateType = SystemConstant.LOG_ADD;
			message = "商行黑名单的增加";
			recordRunningLog("Adder.log", message, fromBean, service);
		} else if (CMD_VERIFY.equals(cmd)) {
			// 审核
			NsBankBlackList bean = service.selectById(fromBean.getId());
			if (bean.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.VR.value)) {
				bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value);
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "商行黑名单的审核";
			recordRunningLog("Updater.log", message, bean, service);
		} else if (CMD_APPROVE.equals(cmd)) {
			// 审批
			NsBankBlackList bean = service.selectById(fromBean.getId());
			if (bean.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value)) {
				bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.PB.value);
			} else if (bean.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.PB.value)) {
				bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value);
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "商行黑名单的审批";
			recordRunningLog("Updater.log", message, bean, service);
		} else if (CMD_SHARE.equals(cmd)) {
			// 分享
			NsBankBlackList bean = service.selectById(fromBean.getId());
			if (bean.isShare()) {
				bean.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			} else {
				bean.setShare(ReportEnum.REPORT_TRUE_FALSE.T.value);
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "商行黑名单的分享";
			recordRunningLog("Updater.log", message, bean, service);
		} else if (CMD_EDIT.equals(cmd)) {
			NsBankBlackList bean = service.selectById(fromBean.getId());
			if (fromBean.getBankCode().trim() == "") {
				bean.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			} else {
				bean.setBankCode(fromBean.getBankCode());
			}
			bean.setAccountType(fromBean.getAccountType());
			bean.setAccountCode(fromBean.getAccountCode());
			bean.setCertificateType(fromBean.getCertificateType());
			bean.setCertificateNumber(fromBean.getCertificateNumber());
			bean.setClientName(fromBean.getClientName());
			bean.setClientEnglishName(fromBean.getClientEnglishName());
			bean.setBlacklistType(fromBean.getBlacklistType());
			bean.setValid(fromBean.isValid());
			bean.setValidDate(fromBean.getValidDate());
			bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.VR.value);

			if (bean.isValid() == SystemConstant.TRUE && fromBean.isValid() == SystemConstant.FALSE) {
				bean.setUnblacklistedDate(DateUtil.getCurrentDate());
				bean.setUnblacklistedOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setUnblacklistedReason(fromBean.getUnblacklistedReason());
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "商行黑名单的编辑";

			recordRunningLog("Updater.log", message, bean, service);
		}
		recordOperateLog(globalInfo, operateType, message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message, NsBankBlackList bean, BankBlackListService service) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
		SysTaskInfo taskInfo;
		try {
			taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bean,
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
		bean.setBrNo(globalinfo.getBrno());
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setQueryType("");
		bean.setTlrIP(globalinfo.getIp());
		bean.setTlrNo(globalinfo.getTlrno());
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
