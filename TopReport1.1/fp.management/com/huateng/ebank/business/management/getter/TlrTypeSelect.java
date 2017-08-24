package com.huateng.ebank.business.management.getter;

import java.util.ArrayList;
import java.util.List;

import resource.bean.pub.TlrInfo;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Page;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.business.management.bean.TlrType;
import com.huateng.ebank.business.management.common.DAOUtils;
import com.huateng.ebank.business.management.operation.TlrInfoExOperation;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.service.pub.UserMgrService;
import com.informix.util.stringUtil;

public class TlrTypeSelect extends BaseGetter {

	public Result call() throws AppException {
		try {
			PageQueryResult pageResult = getData();
			ResultMng.fillResultByList(getCommonQueryBean(),
					getCommQueryServletRequest(), pageResult.getQueryResult(),
					getResult());
			result.setContent(pageResult.getQueryResult());
			if (pageResult.getQueryResult().size() == 0) {
				result.getPage().setTotalPage(0);
			} else {
				result.getPage().setTotalPage(1);
			}
			result.init();
			return result;
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE,
					Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	protected PageQueryResult getData() throws Exception {
		List<TlrType> tlrTypeList = new ArrayList<TlrType>();
		// TlrType tlrType = new TlrType();
		TlrInfo tlrInfo = UserMgrService.getInstance().getUserInfo(
				GlobalInfo.getCurrentInstance().getTlrno());
		if (tlrInfo.getTlrType()
				.equals(SystemConstant.TLR_NO_TYPE_SUPER_MANAGE)) {
			tlrTypeList
					.add(new TlrType(
							SystemConstant.TLR_NO_TYPE_SUPER_MANAGE,
							SystemConstant.TLR_NO_TYPE_NAME[Integer
									.parseInt(SystemConstant.TLR_NO_TYPE_SUPER_MANAGE)]));
		}
		tlrTypeList.add(new TlrType(SystemConstant.TLR_NO_TYPE_MANAGE,
				SystemConstant.TLR_NO_TYPE_NAME[Integer
						.parseInt(SystemConstant.TLR_NO_TYPE_MANAGE)]));
		tlrTypeList.add(new TlrType(SystemConstant.TLR_NO_TYPE_NORMAL_USER,
				SystemConstant.TLR_NO_TYPE_NAME[Integer
						.parseInt(SystemConstant.TLR_NO_TYPE_NORMAL_USER)]));

		PageQueryResult pageQueryResult = new PageQueryResult();
		if (tlrTypeList != null && tlrTypeList.size() > 0) {
			pageQueryResult.setTotalCount(tlrTypeList.size());
		} else {
			pageQueryResult.setTotalCount(0);
		}
		pageQueryResult.setQueryResult(tlrTypeList);
		return pageQueryResult;
	}
}
