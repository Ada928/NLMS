package com.cibfintech.blacklist.bankblacklist.getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
		String qClientName = getCommQueryServletRequest().getParameter("qClientName");
		String qClientEnglishName = getCommQueryServletRequest().getParameter("qClientEnglishName");
		String qCertificateType = getCommQueryServletRequest().getParameter("qCertificateType");
		String qCertificateNumber = getCommQueryServletRequest().getParameter("qCertificateNumber");
		String qAccountCode=getCommQueryServletRequest().getParameter("qAccountCode");
		String qCardBkBookNo=getCommQueryServletRequest().getParameter("qCardBkBookNo");
		// int pageSize = this.getResult().getPage().getEveryPage();
		// int pageIndex = this.getResult().getPage().getCurrentPage();

		GlobalInfo globalinfo = GlobalInfo.getCurrentInstance();
		List<RoleInfo> roleInfos = UserMgrService.getInstance().getUserRoles(globalinfo.getTlrno());
		boolean isSuperManager = false;
		for (RoleInfo roleInfo : roleInfos) {
			if (roleInfo.getRoleType().equals(SystemConstant.ROLE_TYPE_SYS_MNG)) {
				isSuperManager = true;
				break;
			}
		}

		StringBuffer hql = new StringBuffer(" from NsBankBlackListAuditState po where ");

		hql.append(" (po.auditType='").append(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.ADD.value).append("'");
		hql.append(" and po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDING.value).append("')");
		hql.append(" or (po.auditType='").append(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.ADD.value).append("'");
		hql.append(" and po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value).append("')");
		hql.append(" or( po.auditType='").append(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.EDIT.value).append("'");
		hql.append(" and po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDING.value).append("')");
		hql.append(" or( po.auditType='").append(ReportEnum.BANK_BLACKLIST_AUDIT_TYPE.EDIT.value).append("'");
		hql.append(" and po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.EDED.value).append("')");
		hql.append(" or po.auditState='").append(ReportEnum.BANK_BLACKLIST_AUDIT_STATE.VRED.value).append("')");
		if (!isSuperManager) {
			hql.append(" and po.brcode='").append(globalinfo.getBrcode()).append("'");
		}

		hql.append(" order by po.editDate desc");

		StringBuffer hql2 = new StringBuffer(" from NsBankBlackList bblt where 1=1");
		hql2.append(" and bblt.del= 'F'");

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
			hql2.append(" and bblt.certificateNumber = '").append(qCertificateNumber.trim()).append("'");
		}
		if(StringUtils.isNotBlank(qAccountCode)){
			hql2.append(" and bblt.accountCode = '").append(qAccountCode.trim()).append("'");
		}
		if(StringUtils.isNotBlank(qCardBkBookNo)){
			hql2.append(" and bblt.cardBkBookNo = '").append(qCardBkBookNo.trim()).append("'");
		}
		hql2.append(" and rownum<10000 order by bblt.blacklistedDate desc ");

		HashMap<String, NsBankBlackList> blacklistMap = BankBlackListService.getInstance().getBankBlackListByHql(hql2.toString());

		BankBlackListAuditStateService auditStateService = BankBlackListAuditStateService.getInstance();

		List<NsBankBlackListAuditState> auditStates = auditStateService.getBankBankListAuditStateByHql(hql.toString());

		List<BankBlackListAuditStateView> auditStateViews = new ArrayList<BankBlackListAuditStateView>();

		for (NsBankBlackListAuditState auditState : auditStates) {
			BankBlackListAuditStateView view = new BankBlackListAuditStateView();
			
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
			
//			if (null == blackList)
//				break;
			//修改后展示全部编辑后的所有数据
				
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

		String message = "商行黑名单编辑时查询:qClientName=" + qClientName + ",certificateType=" + qCertificateType + ",certificateNumber=" + qCertificateNumber;
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