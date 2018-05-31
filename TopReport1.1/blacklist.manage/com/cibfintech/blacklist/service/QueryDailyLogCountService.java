/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.blacklist.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsQueryDailyLogCount;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;
import resource.dao.base.HQLDAO;

import com.cibfintech.blacklist.util.GenerateID;
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
	public void saveQueryDailyLogCount(String operateType, String brcode, String queryTable, String sumQueryRecord, Date countDay) throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		NsQueryDailyLogCount queryDailyLogCount = new NsQueryDailyLogCount();
		queryDailyLogCount.setId(String.valueOf(GenerateID.getId()));
		queryDailyLogCount.setBrcode(brcode);
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

	/*
	 * 查询
	 * 
	 * @param paramgroupId 参数段编号
	 */
	public List getAllRole() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			NsQueryDailyLogCount bean = (NsQueryDailyLogCount) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsQueryDailyLogCount bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.delete(bean);
			System.out.println("已删除");
		} catch (CommonException e) {
			System.out.println("删除实体出错！ ");
			e.printStackTrace();
		}
	}

	/*
	 * 插入或者更新实体
	 * 
	 * @param biNationregion
	 */
	public void modOrAddEntity(NsQueryDailyLogCount bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsQueryDailyLogCount bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getId().toString())) {
			ExceptionUtil.throwCommonException(" 角色信息重复");
		}
		try {
			rootDAO.save(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体！ ");
		}
	}

	public boolean isExists(String id) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			NsQueryDailyLogCount bean = (NsQueryDailyLogCount) rootDAO.query(NsQueryDailyLogCount.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsQueryDailyLogCount bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.update(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体出错！ ");
			e.printStackTrace();
		}
	}

	public void addTosystaskinfo(SysTaskInfo systackinfo) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(systackinfo);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}

	// 通过id来获取实体类
	public NsQueryDailyLogCount selectById(Integer id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsQueryDailyLogCount bean = null;
		try {
			bean = (NsQueryDailyLogCount) rootdao.query(NsQueryDailyLogCount.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
