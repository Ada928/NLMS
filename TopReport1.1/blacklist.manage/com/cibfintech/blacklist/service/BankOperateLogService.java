package com.cibfintech.blacklist.service;

import java.util.List;

import resource.bean.blacklist.NsBankOperateLog;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

public class BankOperateLogService {

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static BankOperateLogService getInstance() {
		return (BankOperateLogService) ApplicationContextUtils.getBean(BankOperateLogService.class.getName());
	}

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(int pageIndex, int pageSize, String queryString) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		StringBuffer hql = new StringBuffer(" from NsBankOperateLog bblt where 1=1 " + queryString);

		try {
			queryCondition.setPageIndex(pageIndex);
			queryCondition.setPageSize(pageSize);
			queryCondition.setQueryString(hql.toString());
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
	public List getAllBankOperateLog() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			NsBankOperateLog bean = (NsBankOperateLog) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsBankOperateLog bean) {
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
	public void modOrAddEntity(NsBankOperateLog bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsBankOperateLog bean) throws CommonException {
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
			NsBankOperateLog bean = (NsBankOperateLog) rootDAO.query(NsBankOperateLog.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsBankOperateLog bean) {
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
	public NsBankOperateLog selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsBankOperateLog bean = null;
		try {
			bean = (NsBankOperateLog) rootdao.query(NsBankOperateLog.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
