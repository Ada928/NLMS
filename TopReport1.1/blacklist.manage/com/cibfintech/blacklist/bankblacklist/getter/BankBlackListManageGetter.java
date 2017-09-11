package com.cibfintech.blacklist.bankblacklist.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.blacklist.NsBankBlackList;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class BankBlackListManageGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "商行黑名单信息查询");
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
		PageQueryResult result = new PageQueryResult();
		String id = this.getCommQueryServletRequest().getParameter("id");
		String blacklistid = this.getCommQueryServletRequest().getParameter("blacklistid");
		String opType = this.getCommQueryServletRequest().getParameter("op");
		if (!opType.equals("add")) {
			List list = new ArrayList();
			NsBankBlackList bean = BankBlackListService.getInstance().selectById(blacklistid);
			bean.setAuditStateId(id);
			list.add(bean);
			result.setQueryResult(list);
			result.setTotalCount(1);
		}

		return result;
	}
}
