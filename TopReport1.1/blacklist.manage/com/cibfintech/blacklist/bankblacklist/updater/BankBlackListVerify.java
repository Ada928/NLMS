package com.cibfintech.blacklist.bankblacklist.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cibfintech.blacklist.operation.BankBlackListOperation;
import com.cibfintech.view.pub.BankBlackListAuditStateView;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;

/**
 * @author huangcheng
 *
 */
public class BankBlackListVerify extends BaseUpdate {

	private static final String DATASET_ID = "BankBlackListVerify";
	private final static String PARAM_ACTION = "op";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws AppException {

		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();

		// 返回结果对象

		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
		String verify = updateResultBean.getParameter(PARAM_ACTION);
		verify = (null == verify || "" == verify) ? "" : verify;
		List<BankBlackListAuditStateView> beans = new ArrayList<BankBlackListAuditStateView>();
		while (updateResultBean.hasNext()) {
			BankBlackListAuditStateView bean = new BankBlackListAuditStateView();
			Map map = updateResultBean.next();
			String id = (String) map.get("id");
			String blacklistID = (String) map.get("blacklistid");
			bean.setId(id);
			bean.setBlacklistID(blacklistID);
			beans.add(bean);
		}

		OperationContext oc = new OperationContext();

		oc.setAttribute(BankBlackListOperation.CMD, BankBlackListOperation.CMD_VERIFY);
		oc.setAttribute(BankBlackListOperation.IN_VERIFY, verify);
		oc.setAttribute(BankBlackListOperation.IN_BANK_BLACK_LISTS, beans);
		// call方式开启operation事务
		OPCaller.call(BankBlackListOperation.ID, oc);

		return updateReturnBean;

	}

}
