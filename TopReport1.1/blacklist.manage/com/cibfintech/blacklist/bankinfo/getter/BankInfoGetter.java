package com.cibfintech.blacklist.bankinfo.getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.BctlOperateLog;
import resource.bean.pub.RoleInfo;

import com.cibfintech.blacklist.bankinfo.service.BankInfoService;
import com.cibfintech.blacklist.bankinfo.service.BankOperateLogService;
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
import com.huateng.service.pub.UserMgrService;

@SuppressWarnings("unchecked")
public class BankInfoGetter extends BaseGetter {
	/*
	 * 获取商行黑名单
	 * 
	 * @author huangcheng
	 */
	@Override
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
		String brNo = getCommQueryServletRequest().getParameter("brhNo");
		String brName = getCommQueryServletRequest().getParameter("brhName");
		int pageSize = this.getResult().getPage().getEveryPage();
		int pageIndex = this.getResult().getPage().getCurrentPage();

		GlobalInfo globalinfo = GlobalInfo.getCurrentInstance();
		List<RoleInfo> roleInfos = UserMgrService.getInstance().getUserRoles(globalinfo.getTlrno());
		boolean isSuperManager = false;
		for (RoleInfo roleInfo : roleInfos) {
			if (roleInfo.getRoleType().equals(SystemConstant.ROLE_TYPE_SYS_MNG)) {
				isSuperManager = true;
				break;
			}
		}

		StringBuffer hql = new StringBuffer(" from resource.bean.pub.Bctl bblt where 1=1");
		List<Object> list = new ArrayList<Object>();
		hql.append(" and bblt.del= ? ");
		list.add("F");

		if (StringUtils.isNotBlank(brNo)) {
			hql.append(" and bblt.brno= ? ");
			list.add(brNo.trim());
		}
		if (StringUtils.isNotBlank(brName)) {
			hql.append(" and bblt.brname like ? ");
			list.add("%" + brName.trim() + "%");
		}
		if (!isSuperManager) {
			hql.append(" and bblt.brcode= ? ");
			list.add(globalinfo.getBrcode());
		}
		hql.append(" order by bblt.brcode, bblt.effectDate desc");

		PageQueryResult pqr = BankInfoService.getInstance().pageQueryByHql(pageIndex, pageSize, hql.toString(), list);
		String message = "银行信息管理:brhNo=" + brNo + ",brhName=" + brName;
		recordOperateLog(globalinfo, pqr.getTotalCount(), message);
		return pqr;
	}

	// 记录查询日志
	private void recordOperateLog(GlobalInfo globalinfo, int count, String message) {
		BankOperateLogService service = BankOperateLogService.getInstance();
		BctlOperateLog bean = new BctlOperateLog();
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
