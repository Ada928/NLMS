package com.cibfintech.blacklist.roleinfo.updater;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.pub.RoleInfo;

import com.cibfintech.blacklist.operation.RoleInfoOperation;
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
public class RoleInfoUpdate extends BaseUpdate {

	private static final String DATASET_ID = "RoleInfoManage";
	private final static String PARAM_ACTION = "op";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws AppException {
		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();
		// 返回结果对象
		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
		// 返回黑名单对象
		RoleInfo bean = new RoleInfo();
		OperationContext oc = new OperationContext();
		if (updateResultBean.hasNext()) {
			// 属性拷贝
			Map map = updateResultBean.next();
			BaseUpdate.mapToObject(bean, map);
			String op = updateResultBean.getParameter(PARAM_ACTION);
			op = (null == op || "" == op) ? "" : op;
			if (op.equals(RoleInfoOperation.IN_EDIT)) {
				oc.setAttribute(RoleInfoOperation.CMD, RoleInfoOperation.CMD_EDIT);
			}
			if (op.equals(RoleInfoOperation.IN_ADD)) {
				oc.setAttribute(RoleInfoOperation.CMD, RoleInfoOperation.CMD_ADD);
			}
			oc.setAttribute(RoleInfoOperation.IN_PARAM, op);
			oc.setAttribute(RoleInfoOperation.IN_ROLE_INFO, bean);
			// call方式开启operation事务
			OPCaller.call(RoleInfoOperation.ID, oc);
			return updateReturnBean;
		}
		return null;
	}
}
