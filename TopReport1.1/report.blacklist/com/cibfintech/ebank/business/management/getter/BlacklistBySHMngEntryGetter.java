package com.cibfintech.ebank.business.management.getter;

import org.apache.commons.lang.StringUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

/**
 * @author zhiguo.zhao
 *
 */
public class BlacklistBySHMngEntryGetter extends BaseGetter {

	public Result call() throws AppException {
		try {

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "商行黑名单管理查询");
			PageQueryResult pageResult = getData();
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(), pageResult.getQueryResult(),
					getResult());
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
		ROOTDAO dao = ROOTDAOUtils.getROOTDAO();
		PageQueryCondition queryCondition = new PageQueryCondition();

		String partyId = getCommQueryServletRequest().getParameter("qPartyId");
		String zjzl = getCommQueryServletRequest().getParameter("qZjzl");
		String zjhm = getCommQueryServletRequest().getParameter("qZjhm");

		int pageSize = getResult().getPage().getEveryPage();
		int pageIndex = getResult().getPage().getCurrentPage();

		StringBuffer hql = new StringBuffer(" from TShhmd t where t.mdxz='1'");

		if (StringUtils.isNotBlank(partyId)) {
			hql.append(" and t.partyId like '%").append(partyId).append("%'");
		}
		if (StringUtils.isNotBlank(zjzl)) {
			hql.append(" and t.zjzl like '%").append(zjzl).append("%'");
		}
		if (StringUtils.isNotBlank(zjhm)) {
			hql.append(" and t.zjhm like '%").append(zjhm).append("%'");
		}

		hql.append(" order by t.partyId");

		queryCondition.setPageIndex(pageIndex);
		queryCondition.setPageSize(pageSize);
		queryCondition.setQueryString(hql.toString());

		return (PageQueryResult) dao.pageQueryByQL(queryCondition);
	}
}
