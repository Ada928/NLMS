package com.cibfintech.blacklist.Scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsBankBlackListAuditState;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListAuditStateService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.report.utils.ReportEnum;

public class BankBlackListAutoValidChange {
	private static final Logger logger = Logger.getLogger(BankBlackListAutoValidChange.class);

	/*
	 * 每日检查黑名单是否到期
	 */
	public void dailyCheckValidBlackList() throws CommonException {
		Date date = new Date();

		BankBlackListService blacklistService = BankBlackListService.getInstance();
		BankBlackListAuditStateService bankBlackListAuditStateService = BankBlackListAuditStateService.getInstance();
		List<NsBankBlackList> blacklist = blacklistService.getAllBankBlacklist();
		for (NsBankBlackList bblt : blacklist) {
			if (date.getTime() > DateUtil.getNextDayWithTime(bblt.getValidDate()).getTime()) {
				bblt.setApprove(SystemConstant.FALSE);
				bblt.setShare(SystemConstant.FALSE);
				bblt.setBlacklistType(SystemConstant.BLACKLIST_TYPE_GREY);
				bblt.setLastModifyDate(new Date());
				bblt.setLastModifyOperator(SystemConstant.AUTO_OPERATER);
				bblt.setValidDate(DateUtil.getDayAfter100Years());

				List<NsBankBlackListAuditState> auditStates = bankBlackListAuditStateService
						.getBankBankListAuditStateByHql(" from NsBankBlackListAuditState po where po.blacklistID = " + bblt.getId());
				NsBankBlackListAuditState auditState = auditStates.get(0);
				auditState.setEditUserID(SystemConstant.AUTO_OPERATER);
				auditState.setAuditType(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.EDIT.value);
				auditState.setAuditState(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value);
				auditState.setEditDate(new Date());

				blacklistService.modOrAddEntity(bblt);
				bankBlackListAuditStateService.modOrAddEntity(auditState);
			}
		}
	}
}
