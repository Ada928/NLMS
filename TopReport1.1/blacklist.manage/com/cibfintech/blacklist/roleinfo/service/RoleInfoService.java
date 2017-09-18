package com.cibfintech.blacklist.roleinfo.service;

import java.util.List;

import resource.bean.pub.RoleInfo;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

public class RoleInfoService {

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static RoleInfoService getInstance() {
		return (RoleInfoService) ApplicationContextUtils.getBean(RoleInfoService.class.getName());
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
			RoleInfo bean = (RoleInfo) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(RoleInfo bean) {
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
	public void modOrAddEntity(RoleInfo bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(RoleInfo bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getId())) {
			ExceptionUtil.throwCommonException(" 角色信息重复");
		}
		try {
			rootDAO.save(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体！ ");
		}
	}

	public boolean isExists(int id) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			RoleInfo bean = (RoleInfo) rootDAO.query(RoleInfo.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(RoleInfo bean) {
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
	public RoleInfo selectById(Integer id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		RoleInfo bean = null;
		try {
			bean = (RoleInfo) rootdao.query(RoleInfo.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
