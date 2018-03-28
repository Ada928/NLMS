package com.cibfintech.blacklist.bankblacklist.getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.NsBankBLOperateLog;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListOperateLogService;
import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.cibfintech.blacklist.util.GenerateID;
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

@SuppressWarnings("unchecked")
public class ShareBankBlackListQueryGetter extends BaseGetter {
	/*
	 * 获取商行黑名单
	 * 
	 * @author huangcheng
	 */
	@Override
	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "商行黑名单分享查询");
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
		String partyId = getCommQueryServletRequest().getParameter("qPartyId");
		String qCertificateType = getCommQueryServletRequest().getParameter("qCertificateType");
		String qCertificateNumber = getCommQueryServletRequest().getParameter("qCertificateNumber");
		String qClientName = getCommQueryServletRequest().getParameter("qClientName");
		String qClientEnglishName = getCommQueryServletRequest().getParameter("qClientEnglishName");
		int pageSize = this.getResult().getPage().getEveryPage();
		int pageIndex = this.getResult().getPage().getCurrentPage();
		GlobalInfo globalinfo = GlobalInfo.getCurrentInstance();

		StringBuffer hql = new StringBuffer(" from NsBankBlackList bblt where bblt.del='F'");
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(partyId)) {
			hql.append(" and bblt.id = ?");
			list.add(partyId.trim());
		}
		if (StringUtils.isNotBlank(qCertificateType)) {
			hql.append(" and bblt.certificateType = ?");
			list.add(qCertificateType.trim());
		}
		if (StringUtils.isNotBlank(qCertificateNumber)) {
			hql.append(" and bblt.certificateNumber like ?");
			list.add("%" + qCertificateNumber.trim() + "%");
		}
		if (StringUtils.isNotBlank(qClientName)) {
			hql.append(" and bblt.clientName like ?");
			list.add("%" + qClientName.trim() + "%");
		}
		if (StringUtils.isNotBlank(qClientEnglishName)) {
			hql.append(" and bblt.clientEnglishName like ?");
			list.add("%" + qClientEnglishName.trim() + "%");
		}
		hql.append(" and bblt.share=?");
		list.add("T");
		hql.append(" and bblt.approve=?");
		list.add("T");
		hql.append(" order by bblt.createDate desc");

		PageQueryResult pqr = BankBlackListService.getInstance().pageQueryByHql(pageIndex, pageSize, hql.toString(), list);

		String message = "国际黑名单的查询:partyId=" + partyId + ",certificateType=" + qCertificateType + ",certificateNumber=" + qCertificateNumber;
		recordOperateLog(globalinfo, pqr.getTotalCount(), message);
		return pqr;
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, int count, String message) {
		BankBlackListOperateLogService service = BankBlackListOperateLogService.getInstance();
		NsBankBLOperateLog bean = new NsBankBLOperateLog();
		bean.setBrcode(globalinfo.getBrcode());
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setQueryType("share");
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
