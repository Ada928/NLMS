package com.cibfintech.blacklist.bankinfo.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.Bctl;

import com.cibfintech.blacklist.bankinfo.service.BankInfoService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class BankManageQueryGetter extends BaseGetter {

	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "银行信息查询");
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
		String brcode = this.getCommQueryServletRequest().getParameter("brcode");
		String opType = this.getCommQueryServletRequest().getParameter("opType");
		if (!opType.equals("new")) {
			List list = new ArrayList();
			Bctl bctl = BankInfoService.getInstance().selectById(brcode);
			list.add(bctl);
			result.setQueryResult(list);
			result.setTotalCount(1);
		}

		return result;
	}

}
