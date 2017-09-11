package com.cibfintech.blacklist.bankinfo.updater;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.pub.Bctl;

import com.cibfintech.blacklist.operation.BankInfoOperation;
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
public class BankInfoUpdate extends BaseUpdate {

	private static final String DATASET_ID = "BankInfoManage";
	private final static String PARAM_ACTION = "op";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws AppException {
		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();
		// 返回结果对象
		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
		// 返回黑名单对象
		Bctl bean = new Bctl();
		OperationContext oc = new OperationContext();
		if (updateResultBean.hasNext()) {
			// 属性拷贝
			Map map = updateResultBean.next();
			BaseUpdate.mapToObject(bean, map);
			String op = updateResultBean.getParameter(PARAM_ACTION);
			op = (null == op || "" == op) ? "" : op;
			if (op.equals(BankInfoOperation.IN_EDIT)) {
				oc.setAttribute(BankInfoOperation.CMD, BankInfoOperation.CMD_EDIT);
			}
			if (op.equals(BankInfoOperation.IN_ADD)) {
				oc.setAttribute(BankInfoOperation.CMD, BankInfoOperation.CMD_ADD);
			}
			oc.setAttribute(BankInfoOperation.IN_PARAM, op);
			oc.setAttribute(BankInfoOperation.IN_BANK_INFO, bean);
			// call方式开启operation事务
			OPCaller.call(BankInfoOperation.ID, oc);
			return updateReturnBean;
		}
		return null;
	}
}
