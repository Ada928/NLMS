/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.report.blacklist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import resource.bean.report.PoliceBLOperateLog;
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
public class PoliceBlackListOperateLogService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PoliceBlackListOperateLogService.class);

	/**
	 * get instance.
	 *
	 * @return
	 */
	public synchronized static PoliceBlackListOperateLogService getInstance() {
		return (PoliceBlackListOperateLogService) ApplicationContextUtils
				.getBean(PoliceBlackListOperateLogService.class.getName());
	}

	public PoliceBlackListOperateLogService() {
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void savePoliceBLOperateLog(String operateType, String queryType,
			String queryNum, String measssage) throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		PoliceBLOperateLog policeBLOperateLog = new PoliceBLOperateLog();
		policeBLOperateLog.setId(UUID.randomUUID().toString()
				.replaceAll("-", "").toUpperCase());
		policeBLOperateLog.setBrNo(gi.getBrno());
		policeBLOperateLog.setTlrNo(gi.getTlrno());
		policeBLOperateLog.setTlrIP(gi.getIp());
		policeBLOperateLog.setOperateType(operateType);
		policeBLOperateLog.setQueryType(queryType);
		policeBLOperateLog.setQueryRecordNumber(queryNum);
		policeBLOperateLog.setCreateDate(DateUtil.getCurrentDate());
		policeBLOperateLog.setMessage(measssage);
		try {
			hqldao.getHibernateTemplate().save(policeBLOperateLog);
		} catch (Exception e) {
			logger.error("update(PoliceBlackListOperateLog)", e);
			ExceptionUtil.throwCommonException(e.getMessage(),
					ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
	}

	public PageQueryResult queryPoliceBLOperateLogDetail(int pageIndex,
			int pageSize, String qtlrNo, String qtlrIP, String qbrNo,
			String stdate, String endate) throws CommonException {
		StringBuffer sb = new StringBuffer("");
		List<Object> list = new ArrayList<Object>();
		// sb.append("select log from PoliceBLOperateLog log where 1=1");
		sb.append("select log from PoliceBLOperateLog log where 1=1 ");
		if (!DataFormat.isEmpty(qtlrNo)) {
			sb.append(" and  log.tlrNo= ? ");
			list.add(qtlrNo);
		}
		if (!DataFormat.isEmpty(qtlrIP)) {
			sb.append(" and  log.tlrIP= ? ");
			list.add(qtlrIP);
		}
		if (!DataFormat.isEmpty(qbrNo)) {
			sb.append(" and log.brNo = ? ");
			list.add(qbrNo);
		}

		if (!DataFormat.isEmpty(stdate)) {
			sb.append(" and log.createDate>=? ");
			list.add(DateUtil.stringToDate2(stdate));
		}
		if (!DataFormat.isEmpty(endate)) {
			sb.append(" and log.createDate<? ");
			list.add(DateUtil.getStartDateByDays(
					DateUtil.stringToDate2(endate), -1));
		}
		sb.append(" order by log.brNo");

		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();

		PageQueryCondition queryCondition = new PageQueryCondition();
		queryCondition.setQueryString(sb.toString());
		queryCondition.setPageIndex(pageIndex);
		queryCondition.setPageSize(pageSize);
		queryCondition.setObjArray(list.toArray());
		PageQueryResult pageQueryResult = hqldao.pageQueryByQL(queryCondition);
		return pageQueryResult;
	}

	/*
	 * 查询公安部黑名单操作日志中的操作状态为查询的，商行标识号，查询总数 的记录 并且设定操作时间区间 以 商行标识号分组
	 * 
	 * @param startDate 开始时间
	 * 
	 * @param endDate 结束时间
	 */
	public List sumQueryPoliceBlacklist(String startDate, String endDate)
			throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		StringBuffer sb = new StringBuffer(
				"select log.brNo, sum(log.queryRecordNumber) from PoliceBLOperateLog log where 1=1");
		sb.append(" and log.operateType='Q'");
		sb.append(" and log.createDate>=to_date('").append(startDate)
				.append("','yyyy-mm-dd')");
		sb.append(" and log.createDate<to_date('").append(endDate)
				.append("','yyyy-mm-dd')");
		sb.append(" group by log.brNo");
		List list = hqldao.queryByQL2List(sb.toString());
		return list;
	}
}
