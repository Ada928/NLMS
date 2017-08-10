package com.cibfintech.report.blacklist.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.huateng.ebank.business.common.PageQueryCondition;
import com.huateng.ebank.business.common.PageQueryResult;
import com.huateng.ebank.business.management.common.DAOUtils;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.ApplicationContextUtils;
import com.huateng.ebank.framework.util.ExceptionUtil;

import resource.bean.report.BankBlackList;
import resource.bean.report.SysTaskInfo;
import resource.dao.base.HQLDAO;
import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

public class BankBlackListService {

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

	public static BankBlackListService getInstance() {
		return (BankBlackListService) ApplicationContextUtils.getBean("BankBlackListService");
	}

	public PageQueryResult pageQueryByHql(int pageIndex, int pageSize, String partyId, String zjzl, String zjhm) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		PageQueryResult pageQueryResult = null;
		PageQueryCondition queryCondition = new PageQueryCondition();

		StringBuffer hql = new StringBuffer(" from BankBlackList bblt where 1=1 ");

		if (StringUtils.isNotBlank(partyId)) {
			hql.append(" and bblt.party_id like '%").append(partyId).append("%'");
		}
		if (StringUtils.isNotBlank(zjzl)) {
			hql.append(" and bblt.zjzl like '%").append(zjzl).append("%'");
		}
		if (StringUtils.isNotBlank(zjhm)) {
			hql.append(" and bblt.zjhm like '%").append(zjhm).append("%'");
		}

		hql.append(" order by bblt.party_id");

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
	public List getAllBankBlacklist() throws CommonException {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		List list = rootDAO.queryByQL2List("1=1");
		for (int i = 0; i < list.size(); i++) {
			BankBlackList bder = (BankBlackList) list.get(i);
			list.set(i, bder);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(BankBlackList bankBlacklist) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		try {
			rootDAO.delete(bankBlacklist);
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
	public void modOrAddEntity(BankBlackList bankBlacklist) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		try {
			rootDAO.saveOrUpdate(bankBlacklist);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(BankBlackList bankBlacklist) throws CommonException {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		if (isExists(bankBlacklist.getId())) {
			ExceptionUtil.throwCommonException(" 名单重复");
		}
		try {
			rootDAO.save(bankBlacklist);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体！ ");
		}
	}

	public boolean isExists(String id) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		try {
			BankBlackList bankBlacklist = (BankBlackList) rootDAO.query(BankBlackList.class, id);
			if (bankBlacklist == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(BankBlackList bankBlacklist) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		try {
			rootDAO.update(bankBlacklist);
			System.out.println(this.getClass().getName() + " 已插入或更新实体");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新实体出错！ ");
			e.printStackTrace();
		}
	}

	public void addTosystaskinfo(SysTaskInfo systackinfo) {
		ROOTDAO rootDAO = ROOTDAOUtils.getROOTDAO();
		try {
			rootDAO.saveOrUpdate(systackinfo);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}

	// author by 计翔 2012.9.5 通过id来获取实体类
	public BankBlackList selectById(String id) {
		ROOTDAO rootdao = ROOTDAOUtils.getROOTDAO();
		BankBlackList bankBlacklist = null;
		try {

			bankBlacklist = (BankBlackList) rootdao.query(BankBlackList.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bankBlacklist;
	}

}
