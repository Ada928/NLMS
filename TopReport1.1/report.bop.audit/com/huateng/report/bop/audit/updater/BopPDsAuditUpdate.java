package com.huateng.report.bop.audit.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.report.MtsBopCkpDs;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.report.bop.collection.operation.BopCkpDsOperation;

public class BopPDsAuditUpdate extends BaseUpdate {

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean multiUpdateResultBean, HttpServletRequest request,
			HttpServletResponse respone) throws AppException {

		try {
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID("BopPDsAudit");
			List<MtsBopCkpDs> mtsBopCkpDsList = new ArrayList<MtsBopCkpDs>();
			while (updateResultBean.hasNext()) {
				MtsBopCkpDs mtsBopDrDs = new MtsBopCkpDs();
				Map map = updateResultBean.next();
				mapToObject(mtsBopDrDs, map);
				mtsBopCkpDsList.add(mtsBopDrDs);
			}
			String approveStatusChoose = updateResultBean.getParameter("approveStatusChoose");
			String approveResultChoose = updateResultBean.getParameter("approveResultChoose");

			OperationContext oc = new OperationContext();
			oc.setAttribute(BopCkpDsOperation.CMD, BopCkpDsOperation.OP_P_AUDIT);
			oc.setAttribute(BopCkpDsOperation.IN_AUDIT_LIST, mtsBopCkpDsList);
			oc.setAttribute(BopCkpDsOperation.IN_AUDIT_STATUS, approveStatusChoose);
			oc.setAttribute(BopCkpDsOperation.IN_AUDIT_RESULT, approveResultChoose);
			OPCaller.call(BopCkpDsOperation.ID, oc);

			return updateReturnBean;
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

}
