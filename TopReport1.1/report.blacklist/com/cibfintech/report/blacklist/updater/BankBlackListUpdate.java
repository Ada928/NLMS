package com.cibfintech.report.blacklist.updater;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cibfintech.report.blacklist.operation.BankBlackListOperation;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;

import resource.bean.report.BankBlackList;

/**
 * @author huangcheng
 *
 */
public class BankBlackListUpdate extends BaseUpdate {

	private static final String DATASET_ID = "BankBlackList";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2)
			throws AppException {

		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();

		// 返回结果对象
		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);

		// 返回日牌价对象
		BankBlackList bankblacklist = new BankBlackList();

		OperationContext oc = new OperationContext();
		if (updateResultBean.hasNext()) {
			// 属性拷贝
			Map map = updateResultBean.next();
			BaseUpdate.mapToObject(bankblacklist, map);
			if (UpdateResultBean.MODIFY == updateResultBean.getRecodeState()) {
				oc.setAttribute(BankBlackListOperation.CMD, BankBlackListOperation.CMD_MOD);
			}
			if (UpdateResultBean.INSERT == updateResultBean.getRecodeState()) {
				oc.setAttribute(BankBlackListOperation.CMD, BankBlackListOperation.CMD_ADD);
			}
			oc.setAttribute(BankBlackListOperation.IN_PARAM, bankblacklist);
			// call方式开启operation事务
			OPCaller.call(BankBlackListOperation.ID, oc);
			return updateReturnBean;
		}

		return null;
	}

}
