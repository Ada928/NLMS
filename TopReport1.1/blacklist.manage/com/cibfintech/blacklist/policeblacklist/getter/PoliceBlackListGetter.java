package com.cibfintech.blacklist.policeblacklist.getter;

import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListOperateLogService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

@SuppressWarnings("unchecked")
public class PoliceBlackListGetter extends BaseGetter {
	/*
	 * 获取公安部黑名单
	 * 
	 * @author huangcheng
	 */
	@Override
	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "公安部黑名单管理查询");
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
		String qOperateState = getCommQueryServletRequest().getParameter("qOperateState");
		int pageSize = this.getResult().getPage().getEveryPage();
		int pageIndex = this.getResult().getPage().getCurrentPage();
		PageQueryResult pqr = PoliceBlackListService.getInstance().pageQueryByHql(pageIndex, pageSize, qPartyId, qCertificateType, qCertificateNumber,
				qOperateState);

		String message = "公安部黑名单的查询:partyId=" + qPartyId + "certificateType=" + qCertificateType + "certificateNumber=" + qCertificateNumber;
		PoliceBlackListOperateLogService policeBLOperateLogService = PoliceBlackListOperateLogService.getInstance();
		policeBLOperateLogService.savePoliceBLOperateLog(SystemConstant.LOG_QUERY, "", String.valueOf(pqr.getTotalCount()), message);
		return pqr;
	}
}
