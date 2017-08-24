/*
 * ==================================================================
 * The Huateng Software License
 *
 * Copyright (c) 2004-2005 Huateng Software System.  All rights
 * reserved.
 * ==================================================================
 */
package com.huateng.ebank.business.opermng.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.Bctl;
import resource.bean.pub.TlrBctlRel;
import resource.bean.pub.TlrInfo;
import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.service.pub.UserMgrService;

/**
 * @author zhiguo.zhao
 *
 */
public class BctlMngEntryGetter extends BaseGetter {

	public Result call() throws AppException {
		try {
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
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected PageQueryResult getData() throws Exception {
		String tlrno = (String) getCommQueryServletRequest().getParameterMap()
				.get("tlrno");
		String op = (String) getCommQueryServletRequest().getParameterMap()
				.get("op");
		ROOTDAO rootdao = ROOTDAOUtils.getROOTDAO();
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();

		String hql = "select bctl from Bctl bctl where bctl.status='1'";

		TlrInfo tlrInfo = UserMgrService.getInstance().getUserInfo(globalInfo.getTlrno());
		String type = tlrInfo.getTlrType();
		if (!type.equals(SystemConstant.TLR_NO_TYPE_SUPER_MANAGE)) {
			hql += " and bctl.brno = '"
					+ globalInfo.getBrno()+ "' ";
		}

		if (op.equals("modify")) {
			hql += " and bctl.st='4'";
		}
		
		hql += " order by bctl.brno";
		List<Bctl> bcList = rootdao.queryByQL2List(hql);

		List<String> tlrnoBctlRel = new ArrayList<String>();
		if (!DataFormat.isEmpty(tlrno) && !tlrno.equals("0")) {
			String tempHql = "select tlrBctl from TlrBctlRel tlrBctl where tlrBctl.tlrNo = '"
					+ tlrno + "'";
			List<TlrBctlRel> tempList = rootdao.queryByQL2List(tempHql);
			for (TlrBctlRel temp : tempList) {
				tlrnoBctlRel.add(temp.getBrcode());
			}
		}
		if (tlrnoBctlRel.size() > 0) {
			for (Bctl bc : bcList) {
				bc.setSelected(tlrnoBctlRel.contains(bc.getBrcode()));
			}
		}

		PageQueryResult pageQueryResult = new PageQueryResult();
		pageQueryResult.setTotalCount(bcList.size());
		pageQueryResult.setQueryResult(bcList);

		return pageQueryResult;
	}
}
