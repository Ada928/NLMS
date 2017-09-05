/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.blacklist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsQueryMonthlyLogCount;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;
import resource.dao.base.HQLDAO;

import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.ebank.framework.util.ExceptionUtil;

/**
 * @author Administrator
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryMonthlyLogCountService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(QueryMonthlyLogCountService.class);

	/**
	 * get instance.
	 *
	 * @return
	 */
	public synchronized static QueryMonthlyLogCountService getInstance() {
		return (QueryMonthlyLogCountService) ApplicationContextUtils.getBean(QueryMonthlyLogCountService.class.getName());
	}

	public QueryMonthlyLogCountService() {
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void saveQueryMonthlyLogCount(String operateType, String queryTable, String sumQueryRecord, String measssage, Date countMonth)
			throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		NsQueryMonthlyLogCount queryMonthlyLogCount = new NsQueryMonthlyLogCount();
		queryMonthlyLogCount.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		queryMonthlyLogCount.setBrNo(gi.getBrno());
		queryMonthlyLogCount.setOperateType(operateType);
		queryMonthlyLogCount.setQueryTable(queryTable);
		queryMonthlyLogCount.setSumQueryRecord(sumQueryRecord);
		queryMonthlyLogCount.setCountMonth(countMonth);
		queryMonthlyLogCount.setCreateDate(DateUtil.getCurrentDate());
		try {
			hqldao.getHibernateTemplate().save(queryMonthlyLogCount);
		} catch (Exception e) {
			logger.error("update(QueryMonthlyLogCount)", e);
			ExceptionUtil.throwCommonException(e.getMessage(), ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
	}

	public PageQueryResult queryQueryMonthlyLogCountDetail(int pageIndex, int pageSize, String qbrNo, String stdate, String endate) throws CommonException {
		StringBuffer sb = new StringBuffer("");
		List<Object> list = new ArrayList<Object>();
		// sb.append("select log from QueryMonthlyLogCount log where 1=1");
		sb.append("select cont from NsQueryMonthlyLogCount cont where 1=1 ");

		if (!DataFormat.isEmpty(qbrNo)) {
			sb.append(" and cont.brNo = ? ");
			list.add(qbrNo);
		}

		if (!DataFormat.isEmpty(stdate)) {
			sb.append(" and cont.countDate>=? ");
			list.add(DateUtil.stringToDate2(stdate));
		}
		if (!DataFormat.isEmpty(endate)) {
			sb.append(" and cont.countDate<? ");
			list.add(DateUtil.getStartDateByDays(DateUtil.stringToDate2(endate), -1));
		}
		sb.append(" order by cont.brNo");

		// HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();

		PageQueryCondition queryCondition = new PageQueryCondition();
		queryCondition.setQueryString(sb.toString());
		queryCondition.setPageIndex(pageIndex);
		queryCondition.setPageSize(pageSize);
		queryCondition.setObjArray(list.toArray());
		PageQueryResult pageQueryResult = rootDAO.pageQueryByQL(queryCondition);
		return pageQueryResult;
	}
}
