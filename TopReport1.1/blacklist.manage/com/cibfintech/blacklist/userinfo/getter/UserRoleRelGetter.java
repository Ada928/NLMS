package com.cibfintech.blacklist.userinfo.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.RoleInfo;
import resource.bean.pub.TlrRoleRel;

import com.cibfintech.blacklist.roleinfo.service.RoleInfoService;
import com.cibfintech.blacklist.roleinfo.service.RoleTlrRelService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class UserRoleRelGetter extends BaseGetter {

	public Result call() throws AppException {
		try {
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

	private PageQueryResult getData() throws CommonException {
		PageQueryResult pageQueryResult = new PageQueryResult();
		String tlrno = getCommQueryServletRequest().getParameter("tlrno");

		List<TlrRoleRel> list = RoleTlrRelService.getInstance().getTlrRoleRelByTlrno(tlrno);
		List<RoleInfo> roleList = new ArrayList<RoleInfo>();
		for (TlrRoleRel obj : list) {
			roleList.add(RoleInfoService.getInstance().selectById(obj.getRoleId()));
		}

		if (roleList != null && roleList.size() > 0) {
			pageQueryResult.setTotalCount(roleList.size());
		} else {
			pageQueryResult.setTotalCount(0);
		}
		pageQueryResult.setQueryResult(roleList);

		return pageQueryResult;
	}
}
