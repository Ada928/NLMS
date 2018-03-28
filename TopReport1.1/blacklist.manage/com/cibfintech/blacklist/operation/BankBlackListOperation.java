package com.cibfintech.blacklist.operation;

import java.io.IOException;
import java.util.List;

import resource.bean.blacklist.NsBankBLOperateLog;
import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsBankBlackListAuditState;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListAuditStateService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.blacklist.util.GenerateID;
import com.cibfintech.view.pub.BankBlackListAuditStateView;
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
	public static final String IN_BANK_BLACK_LISTS = "IN_BANK_BLACK_LISTS";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_PARAM_SAVE = "IN_PARAM_SAVE";
	public static final String IN_PARAM_SURE = "IN_PARAM_SURE";
	public static final String IN_DEL = "del";
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
		BankBlackListService service = BankBlackListService.getInstance();
		BankBlackListAuditStateService auditStateService = BankBlackListAuditStateService.getInstance();
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		String operateType = "";
		String message = "";

		if (CMD_DEL.equals(cmd)) {
			// 删除
			List<BankBlackListAuditStateView> fromBeans = (List<BankBlackListAuditStateView>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String del = (String) context.getAttribute(IN_DEL);
			for (BankBlackListAuditStateView auditStateView : fromBeans) {
				if (del.equals("deleteT")) {
					NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
					auditState.setId(String.valueOf(GenerateID.getId()));
					auditState.setBlacklistID(auditStateView.getBlacklistID());
					auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.DELETE.value);
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
					auditState.setBrcode(globalInfo.getBrcode());
					auditState.setEditUserID(globalInfo.getTlrno());
					auditState.setEditDate(DateUtil.getCurrentDateWithTime());
					auditStateService.modOrAddEntity(auditState);
				}else if(del.equals("delT")){//编辑岗位删除操作
					NsBankBlackListAuditState auditState = auditStateService.selectById(auditStateView.getId());
					//auditState.setId(String.valueOf(GenerateID.getId()));
					//auditState.setBlacklistID(auditStateView.getBlacklistID());
					auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.DELETE.value);
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.N.value);
					auditState.setBrcode(globalInfo.getBrcode());
					auditState.setEditUserID(globalInfo.getTlrno());
					auditState.setEditDate(DateUtil.getCurrentDateWithTime());
					auditStateService.modOrAddEntity(auditState);
				}

				NsBankBlackList bean = service.selectById(auditStateView.getBlacklistID());
				bean.setDel("T");   //删除
				bean.setApprove(SystemConstant.FALSE);
				bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDate());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_DELEATE;
				message = "商行黑名单的删除:" + bean.getId();
				recordRunningLog("Deleter.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			NsBankBlackList fromBean = (NsBankBlackList) context.getAttribute(IN_BANK_BLACK_LIST);
			String param = (String) context.getAttribute(IN_PARAM_SAVE);
			//String blacklistID = fromBean.getCertificateNumber();
			String blacklistID =String.valueOf(GenerateID.getId());
			
			String tlrno = globalInfo.getTlrno();
			String brcode = globalInfo.getBrcode();

			if (param.equals("queryVerify")) {
				NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
				auditState.setId(String.valueOf(GenerateID.getId()));
				auditState.setBlacklistID(blacklistID);
				auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.ADD.value);
				auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
				auditState.setBrcode(brcode);
				auditState.setEditUserID(tlrno);
				auditState.setEditDate(DateUtil.getCurrentDateWithTime());
				auditStateService.modOrAddEntity(auditState);
			}else if(param.equals("save")){
				NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
				auditState.setId(String.valueOf(GenerateID.getId()));
				auditState.setBlacklistID(blacklistID);
				auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.ADD.value);
				auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDING.value);
				auditState.setBrcode(brcode);
				auditState.setEditUserID(tlrno);
				auditState.setEditDate(DateUtil.getCurrentDateWithTime());
				auditStateService.modOrAddEntity(auditState);
			}

			fromBean.setId(blacklistID);
			fromBean.setBankCode(brcode);
			fromBean.setCreateDate(DateUtil.getCurrentDate());
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setApprove(SystemConstant.FALSE);
			fromBean.setShare(SystemConstant.FALSE);
			fromBean.setBlacklistedDate(DateUtil.getCurrentDate());
			fromBean.setBlacklistedOperator(tlrno);
			fromBean.setLastModifyOperator(tlrno);
			fromBean.setLastModifyDate(DateUtil.getCurrentDate());
			if (fromBean.getValid() == null || fromBean.getValid() == "") {
				fromBean.setValid(SystemConstant.FALSE);
			}
			if (fromBean.getValidDate() == null || fromBean.getValidDate().toString() == "") {
				fromBean.setValidDate(DateUtil.getDayAfter100Years());
			}

			service.addEntity(fromBean);

			operateType = SystemConstant.LOG_ADD;
			message = "商行黑名单的增加:" + fromBean.getId();
			recordRunningLog("Adder.log", message, fromBean, service);
			recordOperateLog(globalInfo, operateType, message);
		} else if (CMD_VERIFY.equals(cmd)) {
			// 审核

			List<BankBlackListAuditStateView> fromBeans = (List<BankBlackListAuditStateView>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String verify = (String) context.getAttribute(IN_VERIFY);
			for (BankBlackListAuditStateView auditStateView : fromBeans) {

				NsBankBlackListAuditState auditState = auditStateService.selectById(auditStateView.getId());
				if (verify.equals("verifyT")) {
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.VRED.value);
				} else if (verify.equals("verifyF")) {
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDING.value);
				}
				auditState.setVerifyUserID(globalInfo.getTlrno());
				auditState.setVerifyDate(DateUtil.getCurrentDateWithTime());
				auditStateService.modEntity(auditState);

				NsBankBlackList bean = service.selectById(auditStateView.getBlacklistID());
				bean.setLastModifyOperator(globalInfo.getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDateWithTime());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_VERIFY;
				message = "商行黑名单的审核:" + bean.getId();
				recordRunningLog("Verify.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}
		} else if (CMD_APPROVE.equals(cmd)) {
			// 审批
			List<BankBlackListAuditStateView> fromBeans = (List<BankBlackListAuditStateView>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String approve = (String) context.getAttribute(IN_APPROVE);

			for (BankBlackListAuditStateView auditStateView : fromBeans) {
				NsBankBlackListAuditState auditState = auditStateService.selectById(auditStateView.getId());
				if (approve.equals("approveT")) {
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.APED.value);
				} else if (approve.equals("approveF")) {
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
				}
				auditState.setApproveUserID(globalInfo.getTlrno());
				auditState.setApproveDate(DateUtil.getCurrentDateWithTime());
				auditStateService.modEntity(auditState);

				NsBankBlackList bean = service.selectById(auditStateView.getBlacklistID());
				if (auditState.getAuditType().equals(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.SHARE.value)) {
					bean.setShare(SystemConstant.TRUE);
				} else if (auditState.getAuditType().equals(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.CANCELSHARE.value)) {
					bean.setShare(SystemConstant.FALSE);
				} else if (auditState.getAuditType().equals(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.DELETE.value)) {
					bean.setDel(SystemConstant.TRUE);
				} else if (auditState.getAuditType().equals(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.VALID.value)) {
					bean.setValid(SystemConstant.TRUE);
				} else if (auditState.getAuditType().equals(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.INVALID.value)) {
					bean.setValid(SystemConstant.FALSE);
				}
				if (approve.equals("approveT")){
					bean.setApprove(SystemConstant.TRUE);
					
				}else if (approve.equals("approveF")){
					bean.setApprove(SystemConstant.FALSE);
				}
				
				bean.setLastModifyOperator(globalInfo.getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDateWithTime());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_APPROVE;
				message = "商行黑名单的审批:" + bean.getId();
				recordRunningLog("Approve.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}

		} else if (CMD_SHARE.equals(cmd)) {
			// 审批
			List<BankBlackListAuditStateView> fromBeans = (List<BankBlackListAuditStateView>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String share = (String) context.getAttribute(IN_SHARE);
			for (BankBlackListAuditStateView auditStateView : fromBeans) {
				if (share.equals("shareT")) {
					NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
					auditState.setId(String.valueOf(GenerateID.getId()));
					auditState.setId(auditStateView.getId());
					auditState.setBlacklistID(auditStateView.getBlacklistID());
					auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.SHARE.value);
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
					auditState.setBrcode(globalInfo.getBrcode());
					auditState.setEditUserID(globalInfo.getTlrno());
					auditState.setEditDate(DateUtil.getCurrentDateWithTime());
					auditStateService.modOrAddEntity(auditState);
				} else if (share.equals("shareF")) {
					NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
					// auditState.setId(String.valueOf(GenerateID.getId()));
					auditState.setId(auditStateView.getId());
					auditState.setBlacklistID(auditStateView.getBlacklistID());
					auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.CANCELSHARE.value);
					auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
					auditState.setBrcode(globalInfo.getBrcode());
					auditState.setEditUserID(globalInfo.getTlrno());
					auditState.setEditDate(DateUtil.getCurrentDateWithTime());
					auditStateService.modOrAddEntity(auditState);
				}

				NsBankBlackList bean = service.selectById(auditStateView.getBlacklistID());

				bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDate());
				bean.setApprove(SystemConstant.FALSE);
				service.modEntity(bean);

				operateType = SystemConstant.LOG_SHARE;
				message = "商行黑名单的共享:" + bean.getId();
				recordRunningLog("Share.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}

		} else if (CMD_EDIT.equals(cmd)) {
			NsBankBlackList fromBean = (NsBankBlackList) context.getAttribute(IN_BANK_BLACK_LIST);
			NsBankBlackList bean = service.selectById(fromBean.getId());
			String param = (String) context.getAttribute(IN_PARAM_SAVE);

			if (param.equals("queryVerify")) {
				NsBankBlackListAuditState auditState = new NsBankBlackListAuditState();
				// auditState.setId(String.valueOf(GenerateID.getId()));
				auditState.setId(fromBean.getAuditStateId());
				auditState.setBlacklistID(bean.getId());
				auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.EDIT.value);
				auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
				auditState.setBrcode(globalInfo.getBrcode());
				auditState.setEditUserID(globalInfo.getTlrno());
				auditState.setEditDate(DateUtil.getCurrentDateWithTime());
				auditStateService.modOrAddEntity(auditState);
			}

			bean.setAccountType(fromBean.getAccountType());
			bean.setAccountCode(fromBean.getAccountCode());
			bean.setCertificateType(fromBean.getCertificateType());
			bean.setCertificateNumber(fromBean.getCertificateNumber());
			bean.setClientName(fromBean.getClientName());
			bean.setClientEnglishName(fromBean.getClientEnglishName());
			bean.setBlacklistType(fromBean.getBlacklistType());
			bean.setValid(fromBean.getValid());
			bean.setValidDate(fromBean.getValidDate());
			bean.setBlacklistedReason(fromBean.getBlacklistedReason());

			if (bean.getValid() == SystemConstant.TRUE && fromBean.getValid() == SystemConstant.FALSE) {
				bean.setUnblacklistedDate(DateUtil.getCurrentDate());
				bean.setUnblacklistedOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setUnblacklistedReason(fromBean.getUnblacklistedReason());
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
			bean.setApprove(SystemConstant.FALSE);
			service.modEntity(bean);

			operateType = SystemConstant.LOG_EDIT;
			message = "商行黑名单的编辑:" + bean.getId();

			recordRunningLog("Updater.log", message, bean, service);
			recordOperateLog(globalInfo, operateType, message);
		}
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
					bean.getId(), bean.getApprove());
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
