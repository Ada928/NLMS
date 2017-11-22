/*
 * Created on 2017-08-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cibfintech.blacklist.service;

import java.util.List;

import org.apache.log4j.Logger;

import resource.bean.blacklist.NsBlackListSocketQueryLog;
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
public class BlackListSocketQueryLogService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BlackListSocketQueryLogService.class);

	/**
	 * get instance.
	 *
	 * @return
	 */
	public synchronized static BlackListSocketQueryLogService getInstance() {
		return (BlackListSocketQueryLogService) ApplicationContextUtils.getBean(BlackListSocketQueryLogService.class.getName());
	}

	public BlackListSocketQueryLogService() {
	}
	@SuppressWarnings({ "deprecation" })
	public void saveBlackListSocketQueryLogs(String sysno, String seqno, String accountCode, String certificateNumber, String clientName, String cardBkBookNo)
			throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		//GlobalInfo gi = GlobalInfo.getCurrentInstance();
		//插入表REPORT.NLMS_BLACKLIST_SOCK_QUERY_LOG
		NsBlackListSocketQueryLog bean = new NsBlackListSocketQueryLog();
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setAccountCode(accountCode);
		bean.setCertificateNumber(certificateNumber);
		bean.setSysno(sysno);
		bean.setSeqno(seqno);
		bean.setCardBkBookNo(cardBkBookNo);
		bean.setClientName(clientName);
		bean.setCreateDate(DateUtil.getCurrentDate());
		try {
			hqldao.getHibernateTemplate().save(bean);
		} catch (Exception e) {
			logger.error("update(saveBlackListSocketQueryLog)", e);
			ExceptionUtil.throwCommonException(e.getMessage(), ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
	}
	@SuppressWarnings({ "deprecation", "unused" })
	public void saveBlackListSocketQueryLog(String sysno, String tranCode, String seqno, String accountCode, String certificateType, String certificateNumber)
			throws CommonException {
		HQLDAO hqldao = BaseDAOUtils.getHQLDAO();
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		NsBlackListSocketQueryLog bean = new NsBlackListSocketQueryLog();
		bean.setId(String.valueOf(GenerateID.getId()));
		bean.setAccountCode(accountCode);
		bean.setCertificateNumber(certificateNumber);
		bean.setCertificateType(certificateType);
		bean.setSysno(sysno);
		bean.setSeqno(seqno);
		bean.setTranCode(tranCode);
		bean.setCreateDate(DateUtil.getCurrentDate());
		try {
			hqldao.getHibernateTemplate().save(bean);
		} catch (Exception e) {
			logger.error("update(saveBlackListSocketQueryLog)", e);
			ExceptionUtil.throwCommonException(e.getMessage(), ErrorCode.ERROR_CODE_TLR_INFO_INSERT, e);
		}
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
			NsBlackListSocketQueryLog bean = (NsBlackListSocketQueryLog) list.get(i);
			list.set(i, bean);
		}
		return list;
	}

	/*
	 * 删除实体
	 * 
	 * @param biNationregion
	 */
	public void removeEntity(NsBlackListSocketQueryLog bean) {
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
	public void modOrAddEntity(NsBlackListSocketQueryLog bean) {
		BlackListDAO rootDAO = BlackListDAOUtils.getBlackListDAO();
		try {
			rootDAO.saveOrUpdate(bean);
			System.out.println(this.getClass().getName() + " 已插入或更新");
		} catch (CommonException e) {
			System.out.println(this.getClass().getName() + " 插入或更新出错！ ");
			e.printStackTrace();
		}
	}

	public void addEntity(NsBlackListSocketQueryLog bean) throws CommonException {
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
			NsBlackListSocketQueryLog bean = (NsBlackListSocketQueryLog) rootDAO.query(NsBlackListSocketQueryLog.class, id);
			if (bean == null) {
				return false;
			}
		} catch (CommonException e) {
			System.out.println("判断实体是否重复出错");
		}
		return true;
	}

	public void modEntity(NsBlackListSocketQueryLog bean) {
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
	public NsBlackListSocketQueryLog selectById(String id) {
		BlackListDAO rootdao = BlackListDAOUtils.getBlackListDAO();
		NsBlackListSocketQueryLog bean = null;
		try {
			bean = (NsBlackListSocketQueryLog) rootdao.query(NsBlackListSocketQueryLog.class, id);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
