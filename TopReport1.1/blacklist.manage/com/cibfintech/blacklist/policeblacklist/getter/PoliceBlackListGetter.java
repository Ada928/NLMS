package com.cibfintech.blacklist.policeblacklist.getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.NsPoliceBLOperateLog;

import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListOperateLogService;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
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
import com.huateng.report.utils.ReportEnum;

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
		String partyId = getCommQueryServletRequest().getParameter("qPartyId");
		String qCertificateType = getCommQueryServletRequest().getParameter("qCertificateType");
		String qCertificateNumber = getCommQueryServletRequest().getParameter("qCertificateNumber");
		String qOperateState = getCommQueryServletRequest().getParameter("qOperateState");
		int pageSize = this.getResult().getPage().getEveryPage();
		int pageIndex = this.getResult().getPage().getCurrentPage();

		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from NsPoliceBlackList pblt where 1=1");
		hql.append(" and pblt.del=?");
		list.add("F");

		if (StringUtils.isNotBlank(partyId)) {
			hql.append(" and pblt.id = '").append(partyId.trim()).append("'");
		}
		if (StringUtils.isNotBlank(qCertificateType)) {
			hql.append(" and pblt.certificateType = '").append(qCertificateType.trim()).append("'");
		}
		if (StringUtils.isNotBlank(qCertificateNumber)) {
			hql.append(" and pblt.certificateNumber like '%").append(qCertificateNumber.trim()).append("%'");
		}
		if (StringUtils.isNotBlank(qOperateState)) {
			hql.append(" and pblt.operateState='").append(qOperateState.trim()).append("'");
		} else {
			hql.append(" and pblt.operateState<>'").append(ReportEnum.REPORT_ST1.N.value).append("'");
		}

		PageQueryResult pqr = PoliceBlackListService.getInstance().pageQueryByHql(pageIndex, pageSize, hql.toString(), list);

		String message = "公安部黑名单的查询:partyId=" + partyId + "certificateType=" + qCertificateType + "certificateNumber=" + qCertificateNumber;
		recordOperateLog(GlobalInfo.getCurrentInstance(), pqr.getTotalCount(), message);
		return pqr;
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, int count, String message) {
		PoliceBlackListOperateLogService service = PoliceBlackListOperateLogService.getInstance();
		NsPoliceBLOperateLog bean = new NsPoliceBLOperateLog();
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
