package com.cibfintech.blacklist.roleinfo.service;

import java.util.List;

import resource.bean.pub.TlrRoleRel;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;

import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

public class RoleTlrRelService {

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static RoleTlrRelService getInstance() {
		return (RoleTlrRelService) ApplicationContextUtils.getBean(RoleTlrRelService.class.getName());
	}

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(GlobalInfo globalinfo, int pageIndex, int pageSize, String hql, List list) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();
		// StringBuffer hql = new
		// StringBuffer(" from TlrRoleRel bblt where 1=1");

		// if (StringUtils.isNotBlank(tlrno)) {
		// hql.append(" and bblt.tlrno = '").append(tlrno.trim()).append("'");
		// }

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
	public List getAllTlrRoleRel() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			TlrRoleRel bean = (TlrRoleRel) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 查询
	 * 
	 * @param paramgroupId 参数段编号
	 */
	@SuppressWarnings("unchecked")
	public List<TlrRoleRel> getTlrRoleRelByTlrno(String tlrno) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List<TlrRoleRel> list = rootDAO.queryByQL2List(" from TlrRoleRel bblt where 1=1 and bblt.tlrno=" + tlrno);
		for (int i = 0; i < list.size(); i++) {
			TlrRoleRel bean = (TlrRoleRel) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 查询
	 * 
	 * @param paramgroupId 参数段编号
	 */
	@SuppressWarnings("unchecked")
	public List<TlrRoleRel> getTlrRoleRelByRoleId(String roleId) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List<TlrRoleRel> list = rootDAO.queryByQL2List(" from TlrRoleRel bblt where 1=1 and bblt.roleId=" + roleId);
		for (int i = 0; i < list.size(); i++) {
			TlrRoleRel bean = (TlrRoleRel) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(TlrRoleRel bean) {
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
	public void modOrAddEntity(TlrRoleRel bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(TlrRoleRel bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getId().toString())) {
			ExceptionUtil.throwCommonException(" 银行用户关系信息重复");
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
			TlrRoleRel bean = (TlrRoleRel) rootDAO.query(TlrRoleRel.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(TlrRoleRel bean) {
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
	public TlrRoleRel selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		TlrRoleRel bean = null;
		try {
			bean = (TlrRoleRel) rootdao.query(TlrRoleRel.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
