package com.cibfintech.blacklist.bankinfo.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.RoleInfo;

import com.cibfintech.blacklist.bankinfo.service.BankOperateLogService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.CommonFunctions;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.service.pub.UserMgrService;

/**
 * @Description: 日志查询
 * @Package: com.huateng.ebank.business.custadmin.getter
 * @Company: Shanghai Huateng Software Systems Co., Ltd.
 */
public class BankOperateLogQueryGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {
		try {

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "银行操作日志查询");

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

	private PageQueryResult getData() throws CommonException {
		int pageIndex = getResult().getPage().getCurrentPage();
		int pageSize = getResult().getPage().getEveryPage();

		String qtlrNo = (String) getCommQueryServletRequest().getParameterMap().get("qtlrNo");
		String qtlrIP = (String) getCommQueryServletRequest().getParameterMap().get("qtlrIP");
		String startDate = (String) getCommQueryServletRequest().getParameterMap().get("startDate");
		String endDate = (String) getCommQueryServletRequest().getParameterMap().get("endDate");

		if (startDate != null && endDate != null) {
			if (com.huateng.common.DateUtil.comparaDate(endDate, startDate)) {
				ExceptionUtil.throwCommonException("开始日期大于结束日期！", ErrorCode.ERROR_CODE_OVER_HEAD);
			}
		}

		GlobalInfo globalinfo = GlobalInfo.getCurrentInstance();
		List<RoleInfo> roleInfos = UserMgrService.getInstance().getUserRoles(globalinfo.getTlrno());
		boolean isSuperManager = false;
		for (RoleInfo roleInfo : roleInfos) {
			if (roleInfo.getRoleType().equals(SystemConstant.ROLE_TYPE_SYS_MNG)) {
				isSuperManager = true;
				break;
			}
		}

		StringBuffer sb = new StringBuffer("");
		List<Object> list = new ArrayList<Object>();
		sb.append(" from BctlOperateLog log where 1=1");
		if (!DataFormat.isEmpty(qtlrNo)) {
			sb.append(" and  log.tlrno= ?");
			list.add(qtlrNo);
		}
		if (!DataFormat.isEmpty(qtlrIP)) {
			sb.append(" and  log.tlrIP= ?");
			list.add(qtlrIP);
		}
		if (!isSuperManager) {
			sb.append(" and log.brcode = ?");
			list.add(globalinfo.getBrcode());
		}
		if (!DataFormat.isEmpty(startDate)) {
			sb.append(" and log.createDate>=?");
			list.add(DateUtil.stringToDate2(startDate));
		}
		if (!DataFormat.isEmpty(endDate)) {
			sb.append(" and log.createDate<?");
			list.add(DateUtil.getStartDateByDays(DateUtil.stringToDate2(endDate), -1));
		}
		sb.append(" order by log.tlrno, log.createDate desc");

		BankOperateLogService service = BankOperateLogService.getInstance();
		return service.pageQueryByHql(pageSize, pageIndex, sb.toString(), list);
	}
}
