package com.cibfintech.blacklist.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import resource.bean.blacklist.NsPoliceBlackList;
import resource.bean.report.SysTaskInfo;
import resource.blacklist.dao.BlackListDAO;
import resource.blacklist.dao.BlackListDAOUtils;
import resource.dao.base.HQLDAO;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.management.common.DAOUtils;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.report.utils.ReportEnum;

public class PoliceBlackListService {

	public PageQueryResult list(int pageIndex, int pageSize, String hql) throws CommonException {
		PageQueryCondition queryCondition = new PageQueryCondition();
		queryCondition.setQueryString(hql);
		queryCondition.setPageIndex(pageIndex);
		queryCondition.setPageSize(pageSize);
		HQLDAO hqlDAO = DAOUtils.getHQLDAO();
		return hqlDAO.pageQueryByQL(queryCondition);
	}

	/*
	 * 获取一个实例
	 * 
	 * @param paramgroupId 参数段编号
	 */

	public static PoliceBlackListService getInstance() {
		return (PoliceBlackListService) ApplicationContextUtils.getBean("PoliceBlackListService");
	}

	public PageQueryResult pageQueryByHql(int pageIndex, int pageSize, String partyId, String qCertificateType, String qCertificateNumber, String qOperateState) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		StringBuffer hql = new StringBuffer(" from NsPoliceBlackList pblt where pblt.del='F'");

		if (StringUtils.isNotBlank(partyId)) {
			hql.append(" and pblt.id = '").append(partyId.trim()).append("'");
		}
		if (StringUtils.isNotBlank(qCertificateType)) {
			hql.append(" and pblt.certificateType = '").append(qCertificateType.trim()).append("'");
		}
		if (StringUtils.isNotBlank(qCertificateNumber)) {
			hql.append(" and pblt.certificateNumber like '%").append(qCertificateNumber.trim()).append("%'");
		}
		if (StringUtils.isNotBlank(qOperateState)) {
			hql.append(" and pblt.operateState='").append(qOperateState.trim()).append("'");
		} else {
			hql.append(" and pblt.operateState<>'").append(ReportEnum.REPORT_ST1.N.value).append("'");
		}
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
	public List getAllPoliceBlacklist() throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			NsPoliceBlackList bblt = (NsPoliceBlackList) list.get(i);
			list.set(i, bblt);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsPoliceBlackList policeBlackList) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.delete(policeBlackList);
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
	public void modOrAddEntity(NsPoliceBlackList policeBlackList) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(policeBlackList);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsPoliceBlackList policeBlackList) throws CommonException {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		if (isExists(policeBlackList.getId())) {
			ExceptionUtil.throwCommonException(" 名单重复");
		}
		try {
			rootDAO.save(policeBlackList);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体！ ");
		}
	}

	public boolean isExists(String id) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			NsPoliceBlackList policeBlackList = (NsPoliceBlackList) rootDAO.query(NsPoliceBlackList.class, id);
			if (policeBlackList == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsPoliceBlackList policeBlackList) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.update(policeBlackList);
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
	public NsPoliceBlackList selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsPoliceBlackList policeBlackList = null;
		try {
			policeBlackList = (NsPoliceBlackList) rootdao.query(NsPoliceBlackList.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return policeBlackList;
	}

}
