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

import resource.bean.blacklist.NsQueryMonthlyLogCount;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;
import resource.dao.base.HQLDAO;

import com.cibfintech.blacklist.util.GenerateID;
import com.huateng.ebank.business.common.BaseDAOUtils;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.GlobalInfo;
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
		queryMonthlyLogCount.setId(String.valueOf(GenerateID.getId()));
		queryMonthlyLogCount.setBrcode(gi.getBrno());
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
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsQueryMonthlyLogCount bean) {
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
	public void modOrAddEntity(NsQueryMonthlyLogCount bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsQueryMonthlyLogCount bean) throws CommonException {
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
			NsQueryMonthlyLogCount bean = (NsQueryMonthlyLogCount) rootDAO.query(NsQueryMonthlyLogCount.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsQueryMonthlyLogCount bean) {
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
	public NsQueryMonthlyLogCount selectById(Integer id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsQueryMonthlyLogCount bean = null;
		try {
			bean = (NsQueryMonthlyLogCount) rootdao.query(NsQueryMonthlyLogCount.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
