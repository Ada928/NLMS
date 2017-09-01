/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.report.blacklist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import resource.bean.report.QueryDailyLogCount;
import resource.dao.base.HQLDAO;

import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.business.common.ErrorCode;
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
public class QueryDailyLogCountService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(QueryDailyLogCountService.class);

	/**
	 * get instance.
	 *
	 * @return
	 */
	public synchronized static QueryDailyLogCountService getInstance() {
		return (QueryDailyLogCountService) ApplicationContextUtils.getBean(QueryDailyLogCountService.class.getName());
	}

	public QueryDailyLogCountService() {
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void saveQueryDailyLogCount(String operateType, String brNo, String queryTable, String sumQueryRecord, Date countDay) throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		QueryDailyLogCount queryDailyLogCount = new QueryDailyLogCount();
		queryDailyLogCount.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		queryDailyLogCount.setBrNo(brNo);
		queryDailyLogCount.setOperateType(operateType);
		queryDailyLogCount.setQueryTable(queryTable);
		queryDailyLogCount.setSumQueryRecord(sumQueryRecord);
		queryDailyLogCount.setCountDay(countDay);
		queryDailyLogCount.setCreateDate(DateUtil.getCurrentDate());
		try {
			hqldao.getHibernateTemplate().save(queryDailyLogCount);
		} catch (Exception e) {
			logger.error("update(QueryDailyLogCount)", e);
			ExceptionUtil.throwCommonException(e.getMessage(), ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
	}

	public PageQueryResult queryQueryDailyLogCountDetail(int pageIndex, int pageSize, String qbrNo, String stdate, String endate) throws CommonException {
		StringBuffer sb = new StringBuffer("");
		List<Object> list = new ArrayList<Object>();
		// sb.append("select log from QueryDailyLogCount log where 1=1");
		sb.append("select cont from QueryDailyLogCount cont where 1=1 ");

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

		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();

		PageQueryCondition queryCondition = new PageQueryCondition();
		queryCondition.setQueryString(sb.toString());
		queryCondition.setPageIndex(pageIndex);
		queryCondition.setPageSize(pageSize);
		queryCondition.setObjArray(list.toArray());
		PageQueryResult pageQueryResult = hqldao.pageQueryByQL(queryCondition);
		return pageQueryResult;
	}
}
