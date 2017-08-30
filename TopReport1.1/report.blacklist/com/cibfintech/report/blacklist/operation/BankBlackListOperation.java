package com.cibfintech.report.blacklist.operation;

import java.io.IOException;

import com.cibfintech.report.blacklist.service.BankBlackListService;
import com.huateng.common.DateUtil;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

import resource.bean.report.BankBlackList;
import resource.bean.report.SysTaskInfo;

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
	private static final HtLog htlog = HtLogFactory
			.getLogger(BankBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		BankBlackList bankBlackList = (BankBlackList) context
				.getAttribute(IN_BANK_BLACK_LIST);
		// 调用服务类
		BankBlackListService service = BankBlackListService.getInstance();
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(bankBlackList);
			BankBlackList tempBankBlackList = service.selectById(bankBlackList.getId());
			// sysCurService.update(sysCurrency);
			tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.N.value);
			tempBankBlackList.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			tempBankBlackList.setValid(ReportEnum.REPORT_TRUE_FALSE.F.value);
			tempBankBlackList.setDel(SystemConstant.TRUE);
			tempBankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			tempBankBlackList.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(tempBankBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的删除" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的删除" });
		} else if (CMD_ADD.equals(cmd)) {

			// 插入或者更新
			// service.addEntity(bankBlackList);
			bankBlackList
					.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.ED.value);
			bankBlackList.setCreateDate(DateUtil.getCurrentDate());
			bankBlackList.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			bankBlackList.setDel(SystemConstant.FALSE);
			bankBlackList.setBankCode(GlobalInfo.getCurrentInstance()
					.getBrcode());
			bankBlackList.setBlacklistedDate(DateUtil.getCurrentDate());
			bankBlackList.setBlacklistedOperator(GlobalInfo
					.getCurrentInstance().getTlrno());
			bankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			bankBlackList.setLastModifyDate(DateUtil.getCurrentDate());

			service.addEntity(bankBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.NEW.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的增加" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的增加" });
		} else if (CMD_VERIFY.equals(cmd)) {
			// 审核
			// service.removeEntity(bankBlackList);
			BankBlackList tempBankBlackList = service.selectById(bankBlackList.getId());
			// sysCurService.update(sysCurrency);
			if(tempBankBlackList.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.VR.value)){
				tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value);
			//} else if(tempBankBlackList.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value)){
			//	tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.VR.value);
			}
			tempBankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			tempBankBlackList.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(tempBankBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的审核" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的审核" });
		} else if (CMD_APPROVE.equals(cmd)) {
			// 审批
			// service.removeEntity(bankBlackList);
			BankBlackList tempBankBlackList = service.selectById(bankBlackList.getId());
			if(tempBankBlackList.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value)){
				tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.PB.value);
			} else if(tempBankBlackList.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.PB.value)){
				tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.AP.value);
			}
			tempBankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			tempBankBlackList.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(tempBankBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的审批" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的审批" });
		} else if (CMD_SHARE.equals(cmd)) {
			// 分享
			// service.removeEntity(bankBlackList);
			BankBlackList tempBankBlackList = service.selectById(bankBlackList.getId());
			if(tempBankBlackList.isShare()){
				tempBankBlackList.setShare(ReportEnum.REPORT_TRUE_FALSE.F.value);
			} else {
				tempBankBlackList.setShare(ReportEnum.REPORT_TRUE_FALSE.T.value);
			}
			tempBankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			tempBankBlackList.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(tempBankBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的分享" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的分享" });
		} else if (CMD_EDIT.equals(cmd)) {
			// service.modEntity(bankBlackList);
			// Iterator it=service.selectByid(bankBlackList.getId());
			BankBlackList tempBankBlackList = service.selectById(bankBlackList.getId());
			if (bankBlackList.getBankCode().trim() == "") {
				tempBankBlackList.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			} else {
				tempBankBlackList.setBankCode(bankBlackList.getBankCode());
			}
			// tempBankBlackList.setOperateState(ReportEnum.REPORT_OPERATE_STATE.ET.value);
			tempBankBlackList.setAccountType(bankBlackList.getAccountType());
			tempBankBlackList.setAccountCode(bankBlackList.getAccountCode());
			tempBankBlackList.setCertificateType(bankBlackList.getCertificateType());
			tempBankBlackList.setCertificateNumber(bankBlackList.getCertificateNumber());
			tempBankBlackList.setClientName(bankBlackList.getClientName());
			tempBankBlackList.setClientEnglishName(bankBlackList.getClientEnglishName());
			tempBankBlackList.setBlacklistType(bankBlackList.getBlacklistType());
			tempBankBlackList.setValid(bankBlackList.isValid());
			tempBankBlackList.setValidDate(bankBlackList.getValidDate());
			tempBankBlackList.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.VR.value);

			if (tempBankBlackList.isValid() == SystemConstant.TRUE
					&& bankBlackList.isValid() == SystemConstant.FALSE) {
				tempBankBlackList.setUnblacklistedDate(DateUtil.getCurrentDate());
				tempBankBlackList.setUnblacklistedOperator(GlobalInfo.getCurrentInstance()
						.getTlrno());
				tempBankBlackList.setUnblacklistedReason(bankBlackList
						.getUnblacklistedReason());
			}
			tempBankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			tempBankBlackList.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(tempBankBlackList);

			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_200399.value,
						ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
						bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名单的编辑" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名单的编辑" });
		}
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}
}
