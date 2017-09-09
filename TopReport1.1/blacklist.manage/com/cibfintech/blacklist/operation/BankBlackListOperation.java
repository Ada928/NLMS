package com.cibfintech.blacklist.operation;

import java.io.IOException;
import java.util.List;

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
	public static final String IN_BANK_BLACK_LISTS = "IN_BANK_BLACK_LISTS";
	public static final String IN_PARAM = "IN_PARAM";
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
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		String operateType = "";
		String message = "";

		if (CMD_DEL.equals(cmd)) {
			// 删除
			List<NsBankBlackList> fromBean = (List<NsBankBlackList>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String del = (String) context.getAttribute(IN_DEL);
			for (NsBankBlackList bblt : fromBean) {
				NsBankBlackList bean = service.selectById(bblt.getId());

				if (del.equals("delT")) {
					if (bean.getDelState().equals(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEING.value)) {
						bean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEED.value);
					}
				}
				//
				// bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.N.value);
				// bean.setShare(SystemConstant.FALSE);
				// bean.setValid(SystemConstant.FALSE);
				// bean.setDel(SystemConstant.TRUE);
				//
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
			fromBean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDED.value);
			fromBean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHING.value);
			fromBean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEING.value);
			fromBean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAING.value);
			fromBean.setCreateDate(DateUtil.getCurrentDate());
			fromBean.setShare(SystemConstant.FALSE);
			fromBean.setDel(SystemConstant.FALSE);
			fromBean.setValid(SystemConstant.FALSE);
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
			message = "商行黑名单的增加:" + fromBean.getId();
			recordRunningLog("Adder.log", message, fromBean, service);
			recordOperateLog(globalInfo, operateType, message);
		} else if (CMD_VERIFY.equals(cmd)) {
			// 审核

			List<NsBankBlackList> fromBeans = (List<NsBankBlackList>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String verify = (String) context.getAttribute(IN_VERIFY);
			for (NsBankBlackList bblt : fromBeans) {
				NsBankBlackList bean = service.selectById(bblt.getId());
				if (bean.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDED.value)) {
					if (verify.equals("verifyT")) {
						bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDVRED.value);
					} else if (verify.equals("verifyF")) {
						bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDING.value);
					}
				}
				if (bean.getShareState().equals(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHED.value)) {
					if (verify.equals("verifyT")) {
						bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHVRED.value);
					} else if (verify.equals("verifyF")) {
						bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHING.value);
					}
				}
				if (bean.getValidDate().equals(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAED.value)) {
					if (verify.equals("verifyT")) {
						bean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAVRED.value);
					} else if (verify.equals("verifyF")) {
						bean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAING.value);
					}
				}
				if (bean.getDelState().equals(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEED.value)) {
					if (verify.equals("verifyT")) {
						bean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEVRED.value);
					} else if (verify.equals("verifyF")) {
						bean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEING.value);
					}
				}

				bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDate());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_EDIT;
				message = "商行黑名单的审核:" + bean.getId();
				recordRunningLog("Verify.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}
		} else if (CMD_APPROVE.equals(cmd)) {
			// 审批
			List<NsBankBlackList> fromBeans = (List<NsBankBlackList>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String approve = (String) context.getAttribute(IN_APPROVE);

			for (NsBankBlackList bblt : fromBeans) {
				NsBankBlackList bean = service.selectById(bblt.getId());
				if (bean.getOperateState().equals(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDVRED.value)) {
					if (approve.equals("approveT")) {
						bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDAPED.value);
					} else if (approve.equals("approveF")) {
						bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDED.value);
					}
				}
				if (bean.getShareState().equals(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHVRED.value)) {
					if (approve.equals("approveT")) {
						bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHAPED.value);
						if (bblt.getShare() == SystemConstant.FALSE) {
							bean.setShare(SystemConstant.TRUE);
						} else {
							bean.setShare(SystemConstant.FALSE);
						}
					} else if (approve.equals("approveF")) {
						bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHED.value);
					}
				}
				if (bean.getValidDate().equals(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAVRED.value)) {
					if (approve.equals("approveT")) {
						bean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAAPED.value);
						if (bblt.getValid() == SystemConstant.FALSE) {
							bean.setValid(SystemConstant.TRUE);
						} else {
							bean.setValid(SystemConstant.FALSE);
						}
					} else if (approve.equals("approveF")) {
						bean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.VAED.value);
					}
				}
				if (bean.getDelState().equals(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEVRED.value)) {
					if (approve.equals("approveT")) {
						bean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.N.value);
						if (bblt.getDel() == SystemConstant.FALSE) {
							bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.N.value);
							bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.N.value);
							bean.setValidState(ReportEnum.BANK_BLACKLIST_VALID_STATE.N.value);
							bean.setShare(SystemConstant.FALSE);
							bean.setValid(SystemConstant.FALSE);
							bean.setDel(SystemConstant.TRUE);
						}
					} else if (approve.equals("approveF")) {
						bean.setDelState(ReportEnum.BANK_BLACKLIST_DEL_STATE.DEED.value);
					}
				}
				bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDate());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_EDIT;
				message = "商行黑名单的审批:" + bean.getId();
				recordRunningLog("Approve.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}

		} else if (CMD_SHARE.equals(cmd)) {
			// 审批
			List<NsBankBlackList> fromBeans = (List<NsBankBlackList>) context.getAttribute(IN_BANK_BLACK_LISTS);
			String share = (String) context.getAttribute(IN_SHARE);
			for (NsBankBlackList bblt : fromBeans) {
				NsBankBlackList bean = service.selectById(bblt.getId());
				if (share.equals("shareT")) {
					bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHED.value);
				} else if (share.equals("shareF")) {
					bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDED.value);
					bean.setShareState(ReportEnum.BANK_BLACKLIST_SHARE_STATE.SHED.value);
				}

				bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setLastModifyDate(DateUtil.getCurrentDate());
				service.modEntity(bean);

				operateType = SystemConstant.LOG_EDIT;
				message = "商行黑名单的共享:" + bean.getId();
				recordRunningLog("Share.log", message, bean, service);
				recordOperateLog(globalInfo, operateType, message);
			}

		} else if (CMD_EDIT.equals(cmd)) {
			NsBankBlackList fromBean = (NsBankBlackList) context.getAttribute(IN_BANK_BLACK_LIST);
			NsBankBlackList bean = service.selectById(fromBean.getId());
			if (fromBean.getBankCode().trim() == "") {
				bean.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			} else {
				bean.setBankCode(fromBean.getBankCode());
			}
			bean.setOperateState(ReportEnum.BANK_BLACKLIST_OPERATE_STATE.EDED.value);

			bean.setAccountType(fromBean.getAccountType());
			bean.setAccountCode(fromBean.getAccountCode());
			bean.setCertificateType(fromBean.getCertificateType());
			bean.setCertificateNumber(fromBean.getCertificateNumber());
			bean.setClientName(fromBean.getClientName());
			bean.setClientEnglishName(fromBean.getClientEnglishName());
			bean.setBlacklistType(fromBean.getBlacklistType());
			bean.setValid(fromBean.getValid());
			bean.setValidDate(fromBean.getValidDate());

			if (bean.getValid() == SystemConstant.TRUE && fromBean.getValid() == SystemConstant.FALSE) {
				bean.setUnblacklistedDate(DateUtil.getCurrentDate());
				bean.setUnblacklistedOperator(GlobalInfo.getCurrentInstance().getTlrno());
				bean.setUnblacklistedReason(fromBean.getUnblacklistedReason());
			}
			bean.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bean.setLastModifyDate(DateUtil.getCurrentDate());
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
