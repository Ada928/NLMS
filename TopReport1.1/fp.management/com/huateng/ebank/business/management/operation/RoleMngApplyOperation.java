package com.huateng.ebank.business.management.operation;

import java.io.IOException;

import resource.bean.pub.RoleInfo;
import resource.bean.report.SysTaskInfo;
import resource.dao.pub.RoleInfoDAO;

import com.huateng.ebank.business.common.DAOUtils;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.report.common.service.ReportShowDetailService;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

/**
 * @Description:
 * @Package: com.huateng.ebank.business.custadmin.operation
 * @author: fubo
 * @date: 2010-7-30 涓婂崃11:13:56
 * @Copyright: Copyright (c) 2010
 * @Company: Shanghai Huateng Software Systems Co., Ltd.
 */
public class RoleMngApplyOperation extends BaseOperation {

	public static final String ID = "management.RoleMngApplyOperation";
	public static final String CMD = "cmd";
	public static final String CMD_STATUS = "cmd_status";
	public static final String CMD_DEL = "cmd_del";
	public static final String IN_BRHID = "IN_BRHID";
	public static final String IN_PARAM = "IN_PARAM";
	public static final String IN_INSERT = "IN_INSERT";
	public static final String IN_UPDATE = "IN_UPDATE";

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.ebank.framework.operation.IOperation#execute(com.huateng.
	 * ebank.framework.operation.OperationContext)
	 */
	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		Integer rowid = Integer
				.valueOf((String) context.getAttribute(IN_BRHID));
		RoleInfoDAO roleInfo = DAOUtils.getRoleInfoDAO();

		if (CMD_STATUS.equals(cmd)) {
			String status = (String) context.getAttribute(IN_PARAM);
			// 往bctl表中插入数据的bean
			RoleInfo role = roleInfo.query(rowid);
			// 有效变为无效的处理
			if ("0".equals(status)) {

				// 序列华后往taskInfo中插入数据
				SysTaskInfo taskInfo;
				try {
					RoleInfo roleTaskInfo = roleInfo.query(rowid);
					// roleTaskInfo.setLock(true);
					roleTaskInfo.setStatus(status);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							roleTaskInfo, roleTaskInfo.getId().toString(),
							roleTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// role.setLock(true);
				role.setSt(ReportEnum.REPORT_ST1.ET.value);

				role.setStatus(ReportEnum.REPORT_VAILD.YES.value);
				roleInfo.getHibernateTemplate().update(role);

			}
			// 无效变为 有效的处理
			else {

				SysTaskInfo taskInfo;
				try {
					RoleInfo roleTaskInfo = roleInfo.query(rowid);
					// roleTaskInfo.setLock(true);
					roleTaskInfo.setStatus(status);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							roleTaskInfo, roleTaskInfo.getId().toString(),
							roleTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				role.setSt(ReportEnum.REPORT_ST1.ET.value);

				role.setStatus(ReportEnum.REPORT_VAILD.NO.value);
				roleInfo.getHibernateTemplate().update(role);

			}

		} else if (CMD_DEL.equals(cmd)) {
			String del = (String) context.getAttribute(IN_PARAM);
			// 往bctl表中插入数据的bean
			RoleInfo role = roleInfo.query(rowid);
			// 删除变为不删除的处理
			if ("F".equals(del)) {

				// 序列华后往taskInfo中插入数据
				SysTaskInfo taskInfo;
				try {
					RoleInfo roleTaskInfo = roleInfo.query(rowid);
					// roleTaskInfo.setLock(true);
					roleTaskInfo.setIsDel(del);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
							roleTaskInfo, roleTaskInfo.getId().toString(),
							roleTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// role.setLock(true);
				role.setSt(ReportEnum.REPORT_ST1.ET.value);

				role.setIsDel(del);
				roleInfo.getHibernateTemplate().update(role);

			}
			// 删除的处理
			else {
				SysTaskInfo taskInfo;
				try {
					RoleInfo roleTaskInfo = roleInfo.query(rowid);
					// roleTaskInfo.setLock(true);
					roleTaskInfo.setIsDel(del);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100399.value,
							ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
							roleTaskInfo, roleTaskInfo.getId().toString(),
							roleTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				role.setSt(ReportEnum.REPORT_ST1.ET.value);
				role.setIsDel(del);
				roleInfo.getHibernateTemplate().update(role);

			}
		}
	}

	public void execute2(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
		// List insertList = (List) context.getAttribute(IN_INSERT);
		// List updateList = (List) context.getAttribute(IN_UPDATE);
		// RoleInfoTSKService roleInfoService =
		// RoleInfoTSKService.getInstance();
		// roleInfoService.saveCustRole(insertList, updateList);
	}

}
