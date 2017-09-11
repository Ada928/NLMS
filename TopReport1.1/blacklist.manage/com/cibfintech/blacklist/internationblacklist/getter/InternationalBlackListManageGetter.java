package com.cibfintech.blacklist.internationblacklist.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.blacklist.NsInternationalBlackList;

import com.cibfintech.blacklist.internationblacklist.service.InternationalBlackListService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

/**
 *
 * author by getter
 */
public class InternationalBlackListManageGetter extends BaseGetter {

	public Result call() throws AppException {
		try {
			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "国际黑名单信息查询");
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
		String opType = this.getCommQueryServletRequest().getParameter("op");
		if (!opType.equals("add")) {
			List list = new ArrayList();
			NsInternationalBlackList bean = InternationalBlackListService.getInstance().selectById(id);
			list.add(bean);
			result.setQueryResult(list);
			result.setTotalCount(1);
		}

		return result;
	}
}
