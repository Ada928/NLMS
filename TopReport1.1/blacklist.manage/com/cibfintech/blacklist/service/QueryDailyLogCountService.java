/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.blacklist.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsQueryDailyLogCount;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;
import resource.dao.base.HQLDAO;

import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
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
		NsQueryDailyLogCount queryDailyLogCount = new NsQueryDailyLogCount();
		queryDailyLogCount.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		queryDailyLogCount.setBrcode(brNo);
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

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(int pageSize, int pageIndex, String hql, List list) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		try {
			queryCondition.setPageIndex(pageIndex);
			queryCondition.setPageSize(pageSize);
			queryCondition.setQueryString(hql.toString());
			queryCondition.setObjArray(list.toArray());
			pageQueryResult = rootDAO.pageQueryByQL(queryCondition);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return pageQueryResult;
	}
}
