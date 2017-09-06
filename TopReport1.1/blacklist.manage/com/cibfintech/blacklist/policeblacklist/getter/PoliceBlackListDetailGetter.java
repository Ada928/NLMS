package com.cibfintech.blacklist.policeblacklist.getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import resource.bean.blacklist.NsPoliceBlackList;
import resource.bean.report.SysTaskInfo;
import resource.bean.report.SysTaskLog;

import com.cibfintech.blacklist.bean.PoliceBlackListDetail;
import com.cibfintech.blacklist.policeblacklist.service.PoliceBlackListService;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.report.common.service.ReportShowDetailService;
import com.huateng.report.utils.ReportTaskUtil;

/**
 *
 * author by getter
 */
public class PoliceBlackListDetailGetter extends BaseGetter {
	public Result call() throws AppException {
		String action = this.getCommQueryServletRequest().getParameter("action");
		String ost = this.getCommQueryServletRequest().getParameter("osta");
		String id = this.getCommQueryServletRequest().getParameter("id");
		String flag = this.getCommQueryServletRequest().getParameter("flag");
		String tskId = this.getCommQueryServletRequest().getParameter("tskId");
		List list = new ArrayList();

		try {
			if ("detail".equals(action)) {
				// 新bean从审计任务中获取
				ReportTaskUtil rt = new ReportTaskUtil();
				if ("0".equals(flag)) {
					Iterator it = ReportShowDetailService.getInstance().selectByKey(id);
					PoliceBlackListDetail ber = new PoliceBlackListDetail();
					NsPoliceBlackList oldbean = (NsPoliceBlackList) PoliceBlackListService.getInstance().selectById(id);
					ber.setOld_policeBlackList(oldbean);
					NsPoliceBlackList newBean = null;
					Class cls = null;
					while (it.hasNext()) {
						SysTaskInfo tem = (SysTaskInfo) it.next();
						Object temp = rt.getObjctBySysTaskInfo(tem);
						cls = temp.getClass();
						if (cls.equals(NsPoliceBlackList.class)) {
							newBean = (NsPoliceBlackList) temp;
							ber.setPoliceBlackList(newBean);
						}
					}
					list.add(ber);
				} else if ("1".equals(flag)) {
					SysTaskLog systasklog = ReportShowDetailService.getInstance().selectTaskLog(tskId);
					NsPoliceBlackList oldValue = null;
					NsPoliceBlackList newValue = null;
					PoliceBlackListDetail bimonth = new PoliceBlackListDetail();

					if (systasklog.getOldVal1() != null) {
						oldValue = (NsPoliceBlackList) rt.getOldObjectByTaskLog(systasklog);
						// bimonth.setOld_bimonthexchangerate(oldValue);
					}
					if (systasklog.getNewVal1() != null) {
						newValue = (NsPoliceBlackList) rt.getNewObjectByTaskLog(systasklog);
						// bimonth.setBimonthexchangerate(newValue);
					}
					// 新增的时候
					if (ost.equals("1")) {
						bimonth.setOld_policeBlackList(newValue);
					}
					// 修改的时候
					else if (ost.equals("2")) {
						bimonth.setPoliceBlackList(newValue);
						bimonth.setOld_policeBlackList(oldValue);
					}
					// 删除的时候
					else if (ost.equals("3")) {
						bimonth.setOld_policeBlackList(oldValue);
					}
					list.add(bimonth);
				}
			}
			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(), list, getResult());
			getResult().setContent(list);
			getResult().getPage().setTotalPage(1);
			getResult().init();
			return getResult();
		} catch (CommonException e) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}

	}
}
