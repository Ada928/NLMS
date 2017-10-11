/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.blacklist.policeblacklist.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsPoliceBLOperateLog;
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
public class PoliceBlackListOperateLogService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PoliceBlackListOperateLogService.class);

	/**
	 * get instance.
	 *
	 * @return
	 */
	public synchronized static PoliceBlackListOperateLogService getInstance() {
		return (PoliceBlackListOperateLogService) ApplicationContextUtils.getBean(PoliceBlackListOperateLogService.class.getName());
	}

	public PoliceBlackListOperateLogService() {
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void savePoliceBLOperateLog(String operateType, String queryType, String queryNum, String measssage) throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		NsPoliceBLOperateLog policeBLOperateLog = new NsPoliceBLOperateLog();
		policeBLOperateLog.setId(String.valueOf(GenerateID.getId()));
		policeBLOperateLog.setBrcode(gi.getBrcode());
		policeBLOperateLog.setTlrno(gi.getTlrno());
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
			ExceptionUtil.throwCommonException(e.getMessage(), ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
	}

	/*
	 * 查询公安部黑名单操作日志中的操作状态为查询的，商行标识号，查询总数 的记录 并且设定操作时间区间 以 商行标识号分组
	 * 
	 * @param startDate 开始时间
	 * 
	 * @param endDate 结束时间
	 */
	public List sumQueryPoliceBlacklist(Date startDate, Date endDate) throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		StringBuffer sb = new StringBuffer("select log.brcode, sum(log.queryRecordNumber) from NsPoliceBLOperateLog log where 1=1");
		sb.append(" and log.operateType='Q'");
		sb.append(" and log.createDate>=to_date('").append(DateUtil.dateToString(startDate)).append("','yyyy-mm-dd')");
		sb.append(" and log.createDate<to_date('").append(DateUtil.dateToString(endDate)).append("','yyyy-mm-dd')");
		sb.append(" group by log.brcode");
		List list = hqldao.queryByQL2List(sb.toString());
		return list;
	}

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(int pageIndex, int pageSize, String sb, List list) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		try {
			queryCondition.setPageIndex(pageIndex);
			queryCondition.setPageSize(pageSize);
			queryCondition.setQueryString(sb.toString());
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
	public List getAllBlackListOperateLog() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			NsPoliceBLOperateLog bean = (NsPoliceBLOperateLog) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsPoliceBLOperateLog bean) {
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
	public void modOrAddEntity(NsPoliceBLOperateLog bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsPoliceBLOperateLog bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getId())) {
			ExceptionUtil.throwCommonException(" 银行操作信息重复");
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
			NsPoliceBLOperateLog bean = (NsPoliceBLOperateLog) rootDAO.query(NsPoliceBLOperateLog.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsPoliceBLOperateLog bean) {
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
	public NsPoliceBLOperateLog selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsPoliceBLOperateLog bean = null;
		try {
			bean = (NsPoliceBLOperateLog) rootdao.query(NsPoliceBLOperateLog.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
