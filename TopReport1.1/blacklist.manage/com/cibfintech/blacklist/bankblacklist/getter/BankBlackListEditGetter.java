package com.cibfintech.blacklist.bankblacklist.getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import resource.bean.blacklist.NsBankBLOperateLog;
import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsBankBlackListAuditState;
import resource.bean.pub.RoleInfo;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListAuditStateService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.blacklist.util.GenerateID;
import com.cibfintech.view.pub.BankBlackListAuditStateView;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.report.utils.ReportEnum;
import com.huateng.service.pub.UserMgrService;

@SuppressWarnings("unchecked")
public class BankBlackListEditGetter extends BaseGetter {
	/*
	 * 获取商行黑名单
	 * 
	 * @author
	 */
	@Override
	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "商行黑名单编辑");
			PageQueryResult pageResult = getData();
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(), pageResult.getQueryResult(), getResult());
			result.setContent(pageResult.getQueryResult());
			result.getPage().setTotalPage(pageResult.getPageCount(getResult().getPage().getEveryPage()));
			result.init();
			return result;
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	protected PageQueryResult getData() throws Exception {
		String qPartyId = getCommQueryServletRequest().getParameter("qPartyId");
		String qCertificateType = getCommQueryServletRequest().getParameter("qCertificateType");
		String qCertificateNumber = getCommQueryServletRequest().getParameter("qCertificateNumber");

		GlobalInfo globalinfo = GlobalInfo.getCurrentInstance();
		List<RoleInfo> roleInfos = UserMgrService.getInstance().getUserRoles(globalinfo.getTlrno());
		boolean isSuperManager = false;
		for (RoleInfo roleInfo : roleInfos) {
			if (roleInfo.getRoleType().equals(SystemConstant.ROLE_TYPE_SYS_MNG)) {
				isSuperManager = true;
				break;
			}
		}

		StringBuffer hql = new StringBuffer(" from NsBankBlackListAuditState po where 1=1");
		if (!isSuperManager) {
			hql.append(" and po.editUserID='").append(globalinfo.getTlrno()).append("'");
		}
		hql.append(" or po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDING.value).append("'");
		hql.append(" or po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value).append("'");
		hql.append(" order by po.editDate desc");

		BankBlackListService service = BankBlackListService.getInstance();
		BankBlackListAuditStateService auditStateService = BankBlackListAuditStateService.getInstance();

		List<NsBankBlackListAuditState> auditStates = auditStateService.getBankBankListAuditStateByHql(hql.toString());

		List<BankBlackListAuditStateView> auditStateViews = new ArrayList<BankBlackListAuditStateView>();
		for (NsBankBlackListAuditState auditState : auditStates) {
			BankBlackListAuditStateView view = new BankBlackListAuditStateView();
			NsBankBlackList blackList = service.selectById(auditState.getBlacklistID());
			view.setId(auditState.getId());
			view.setAuditState(auditState.getAuditState());
			view.setAuditType(auditState.getAuditType());
			view.setBlacklistID(auditState.getBlacklistID());
			view.setBrcode(auditState.getBrcode());
			view.setEditUserID((auditState.getEditUserID()));
			view.setVerifyUserID(auditState.getVerifyUserID());
			view.setApproveUserID(auditState.getApproveUserID());
			view.setEditDate(auditState.getEditDate());
			view.setVerifyDate(auditState.getVerifyDate());
			view.setApproveDate(auditState.getApproveDate());
			view.setBlacklistType(blackList.getBlacklistType());
			view.setCertificateNumber(blackList.getCertificateNumber());
			view.setCertificateType(blackList.getCertificateType());
			view.setClientName(blackList.getClientName());
			view.setClientEnglishName(blackList.getClientEnglishName());

			auditStateViews.add(view);
		}

		PageQueryResult pageQueryResult = new PageQueryResult();
		if (auditStateViews != null && auditStateViews.size() > 0) {
			pageQueryResult.setTotalCount(auditStateViews.size());
		} else {
			pageQueryResult.setTotalCount(0);
		}
		pageQueryResult.setQueryResult(auditStateViews);

		String message = "商行黑名单编辑时查询:partyId=" + qPartyId + ",certificateType=" + qCertificateType + ",certificateNumber=" + qCertificateNumber;
		recordOperateLog(globalinfo, pageQueryResult.getTotalCount(), message);

		return pageQueryResult;
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, int count, String message) {
		BankBlackListOperateLogService service = BankBlackListOperateLogService.getInstance();
		NsBankBLOperateLog bean = new NsBankBLOperateLog();
		bean.setBrcode(globalinfo.getBrcode());
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setQueryType("");
		bean.setQueryRecordNumber(String.valueOf(count));
		bean.setTlrIP(globalinfo.getIp());
		bean.setTlrno(globalinfo.getTlrno());
		bean.setOperateType(SystemConstant.LOG_QUERY);
		bean.setMessage(message);
		bean.setCreateDate(new Date());
		try {
			service.addEntity(bean);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}
}