package com.cibfintech.blacklist.userinfo.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.pub.RoleInfo;
import resource.bean.pub.TlrInfo;

import com.cibfintech.blacklist.operation.UserInfoOperation;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
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
public class UserInfoUpdate extends BaseUpdate {

	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean multiUpdateResultBean, HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {

			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("UserInfoManage");
			TlrInfo bean = null;
			while (updateResultBean.hasNext()) {
				bean = new TlrInfo();
				Map map = updateResultBean.next();
				mapToObject(bean, map);
			}

			UpdateResultBean roleUpdateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("UserRoleRelSelect");
			List<RoleInfo> roles = new ArrayList<RoleInfo>();
			while (roleUpdateResultBean.hasNext()) {
				RoleInfo role = new RoleInfo();
				Map map = roleUpdateResultBean.next();
				String isSelect = (String) map.get("select");
				if (Boolean.parseBoolean(isSelect)) {
					String roleId = (String) map.get("roleId");
					String roleName = (String) map.get("roleName");
					role.setId(Integer.parseInt(roleId));
					role.setRoleName(roleName);
					roles.add(role);
				}
			}

			String op = updateResultBean.getParameter("op");

			OperationContext oc = new OperationContext();
			oc.setAttribute(UserInfoOperation.CMD, op);
			oc.setAttribute(UserInfoOperation.IN_ROLE_LIST, roles);
			oc.setAttribute(UserInfoOperation.IN_USER_INFO, bean);

			OPCaller.call(UserInfoOperation.ID, oc);

			updateReturnBean.setParameter("SuccessInfo", UserInfoOperation.getSuccessInfo());
			return updateReturnBean;
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}
}
