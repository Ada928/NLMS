package com.huateng.ebank.business.management.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.Bctl;

import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.service.BctlService;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;

public class BranchManageQueryGetter extends BaseGetter {

	public Result call() throws AppException {
		String brcode = this.getCommQueryServletRequest().getParameter("brcode");

		if (brcode == null || brcode.equals("")) {
			brcode = "0";
		}

		Bctl bctl = (Bctl) BctlService.getInstance().queryByBrcode(brcode);

		ResultMng.fillResultByObject(this.commonQueryBean, getCommQueryServletRequest(), bctl, getResult());

		List content = new ArrayList();
		getResult().setContent(content);
		getResult().init();

		return getResult();
	}
}
