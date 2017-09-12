package com.cibfintech.blacklist.getter;

import java.util.ArrayList;
import java.util.List;

import com.cibfintech.blacklist.service.QueryDailyLogCountService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.CommonFunctions;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

/**
 * @Description: 日志查询
 * @Package: com.huateng.ebank.business.custadmin.getter
 * @Company: Shanghai Huateng Software Systems Co., Ltd.
 */
public class QueryDailyLogCountGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {
		try {

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "每日黑名单日志查询");
			CommonFunctions comm = CommonFunctions.getInstance();
			PageQueryResult pageResult = getData();
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(), pageResult.getQueryResult(), getResult());
			result.setContent(pageResult.getQueryResult());
			result.getPage().setTotalPage(pageResult.getPageCount(getResult().getPage().getEveryPage()));
			result.init();
			return result;
		} catch (CommonException e) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("deprecation")
	private PageQueryResult getData() throws CommonException {
		int pageIndex = getResult().getPage().getCurrentPage();
		int pageSize = getResult().getPage().getEveryPage();

		String qbrcode = (String) getCommQueryServletRequest().getParameterMap().get("qbrcode");
		String startDate = (String) getCommQueryServletRequest().getParameterMap().get("startDate");
		String endDate = (String) getCommQueryServletRequest().getParameterMap().get("endDate");
		if (startDate != null && endDate != null) {
			if (com.huateng.common.DateUtil.comparaDate(endDate, startDate)) {
				ExceptionUtil.throwCommonException("开始日期大于结束日期！", ErrorCode.ERROR_CODE_OVER_HEAD);
			}
		}

		StringBuffer sb = new StringBuffer("");
		List<Object> list = new ArrayList<Object>();
		sb.append(" from NsQueryDailyLogCount cont where 1=1 ");

		if (!DataFormat.isEmpty(qbrcode)) {
			sb.append(" and cont.brcode = ? ");
			list.add(qbrcode);
		}

		if (!DataFormat.isEmpty(startDate)) {
			sb.append(" and cont.countDate>=? ");
			list.add(DateUtil.stringToDate2(startDate));
		}
		if (!DataFormat.isEmpty(endDate)) {
			sb.append(" and cont.countDate<? ");
			list.add(DateUtil.getStartDateByDays(DateUtil.stringToDate2(endDate), -1));
		}
		sb.append(" order by cont.brcode, cont.createDate desc");

		QueryDailyLogCountService service = QueryDailyLogCountService.getInstance();
		return service.pageQueryByHql(pageSize, pageIndex, sb.toString(), list);
	}

}
