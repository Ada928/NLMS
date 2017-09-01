package com.cibfintech.report.blacklist.getter;

import java.util.Date;
import java.util.List;

import com.cibfintech.report.blacklist.service.InternationBlackListOperateLogService;
import com.huateng.common.DateUtil;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.CommonFunctions;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

/**
 * @Description: 日志查询
 * @Package: com.huateng.ebank.business.custadmin.getter
 * @Company: Shanghai Huateng Software Systems Co., Ltd.
 */
public class InternationBLOPLogQueryGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {
		try {

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME,
					"商行黑名单日志查询");

			CommonFunctions comm = CommonFunctions.getInstance();
			PageQueryResult pageResult = getData();
			ResultMng.fillResultByList(getCommonQueryBean(),
					getCommQueryServletRequest(), pageResult.getQueryResult(),
					getResult());
			result.setContent(pageResult.getQueryResult());
			result.getPage().setTotalPage(
					pageResult.getPageCount(getResult().getPage()
							.getEveryPage()));
			result.init();
			return result;
		} catch (CommonException e) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("deprecation")
	private PageQueryResult getData() throws CommonException {
		int pageIndex = getResult().getPage().getCurrentPage();
		int pageSize = getResult().getPage().getEveryPage();

		String qtlrNo = (String) getCommQueryServletRequest().getParameterMap()
				.get("qtlrNo");
		String qbrNo = (String) getCommQueryServletRequest().getParameterMap()
				.get("qbrNo");
		String qtlrIP = (String) getCommQueryServletRequest().getParameterMap()
				.get("qtlrIP");
		String startDate = (String) getCommQueryServletRequest()
				.getParameterMap().get("startDate");
		String endDate = (String) getCommQueryServletRequest()
				.getParameterMap().get("endDate");
		if (startDate != null && endDate != null) {
			if (DateUtil.comparaDate(endDate, startDate)) {
				ExceptionUtil.throwCommonException("开始日期大于结束日期！",
						ErrorCode.ERROR_CODE_OVER_HEAD);
			}
		}
		InternationBlackListOperateLogService internationBLOPLogService = InternationBlackListOperateLogService
				.getInstance();

		List list = internationBLOPLogService.sumQueryInternationBlacklist(
				com.huateng.ebank.framework.util.DateUtil
						.dateToString(com.huateng.ebank.framework.util.DateUtil
								.getBeforeDayWithTime(new Date())),
				com.huateng.ebank.framework.util.DateUtil
						.dateToString(new Date()));

		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			System.out.println(obj[0] + " " + obj[1]);
		}

		return internationBLOPLogService.queryInternationBLOperateLogDetail(
				pageIndex, pageSize, qtlrNo, qtlrIP, qbrNo, startDate, endDate);
	}

}
