package com.huateng.report.getter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import resource.bean.report.BopCfaCreditorDs;
import resource.bean.report.BopCfaExdebtDs;
import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.Result;
import com.huateng.commquery.result.ResultMng;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.report.common.ReportConstant;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.web.commQuery.BaseGetter;
import com.huateng.exception.AppException;
import com.huateng.report.bean.BopForDebtFeiPerSave;
import com.huateng.report.service.BopForDebtYinTuanService;

/**
 *
 * @author shishu.zhang
 *
 *         2012-8-15上午10:54:59
 */
@SuppressWarnings("unchecked")
public class BopForDebtFeiPerSaveOverGetter extends BaseGetter {

	@Override
	public Result call() throws AppException {
		try {
			PageQueryResult pageQueryResult = getData();

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "外债非居民个人存款补录-外债非居民个人存款余额信息查询");

			ResultMng.fillResultByList(getCommonQueryBean(), getCommQueryServletRequest(),
					pageQueryResult.getQueryResult(), getResult());
			result.setContent(pageQueryResult.getQueryResult());
			result.getPage().setTotalPage(pageQueryResult.getPageCount(getResult().getPage().getEveryPage()));
			result.init();
			return result;
		} catch (CommonException e) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage());
		} catch (AppException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, ex.getMessage(), ex);
		}
	}

	public PageQueryResult getData() throws IllegalAccessException, InvocationTargetException, AppException {
		int pageSize = this.getResult().getPage().getEveryPage();
		int pageIndex = this.getResult().getPage().getCurrentPage();
		Map map = getCommQueryServletRequest().getParameterMap();
		String op = (String) map.get("op");
		if (!DataFormat.isEmpty(op)) {
			List<BopForDebtFeiPerSave> list = new ArrayList<BopForDebtFeiPerSave>();
			PageQueryResult queryResult = new PageQueryResult();
			if (op.equals("new")) {
				// TODO
			} else {
				String id = (String) map.get("id");
				ROOTDAO rootdao = ROOTDAOUtils.getROOTDAO();
				BopCfaExdebtDs bopCfaExdebtDs = rootdao.query(BopCfaExdebtDs.class, id);

				BopForDebtFeiPerSave feiPerSaveBean = new BopForDebtFeiPerSave();
				BopCfaExdebtDs signCfaExdebtDs = (BopCfaExdebtDs) rootdao.query(BopCfaExdebtDs.class,
						bopCfaExdebtDs.getFiller1());
				String creHql = "from BopCfaCreditorDs model where model.recId ='" + signCfaExdebtDs.getId() + "'";
				BopCfaCreditorDs cfaCreditorDs = (BopCfaCreditorDs) rootdao.queryByQL2List(creHql).get(0);
				// BeanUtils.copyProperties(feiPerSaveBean, cfaCreditorDs);
				// //先拷贝债权人不然会覆盖主信息中的id
				BeanUtils.copyProperties(feiPerSaveBean, bopCfaExdebtDs);
				feiPerSaveBean.setRecId(cfaCreditorDs.getRecId());
				feiPerSaveBean.setCreditorca(cfaCreditorDs.getCreditorca());
				feiPerSaveBean.setCreditorcode(cfaCreditorDs.getCreditorcode()); // 债权人代码
				feiPerSaveBean.setCreditorname(cfaCreditorDs.getCreditorname());// 债权人中文名称
				feiPerSaveBean.setCreditornamen(cfaCreditorDs.getCreditornamen());// 债权人英文名称
				feiPerSaveBean.setCreditortype(cfaCreditorDs.getCreditortype());// 债权人类型代码
				feiPerSaveBean.setCrehqcode(cfaCreditorDs.getCrehqcode());// 债权人总部所在国家（地区）代码
				feiPerSaveBean.setOpercode(cfaCreditorDs.getOpercode());// 债权人经营地所在国家（地区）代码
				feiPerSaveBean.setDebtype(signCfaExdebtDs.getDebtype());
				feiPerSaveBean.setDebtorcode(signCfaExdebtDs.getDebtorcode());
				feiPerSaveBean.setLimitType(signCfaExdebtDs.getLimitType());
				feiPerSaveBean.setValuedate(signCfaExdebtDs.getValuedate());
				feiPerSaveBean.setContractcurr(signCfaExdebtDs.getContractcurr());
				feiPerSaveBean.setFloatrate(signCfaExdebtDs.getFloatrate());
				feiPerSaveBean.setAnninrate(signCfaExdebtDs.getAnninrate());
				feiPerSaveBean.setSpapfeboindex(signCfaExdebtDs.getSpapfeboindex());
				feiPerSaveBean.setDebtyperema(signCfaExdebtDs.getRemark());
				list.add(feiPerSaveBean);
			}
			queryResult.setQueryResult(list);
			// 页面接收判断
			getCommQueryServletRequest().setParameter("op", op);
			return queryResult;
		} else {

			this.setValue2DataBus(ReportConstant.QUERY_LOG_BUSI_NAME, "外债-非居民个人存款补录-变动信息查询");

			String qstartdate = (String) map.get("startdate");
			String qenddate = (String) map.get("enddate");
			String qactiontype = (String) map.get("qactiontype");
			String qapproveStatus = (String) map.get("qapproveStatus");
			String qrepStatus = (String) map.get("qrepStatus");

			String qRecStatus = (String) map.get("qRecStatus");
			String filler2 = (String) map.get("filler2");

			BopForDebtYinTuanService debtYinTuanService = BopForDebtYinTuanService.getInstance();
			return debtYinTuanService.queryRecordFeiPerSave("over", pageIndex, pageSize, qstartdate, qenddate,
					qactiontype, qapproveStatus, qrepStatus, qRecStatus, filler2);
		}
	}
}
