package com.cibfintech.blacklist.bankblacklist.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cibfintech.blacklist.operation.BankBlackListOperation;
import com.cibfintech.view.pub.BankBlackListAuditStateView;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;

/*

 * 
 */
public class BankBlackListDEL extends BaseUpdate {

	private static final String DATASET_ID = "BankBlackListEdit";
	private static final String PARAM_ACTION = "op";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean multiUpdateResultBean, HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			// 返回对象
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			// 结果集对象
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
			String del = updateResultBean.getParameter(PARAM_ACTION);
			del = (null == del || "" == del) ? "" : del;
			// 更新对象
			List<BankBlackListAuditStateView> beans = new ArrayList<BankBlackListAuditStateView>();
			// Operation参数
			OperationContext context = new OperationContext();
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
			oc.setAttribute(BankBlackListOperation.CMD, BankBlackListOperation.CMD_DEL);
			oc.setAttribute(BankBlackListOperation.IN_DEL, del);
			oc.setAttribute(BankBlackListOperation.IN_BANK_BLACK_LISTS, beans);

			// call方式开启operation事务
			OPCaller.call(BankBlackListOperation.ID, oc);
			return updateReturnBean;
		} catch (AppException appe) {
			throw appe;
		} catch (Exception e) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage(), e);
		}
	}
}
