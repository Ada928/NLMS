package com.cibfintech.blacklist.bankblacklist.getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.blacklist.NsBankBlackListAuditState;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListAuditStateService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.view.pub.BankBlackListAuditStateView;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

@SuppressWarnings("unchecked")
public class BankBlackListShareGetter extends BaseGetter {
	/*
	 * 获取商行黑名单
	 * 
	 * @author
	 */
	@Override
	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "商行黑名单分享");
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
		String qShare = getCommQueryServletRequest().getParameter("qShareType");
		String qClientName = getCommQueryServletRequest().getParameter("qClientName");
		String qClientEnglishName = getCommQueryServletRequest().getParameter("qClientEnglishName");
		String qCertificateType = getCommQueryServletRequest().getParameter("qCertificateType");
		String qCertificateNumber = getCommQueryServletRequest().getParameter("qCertificateNumber");
		String qAccountCode=getCommQueryServletRequest().getParameter("qAccountCode");
		String qCardBkBookNo=getCommQueryServletRequest().getParameter("qCardBkBookNo");
		//qShare = qShare == null ? "" : qShare;
		StringBuffer hql = new StringBuffer(" from NsBankBlackListAuditState po where 1=1");
		hql.append(" order by po.auditType desc, po.auditState desc, po.editDate desc");

		BankBlackListService service = BankBlackListService.getInstance();
		BankBlackListAuditStateService auditStateService = BankBlackListAuditStateService.getInstance();

		List<NsBankBlackListAuditState> auditStates = auditStateService.getBankBankListAuditStateByHql(hql.toString());
		
		StringBuffer hql2 = new StringBuffer(" from NsBankBlackList bblt where 1=1");
		hql2.append(" and bblt.del= 'F'");
		//1:已分享  2：未分享
		if("2".equals(qShare)){    
			hql2.append(" and bblt.share= 'F'");
		}else if("1".equals(qShare)){
			hql2.append(" and bblt.share= 'T'");
		}
		if (StringUtils.isNotBlank(qClientName)) {
			hql2.append(" and bblt.clientName like '%").append(qClientName.trim()).append("%'");
		}
		if (StringUtils.isNotBlank(qClientEnglishName)) {
			hql2.append(" and bblt.clientEnglishName like '%").append(qClientEnglishName.trim()).append("%'");
		}
		if (StringUtils.isNotBlank(qCertificateType)) {
			hql2.append(" and bblt.certificateType = '").append(qCertificateType.trim()).append("'");
		}
		if (StringUtils.isNotBlank(qCertificateNumber)) {
			hql2.append(" and bblt.certificateNumber like '%").append(qCertificateNumber.trim()).append("%'");
		}
		
		if(StringUtils.isNotBlank(qAccountCode)){
			hql2.append(" and bblt.accountCode = '").append(qAccountCode.trim()).append("'");
		}
		if(StringUtils.isNotBlank(qCardBkBookNo)){
			hql2.append(" and bblt.cardBkBookNo = '").append(qCardBkBookNo.trim()).append("'");
		}
		hql.append(" and rownum<10000 order by bblt.blacklistedDate desc");
		
		HashMap<String, NsBankBlackList> blacklistMap = BankBlackListService.getInstance().getBankBlackListByHql(hql2.toString());

		List<BankBlackListAuditStateView> auditStateViews = new ArrayList<BankBlackListAuditStateView>();
		for (NsBankBlackListAuditState auditState : auditStates) {
			BankBlackListAuditStateView view = new BankBlackListAuditStateView();
			//NsBankBlackList blackList = service.selectById(auditState.getBlacklistID());
			NsBankBlackList blackList = blacklistMap.get(auditState.getBlacklistID());
			
			if (null != blackList && !"".equals(blackList)){
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
				view.setAccountType(blackList.getAccountType());
				view.setAccountCode(blackList.getAccountCode());
				view.setCardBkBookNo(blackList.getCardBkBookNo());
	
				auditStateViews.add(view);
			}
			
		}

		PageQueryResult pageQueryResult = new PageQueryResult();
		if (auditStateViews != null && auditStateViews.size() > 0) {
			pageQueryResult.setTotalCount(auditStateViews.size());
		} else {
			pageQueryResult.setTotalCount(0);
		}
		pageQueryResult.setQueryResult(auditStateViews);

		return pageQueryResult;
	}
}