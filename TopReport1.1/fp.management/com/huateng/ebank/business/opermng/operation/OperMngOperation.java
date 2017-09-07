/*
 * Created on 2005-5-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.huateng.ebank.business.opermng.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import resource.bean.pub.RoleInfo;
import resource.bean.pub.TlrBctlRel;
import resource.bean.pub.TlrInfo;
import resource.bean.pub.TlrRoleRel;
import resource.bean.report.SysTaskInfo;
import resource.dao.pub.TlrBctlRelDAO;
import resource.dao.pub.TlrInfoDAO;
import resource.dao.pub.TlrRoleRelDAO;
import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.DAOUtils;
import com.huateng.ebank.business.common.ErrorCode;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.business.common.service.CommonService;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.report.dataquery.bean.TlrMngRelBean;
import com.huateng.report.system.bean.TlrInfoAuditBean;
import com.huateng.report.system.service.TaskListService;
import com.huateng.report.utils.RepList;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;
import com.huateng.service.pub.PasswordService;
import com.huateng.service.pub.UserMgrService;
import com.huateng.view.pub.TlrRoleRelationView;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author zhiguo.zhao
 *
 */
public class OperMngOperation extends BaseOperation {

	private static final HtLog htlog = HtLogFactory.getLogger(OperMngOperation.class);

	public static final String ID = "management.OperMngOperation";
	public static final String CMD = "cmd";
	public static final String CMD_STATUS = "cmd_status";
	public static final String CMD_DEL = "cmd_del";
	public static final String IN_TLRINFO = "IN_TLRINFO";
	public static final String IN_TLRNO = "IN_TLRNO";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_ROLELIST = "IN_ROLELIST";
	public static final String IN_BCTLLIST = "IN_BCTLLIST";
	// jianxue.zhang
	public static final String IN_TLRLLIST = "IN_TLRLIST";

	private static String info = "";

	public static String getSuccessInfo() {
		return info;
	}

	public static void setSuccessInfo(String str) {
		info = str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.ebank.framework.operation.IOperation#beforeProc(com.huateng
	 * .ebank.framework.operation.OperationContext)
	 */
	public void beforeProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

	}

	// jianxue.zhang
	@SuppressWarnings("unchecked")
	private void delUserRel(String tlrno, List<TlrRoleRel> roleList, List<TlrMngRelBean> tlrList) throws CommonException {
		String key = tlrno;
		// 先删除用户的角色表和机构关联
		ROOTDAO rootdao = ROOTDAOUtils.getROOTDAO();
		TlrRoleRelDAO tlrRoleRelDAO = DAOUtils.getTlrRoleRelDAO();

		List<TlrRoleRel> roleRellist = rootdao.queryByQL2List("from TlrRoleRel where tlrno = '" + key + "'");

		for (TlrRoleRel trlrolereldel : roleRellist) {
			rootdao.delete(trlrolereldel);
		}

		// 给用户分配角色表：
		for (TlrRoleRel trlrolerel : roleList) {
			// rootdao.save(trlrolerel);
			tlrRoleRelDAO.insert(trlrolerel);
		}
	}

	private void updateTlrBctlRels(TlrInfo tlrInfo) throws CommonException {
		TlrBctlRelDAO dao = DAOUtils.getTlrBctlRelDAO();
		List<TlrBctlRel> list = dao.queryByCondition(" po.tlrNo='" + tlrInfo.getTlrno() + "'");
		if (null == list) {
			saveTlrBctleRels(tlrInfo.getBrcode(), tlrInfo.getBrcode());
		} else {
			TlrBctlRel po = list.get(0);
			po.setBrcode(tlrInfo.getBrcode());
			dao.update(po);
		}
	}

	// 保存银行和用户的关系
	private void saveTlrBctleRels(String brcode, String tlrno) throws CommonException {
		TlrBctlRelDAO tlrBctlDAO = DAOUtils.getTlrBctlRelDAO();
		TlrBctlRel tlrBctlRel = new TlrBctlRel();
		tlrBctlRel.setBrcode(brcode);
		tlrBctlRel.setTlrno(tlrno);
		tlrBctlDAO.insert(tlrBctlRel);
	}

	private RepList<TlrRoleRel> saveTlrRoleRels(List<RoleInfo> roles, TlrInfo tlrInfo) throws CommonException {
		RepList<TlrRoleRel> roleRellist = new RepList<TlrRoleRel>();
		TlrRoleRelDAO tlrRoleRelDAO = DAOUtils.getTlrRoleRelDAO();
		for (RoleInfo rl : roles) {
			TlrRoleRel tlrRoleRel = new TlrRoleRel();
			tlrRoleRel.setRoleId(rl.getId());
			tlrRoleRel.setTlrno(tlrInfo.getTlrno());
			tlrRoleRel.setStatus(SystemConstant.VALID_FLAG_VALID);
			tlrRoleRelDAO.insert(tlrRoleRel);
			roleRellist.add(tlrRoleRel);
		}
		return roleRellist;
	}

	private void addTlrInfo(TlrInfo tlrInfo, GlobalInfo globalInfo, TlrInfoDAO tlrInfoDAO) throws CommonException {
		// TlrInfo tmpInfo = tlrInfoDAO.query(tlrInfo.getTlrno());
		String tlrno = "";
		String brcode = tlrInfo.getBrcode();
		String currentBrCode = globalInfo.getBrcode();
		List<TlrInfo> list = new ArrayList<TlrInfo>();
		if (null == brcode || "".equals(brcode)) {
			list = tlrInfoDAO.queryByCondition(" po.tlrno like '%" + currentBrCode + "%'");
			if (list.isEmpty()) {
				tlrno = currentBrCode + "001";
			} else {
				Collections.sort(list);
				tlrno = list.get(list.size() - 1).getTlrno();
				brcode = tlrno.substring(0, 5);
				int index = Integer.parseInt(tlrno.substring(5, tlrno.length()));
				index++;
				if (index < 10) {
					tlrno = brcode + "00" + String.valueOf(index);
				} else if (index < 100) {
					tlrno = brcode + "0" + String.valueOf(index);
				} else {
					tlrno = brcode + String.valueOf(index);
				}
			}
			brcode = currentBrCode;
		} else {
			tlrno = brcode + "001";
		}

		tlrInfo.setTlrno(tlrno);
		tlrInfo.setBrcode(brcode);

		tlrInfo.setStatus(SystemConstant.TLR_NO_STATE_LOGOUT);
		// 设置有效标志
		tlrInfo.setFlag(SystemConstant.FLAG_ON);

		// 设置默认操作员密码
		String sysDefaultPwd = CommonService.getInstance().getSysParamDef("PSWD", "DEFAULT_PWD", SystemConstant.DEFAULT_PASSWORD);
		String encMethod = CommonService.getInstance().getSysParamDef("PSWD", "ENC_MODE", "AES128");
		String password = PasswordService.getInstance().EncryptPassword(sysDefaultPwd, encMethod);
		tlrInfo.setPassword(password);

		// 为操作员密码错误次数付初始值
		tlrInfo.setTotpswderrcnt(new Integer(0));
		tlrInfo.setPswderrcnt(new Integer(0));
		tlrInfo.setPasswdenc(encMethod);
		tlrInfo.setCreateDate(DateUtil.getCurrentDate());
		tlrInfo.setLastUpdTime(DateUtil.getTimestamp());
		tlrInfo.setLastUpdOperId(globalInfo.getTlrno());
		tlrInfo.setLock(SystemConstant.NOT_LOCKED);
		tlrInfo.setDel(SystemConstant.FALSE);
		tlrInfo.setSt(ReportEnum.REPORT_ST1.Y.value);
		tlrInfoDAO.saveOrUpdate(tlrInfo);
		saveTlrBctleRels(brcode, tlrno);
		setSuccessInfo("新建用户成功：用户 ID 为 " + tlrno + ", 默认密码为：" + sysDefaultPwd + ", 请及时登录修改密码。");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.ebank.framework.operation.IOperation#execute(com.huateng.
	 * ebank.framework.operation.OperationContext)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void execute(OperationContext context) throws CommonException {
		// GlobalInfo就相当于一个session
		GlobalInfo globalInfo = GlobalInfo.getCurrentInstance();
		TlrInfoDAO tlrInfoDAO = DAOUtils.getTlrInfoDAO();
		TlrRoleRelDAO relationDao = DAOUtils.getTlrRoleRelDAO();
		ROOTDAO rootdao = ROOTDAOUtils.getROOTDAO();
		// modify by jianxue.zhang
		TaskListService tls = TaskListService.getInstance();

		if ("new".equals(context.getAttribute(CMD))) {
			TlrInfo tlrInfo = (TlrInfo) context.getAttribute(IN_TLRINFO);
			List<RoleInfo> roles = (List<RoleInfo>) context.getAttribute(IN_ROLELIST);

			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {
				if (tlrInfoDAO.query(tlrInfo.getTlrno()) == null) {
					addTlrInfo(tlrInfo, globalInfo, tlrInfoDAO);
					// 保存角色岗位
					saveTlrRoleRels(roles, tlrInfo);
				} else {
					ExceptionUtil.throwCommonException("该操作员已经存在，不能新增", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
				}
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "新增用户编号[" + tlrInfo.getTlrno() + "]" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "新增用户编号[" + tlrInfo.getTlrno() + "]" });
			} else {
				if (tlrInfoDAO.query(tlrInfo.getTlrno()) == null) {
					addTlrInfo(tlrInfo, globalInfo, tlrInfoDAO);
					// 保存角色岗位
					RepList<TlrRoleRel> roleRellist = saveTlrRoleRels(roles, tlrInfo);

					try {
						TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
						tlrInfoAuditBean.setTlrInfo(tlrInfo);
						// tlrInfoAuditBean.setBctlRellist(bctlRellist);
						tlrInfoAuditBean.setRoleRellist(roleRellist);
						SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
								ReportEnum.REPORT_TASK_TRANS_CD.NEW.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), null);
						rootdao.saveOrUpdate(tskInf);
					} catch (IOException e) {
						ExceptionUtil.throwCommonException("操作员新增保存，双岗复核序列化到数据库出错！");
						e.printStackTrace();
					}
				} else {
					ExceptionUtil.throwCommonException("该操作员已经存在，不能新增", ErrorCode.ERROR_CODE_CANNOT_SUBMIT);
				}
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "新增用户编号[" + tlrInfo.getTlrno() + "]" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "新增用户编号[" + tlrInfo.getTlrno() + "]" });
			}

		} else if ("modify".equals(context.getAttribute(CMD))) {
			TlrInfo tlrInfo = (TlrInfo) context.getAttribute(IN_TLRINFO);
			List<RoleInfo> roles = (List<RoleInfo>) context.getAttribute(IN_ROLELIST);

			List<TlrMngRelBean> tlrs = (List<TlrMngRelBean>) context.getAttribute(IN_TLRLLIST);

			// 更新用户银行依赖
			updateTlrBctlRels(tlrInfo);

			// 角色岗位
			RepList<TlrRoleRel> roleRellist = new RepList<TlrRoleRel>();
			for (RoleInfo rl : roles) {
				TlrRoleRel tlrRoleRel = new TlrRoleRel();
				tlrRoleRel.setRoleId(rl.getId());
				tlrRoleRel.setTlrno(tlrInfo.getTlrno());
				tlrRoleRel.setStatus(SystemConstant.VALID_FLAG_VALID);
				roleRellist.add(tlrRoleRel);
			}

			TlrInfo dbTrlInfo = rootdao.query(TlrInfo.class, tlrInfo.getTlrno());
			dbTrlInfo.setBrcode(tlrInfo.getBrcode());
			dbTrlInfo.setSt(ReportEnum.REPORT_ST1.ET.value);

			String oldTlrName = dbTrlInfo.getTlrName();
			Date oldLastUpTm = dbTrlInfo.getLastUpdTime();

			// 新的值序列化数据库
			dbTrlInfo.setTlrName(tlrInfo.getTlrName());
			// dbTrlInfo.setTlrType(tlrInfo.getTlrType());
			dbTrlInfo.setLastUpdTime(DateUtil.getTimestamp());

			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {
				dbTrlInfo.setSt(ReportEnum.REPORT__FH_ST.YES.value);
				rootdao.saveOrUpdate(dbTrlInfo);
				delUserRel(dbTrlInfo.getTlrno(), roleRellist, tlrs);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "修改用户编号[" + dbTrlInfo.getTlrno() + "]" });
			} else {
				try {
					TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
					tlrInfoAuditBean.setTlrInfo(dbTrlInfo);
					// tlrInfoAuditBean.setBctlRellist(bctlRellist);
					tlrInfoAuditBean.setRoleRellist(roleRellist);
					SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), dbTrlInfo.getSt());
					rootdao.saveOrUpdate(tskInf);
				} catch (IOException e) {
					ExceptionUtil.throwCommonException("操作员修改保存，双岗复核序列化到数据库出错！");
					e.printStackTrace();
				}
				dbTrlInfo.setLastUpdTime(oldLastUpTm);
				dbTrlInfo.setTlrName(oldTlrName);
				rootdao.saveOrUpdate(dbTrlInfo);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "修改用户编号[" + dbTrlInfo.getTlrno() + "]" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "修改用户编号[" + dbTrlInfo.getTlrno() + "]" });
			}

		} else if (CMD_DEL.equals(context.getAttribute(CMD))) {
			String tlrno = (String) context.getAttribute(IN_TLRNO);
			TlrInfo tlrInfo = tlrInfoDAO.query(tlrno);

			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {

				if (tlrInfo.getTlrno().equals(ReportEnum.REPORT_SYS_SUPER_MANAGER_INFO.TLRID.value)) {
					ExceptionUtil.throwCommonException("不能删除管理员所在机构信息！");
				} else {
					// 删除
					tlrInfo.setDel(SystemConstant.TRUE);

					rootdao.saveOrUpdate(tlrInfo);

					List urrlist = relationDao.queryByCondition(" po.tlrno = '" + tlrno + "'");
					for (Iterator it = urrlist.iterator(); it.hasNext();) {
						TlrRoleRel ref = (TlrRoleRel) it.next();
						relationDao.delete(ref);
					}

					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]删除操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]删除操作" });
				}

			} else {
				if (tlrInfo.getTlrno().equals(ReportEnum.REPORT_SYS_SUPER_MANAGER_INFO.TLRID.value)) {
					ExceptionUtil.throwCommonException("不能删除管理员所在机构信息！");
				} else {
					List<TlrBctlRel> bctlRellist = rootdao.queryByQL2List("from TlrBctlRel where tlrNo = '" + tlrno + "'");
					List<TlrRoleRel> roleRellist = rootdao.queryByQL2List("from TlrRoleRel where tlrno = '" + tlrno + "'");
					// 授权机构
					RepList<TlrBctlRel> repBctlList = new RepList<TlrBctlRel>();
					for (TlrBctlRel tlrBctlRel : bctlRellist) {
						repBctlList.add(tlrBctlRel);
					}

					// 角色岗位
					RepList<TlrRoleRel> repRoleList = new RepList<TlrRoleRel>();
					for (TlrRoleRel tlrRoleRel : roleRellist) {
						repRoleList.add(tlrRoleRel);
					}

					// 设置修改中
					tlrInfo.setSt(ReportEnum.REPORT_ST1.ET.value);
					boolean oldIsDel = tlrInfo.isDel();
					tlrInfo.setDel(SystemConstant.FALSE);
					try {
						TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
						tlrInfoAuditBean.setTlrInfo(tlrInfo);
						tlrInfoAuditBean.setBctlRellist(repBctlList);
						tlrInfoAuditBean.setRoleRellist(repRoleList);
						SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
								ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), tlrInfo.getSt());
						rootdao.saveOrUpdate(tskInf);
					} catch (IOException e) {
						ExceptionUtil.throwCommonException("操作员删除，双岗复核序列化到数据库出错！");
						e.printStackTrace();
					}
					// 改回原值
					tlrInfo.setDel(oldIsDel);
					rootdao.saveOrUpdate(tlrInfo);
					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]删除操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]删除操作" });
				}
			}
		} else if ("mod".equals(context.getAttribute(CMD))) {
			TlrInfo tlrInfo = (TlrInfo) context.getAttribute(IN_TLRINFO);
			TlrInfo ti = tlrInfoDAO.query(tlrInfo.getTlrno());
			if (ti != null) {
				ti.setTlrName(tlrInfo.getTlrName());
				ti.setBrcode(tlrInfo.getBrcode());
				// ti.setTlrType(tlrInfo.getTlrType());
				ti.setEffectDate(tlrInfo.getEffectDate());
				ti.setExpireDate(tlrInfo.getExpireDate());
				ti.setEmail(tlrInfo.getEmail());
				tlrInfoDAO.saveOrUpdate(ti);
			}
		} else if ("auth".equals(context.getAttribute(CMD))) {
			List roleList = (List) context.getAttribute(IN_ROLELIST);
			TlrRoleRel rr = null;
			for (int i = 0; i < roleList.size(); i++) {
				TlrRoleRelationView inurr = (TlrRoleRelationView) roleList.get(i);
				List urrlist = relationDao.queryByCondition(" po.tlrno = '" + inurr.getTlrno() + "'  and po.roleId = " + inurr.getRoleId());
				// 选中的岗位
				if (inurr.isSelected()) {
					// 原先无数据,则插入新数据
					if (urrlist == null || urrlist.size() == 0) {
						rr = new TlrRoleRel();
						rr.setRoleId(Integer.valueOf(inurr.getRoleId()));
						rr.setTlrno(inurr.getTlrno());
						rr.setStatus(SystemConstant.VALID_FLAG_VALID);// 1有效 0无效
						relationDao.getHibernateTemplate().saveOrUpdate(rr);
					}
					// 原先有数据，则更新status
					else {
						for (int j = 0; j < urrlist.size(); j++) {
							rr = (TlrRoleRel) urrlist.get(j);
							if (!"1".equals(rr.getStatus())) {
								rr.setStatus(SystemConstant.VALID_FLAG_VALID);
								relationDao.getHibernateTemplate().saveOrUpdate(rr);
							}
						}
					}
				}
				// 没有选中的岗位
				else {
					for (int k = 0; k < urrlist.size(); k++) {
						rr = (TlrRoleRel) urrlist.get(k);
						relationDao.delete(rr);
					}
				}

			}
		} else if ("resetPwd".equals(context.getAttribute(CMD))) {
			String tlrno = (String) context.getAttribute(IN_TLRNO);
			// 修改用户密码
			TlrInfo tlrInfo = tlrInfoDAO.query(tlrno);
			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {

				UserMgrService userMgrService = new UserMgrService();
				String sysDefaultPwd = CommonService.getInstance().getSysParamDef("PSWD", "DEFAULT_PWD", SystemConstant.DEFAULT_PASSWORD);

				userMgrService.updatePassword(tlrInfo.getTlrno(), sysDefaultPwd);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "重置用户编号[" + tlrInfo.getTlrno() + "]的密码" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "重置用户编号[" + tlrInfo.getTlrno() + "]的密码" });

			} else {
				List<TlrBctlRel> bctlRellist = rootdao.queryByQL2List("from TlrBctlRel where tlrNo = '" + tlrno + "'");
				List<TlrRoleRel> roleRellist = rootdao.queryByQL2List("from TlrRoleRel where tlrno = '" + tlrno + "'");
				// 授权机构
				RepList<TlrBctlRel> repBctlList = new RepList<TlrBctlRel>();
				for (TlrBctlRel tlrBctlRel : bctlRellist) {
					repBctlList.add(tlrBctlRel);
				}
				// 角色岗位
				RepList<TlrRoleRel> repRoleList = new RepList<TlrRoleRel>();
				for (TlrRoleRel tlrRoleRel : roleRellist) {
					repRoleList.add(tlrRoleRel);
				}

				// 设置修改中
				tlrInfo.setSt(ReportEnum.REPORT_ST1.ET.value);
				// 设置充值密码标识
				tlrInfo.setRestFlg("reset");
				try {
					TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
					tlrInfoAuditBean.setTlrInfo(tlrInfo);
					tlrInfoAuditBean.setBctlRellist(repBctlList);
					tlrInfoAuditBean.setRoleRellist(repRoleList);
					SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
					// 这儿得改成修改
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), tlrInfo.getSt());
					rootdao.saveOrUpdate(tskInf);
				} catch (IOException e) {
					ExceptionUtil.throwCommonException("操作员重置密码，双岗复核序列化到数据库出错！");
					e.printStackTrace();
				}
				rootdao.saveOrUpdate(tlrInfo);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "重置用户编号[" + tlrInfo.getTlrno() + "]的密码" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "重置用户编号[" + tlrInfo.getTlrno() + "]的密码" });
			}
		} else if ("unlock".equals(context.getAttribute(CMD))) {// 解锁
			String tlrno = (String) context.getAttribute(IN_TLRNO);
			TlrInfo tlrInfo = tlrInfoDAO.query(tlrno);
			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {

				// 解锁
				tlrInfo.setLock(SystemConstant.NOT_LOCKED);

				// 改回原值
				rootdao.saveOrUpdate(tlrInfo);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]解锁操作" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]解锁操作" });

			} else {
				List<TlrBctlRel> bctlRellist = rootdao.queryByQL2List("from TlrBctlRel where tlrNo = '" + tlrno + "'");
				List<TlrRoleRel> roleRellist = rootdao.queryByQL2List("from TlrRoleRel where tlrno = '" + tlrno + "'");
				// 授权机构
				RepList<TlrBctlRel> repBctlList = new RepList<TlrBctlRel>();
				for (TlrBctlRel tlrBctlRel : bctlRellist) {
					repBctlList.add(tlrBctlRel);
				}

				// 角色岗位
				RepList<TlrRoleRel> repRoleList = new RepList<TlrRoleRel>();
				for (TlrRoleRel tlrRoleRel : roleRellist) {
					repRoleList.add(tlrRoleRel);
				}

				// 设置修改中
				tlrInfo.setSt(ReportEnum.REPORT_ST1.ET.value);
				boolean oldIsLock = tlrInfo.isLock();
				tlrInfo.setLock(SystemConstant.NOT_LOCKED);
				try {
					TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
					tlrInfoAuditBean.setTlrInfo(tlrInfo);
					tlrInfoAuditBean.setBctlRellist(repBctlList);
					tlrInfoAuditBean.setRoleRellist(repRoleList);
					SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), tlrInfo.getSt());
					rootdao.saveOrUpdate(tskInf);
				} catch (IOException e) {
					ExceptionUtil.throwCommonException("操作员解锁，双岗复核序列化到数据库出错！");
					e.printStackTrace();
				}
				// 改回原值
				tlrInfo.setLock(oldIsLock);
				rootdao.saveOrUpdate(tlrInfo);
				globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]解锁操作" });
				htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]解锁操作" });
			}
		} else if (CMD_STATUS.equals(context.getAttribute(CMD))) { // 有效/无效 强行签退
			String tlrno = (String) context.getAttribute(IN_TLRNO);
			String status = (String) context.getAttribute(IN_PARAM);
			TlrInfo tlrInfo = tlrInfoDAO.query(tlrno);

			if (!tls.isNeedApprove(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value)) {

				// 设置修改中
				if (SystemConstant.FLAG_ON.equals(status) || SystemConstant.FLAG_OFF.equals(status)) {
					tlrInfo.setFlag(status);
				} else if ("logout".equals(status)) {
					tlrInfo.setStatus(SystemConstant.TLR_NO_STATE_LOGOUT);
				}

				// 改回原值
				rootdao.saveOrUpdate(tlrInfo);
				if ("logout".equals(status)) {
					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]强行签退操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]强行签退操作" });
				} else {
					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]有效无效操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "有效无效操作" });
				}
			} else {
				List<TlrBctlRel> bctlRellist = rootdao.queryByQL2List("from TlrBctlRel where tlrNo = '" + tlrno + "'");
				List<TlrRoleRel> roleRellist = rootdao.queryByQL2List("from TlrRoleRel where tlrno = '" + tlrno + "'");
				// 授权机构
				RepList<TlrBctlRel> repBctlList = new RepList<TlrBctlRel>();
				for (TlrBctlRel tlrBctlRel : bctlRellist) {
					repBctlList.add(tlrBctlRel);
				}
				// 角色岗位
				RepList<TlrRoleRel> repRoleList = new RepList<TlrRoleRel>();
				for (TlrRoleRel tlrRoleRel : roleRellist) {
					repRoleList.add(tlrRoleRel);
				}

				// 设置修改中
				tlrInfo.setSt(ReportEnum.REPORT_ST1.ET.value);
				String oldFlag = tlrInfo.getFlag();
				String oldStatus = tlrInfo.getStatus();

				if (SystemConstant.FLAG_ON.equals(status) || SystemConstant.FLAG_OFF.equals(status)) {
					tlrInfo.setFlag(status);
				} else if ("logout".equals(status)) {
					tlrInfo.setStatus(SystemConstant.TLR_NO_STATE_LOGOUT);
				}

				try {
					TlrInfoAuditBean tlrInfoAuditBean = new TlrInfoAuditBean();
					tlrInfoAuditBean.setTlrInfo(tlrInfo);
					tlrInfoAuditBean.setBctlRellist(repBctlList);
					tlrInfoAuditBean.setRoleRellist(repRoleList);
					SysTaskInfo tskInf = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, tlrInfoAuditBean, tlrInfoAuditBean.getTlrInfo().getTlrno(), tlrInfo.getSt());
					rootdao.saveOrUpdate(tskInf);
				} catch (IOException e) {
					if ("logout".equals(status)) {
						ExceptionUtil.throwCommonException("操作员强行签退，双岗复核序列化到数据库出错！");
					} else {
						ExceptionUtil.throwCommonException("操作员有效无效，双岗复核序列化到数据库出错！");
					}
					e.printStackTrace();
				}
				// 改回原值
				tlrInfo.setFlag(oldFlag);
				tlrInfo.setStatus(oldStatus);
				rootdao.saveOrUpdate(tlrInfo);
				if ("logout".equals(status)) {
					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]强行签退操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]强行签退操作" });
				} else {
					globalInfo.addBizLog("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "]有效无效操作" });
					htlog.info("Updater.log", new String[] { globalInfo.getTlrno(), globalInfo.getBrno(), "用户编号[" + tlrInfo.getTlrno() + "有效无效操作" });
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.ebank.framework.operation.IOperation#afterProc(com.huateng
	 * .ebank.framework.operation.OperationContext)
	 */
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

	}
}