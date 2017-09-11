package com.cibfintech.blacklist.roleinfo.service;

import java.util.List;

import resource.bean.pub.RoleFuncRel;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

public class RoleFuncRelService {

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static RoleFuncRelService getInstance() {
		return (RoleFuncRelService) ApplicationContextUtils.getBean(RoleFuncRelService.class.getName());
	}

	@SuppressWarnings("unchecked")
	public PageQueryResult pageQueryByHql(int pageIndex, int pageSize, String hql, List list) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		// StringBuffer hql = new
		// StringBuffer(" from RoleFuncRel po where 1=1");

		/*
		 * if (StringUtils.isNotBlank(partyId)) {
		 * hql.append(" and bblt.id = '").append(partyId.trim()).append("'"); }
		 * if (StringUtils.isNotBlank(qCertificateType)) {
		 * hql.append(" and bblt.certificateType = '"
		 * ).append(qCertificateType.trim()).append("'"); } if
		 * (StringUtils.isNotBlank(qCertificateNumber)) {
		 * hql.append(" and bblt.certificateNumber like '%"
		 * ).append(qCertificateNumber.trim()).append("%'"); } if
		 * (!isSuperManager) {
		 * hql.append(" and bblt.bankCode = '").append(globalinfo
		 * .getBrcode()).append("'"); }
		 * hql.append(" and bblt.operateState in ").append(operateStates);
		 */

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
	public List getAllRoleFuncRel() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			RoleFuncRel bean = (RoleFuncRel) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 查询
	 * 
	 * @param paramgroupId 参数段编号
	 */
	public List<RoleFuncRel> getRelByRoleId(Integer roleId) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List<RoleFuncRel> list = rootDAO.queryByQL2List("from RoleFuncRel po where 1=1 and po.roleId=" + roleId);
		for (int i = 0; i < list.size(); i++) {
			RoleFuncRel bean = (RoleFuncRel) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(RoleFuncRel bean) {
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
	public void modOrAddEntity(RoleFuncRel bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(RoleFuncRel bean) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(bean.getId())) {
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
			RoleFuncRel bean = (RoleFuncRel) rootDAO.query(RoleFuncRel.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(RoleFuncRel bean) {
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
	public RoleFuncRel selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		RoleFuncRel bean = null;
		try {
			bean = (RoleFuncRel) rootdao.query(RoleFuncRel.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
