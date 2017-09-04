/**
 *
 */
package com.huateng.ebank.business.opermng.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.pub.RoleInfo;
import resource.bean.pub.TlrInfo;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.opermng.operation.OperMngOperation;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.report.dataquery.bean.TlrMngRelBean;

/**
 * @author zhiguo.zhao
 *
 */
public class OperMngModUpdate extends BaseUpdate {

	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean multiUpdateResultBean, HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {

			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("operMngMod");
			TlrInfo operator = null;
			while (updateResultBean.hasNext()) {
				operator = new TlrInfo();
				Map map = updateResultBean.next();
				mapToObject(operator, map);
			}

			List<TlrMngRelBean> tlrmng = new ArrayList<TlrMngRelBean>();
			UpdateResultBean roleUpdateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("operMngRoleInfo");
			List<RoleInfo> roles = new ArrayList<RoleInfo>();
			while (roleUpdateResultBean.hasNext()) {
				RoleInfo role = new RoleInfo();
				Map map = roleUpdateResultBean.next();
				String roleId = (String) map.get("roleId");
				String roleName = (String) map.get("roleName");
				role.setId(Integer.parseInt(roleId));
				role.setRoleName(roleName);
				roles.add(role);
			}

			String op = updateResultBean.getParameter("op");

			OperationContext oc = new OperationContext();
			oc.setAttribute(OperMngOperation.CMD, op);
			oc.setAttribute(OperMngOperation.IN_ROLELIST, roles);
			oc.setAttribute(OperMngOperation.IN_TLRLLIST, tlrmng);
			oc.setAttribute(OperMngOperation.IN_TLRINFO, operator);

			OPCaller.call(OperMngOperation.ID, oc);
			return updateReturnBean;
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

}
