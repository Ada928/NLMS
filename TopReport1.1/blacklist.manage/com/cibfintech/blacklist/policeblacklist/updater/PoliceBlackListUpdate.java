package com.cibfintech.blacklist.policeblacklist.updater;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resource.bean.blacklist.NsPoliceBlackList;

import com.cibfintech.blacklist.operation.PoliceBlackListOperation;
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
public class PoliceBlackListUpdate extends BaseUpdate {

	private static final String DATASET_ID = "PoliceBlackListManage";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws AppException {

		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();

		// 返回结果对象
		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);

		// 返回日牌价对象
		NsPoliceBlackList policeblacklist = new NsPoliceBlackList();

		OperationContext oc = new OperationContext();
		if (updateResultBean.hasNext()) {
			// 属性拷贝
			Map map = updateResultBean.next();
			BaseUpdate.mapToObject(policeblacklist, map);
			if (UpdateResultBean.MODIFY == updateResultBean.getRecodeState()) {
				oc.setAttribute(PoliceBlackListOperation.CMD, PoliceBlackListOperation.CMD_MOD);
			}
			if (UpdateResultBean.INSERT == updateResultBean.getRecodeState()) {
				oc.setAttribute(PoliceBlackListOperation.CMD, PoliceBlackListOperation.CMD_ADD);
			}
			oc.setAttribute(PoliceBlackListOperation.IN_PARAM, policeblacklist);
			// call方式开启operation事务
			OPCaller.call(PoliceBlackListOperation.ID, oc);
			return updateReturnBean;
		}

		return null;
	}

}
