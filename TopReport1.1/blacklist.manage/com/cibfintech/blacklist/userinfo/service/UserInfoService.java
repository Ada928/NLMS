package com.cibfintech.blacklist.userinfo.service;

import java.util.List;

import resource.bean.pub.TlrInfo;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

public class UserInfoService {

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static UserInfoService getInstance() {
		return (UserInfoService) ApplicationContextUtils.getBean(UserInfoService.class.getName());
	}

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(int pageSize, int pageIndex, String hql, List list) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		try {
			queryCondition.setQueryString(hql.toString());
			queryCondition.setPageIndex(pageIndex);
			queryCondition.setPageSize(pageSize);
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
	public List getAllUser() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			TlrInfo bean = (TlrInfo) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 查询
	 * 
	 * @param paramgroupId 参数段编号
	 */
	public List queryUserByHql(String hql) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List(hql);
		for (int i = 0; i < list.size(); i++) {
			TlrInfo bean = (TlrInfo) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(TlrInfo bean) {
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
	public void modOrAddEntity(TlrInfo bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(TlrInfo bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getBrcode())) {
			ExceptionUtil.throwCommonException(" 银行信息重复");
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
			TlrInfo bean = (TlrInfo) rootDAO.query(TlrInfo.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(TlrInfo bean) {
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
	public TlrInfo selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		TlrInfo bean = null;
		try {
			bean = (TlrInfo) rootdao.query(TlrInfo.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
