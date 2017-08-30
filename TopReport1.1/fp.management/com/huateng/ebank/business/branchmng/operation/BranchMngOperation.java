package com.huateng.ebank.business.branchmng.operation;

import java.io.IOException;

import resource.bean.pub.Bctl;
import resource.bean.report.SysTaskInfo;
import resource.dao.pub.BctlDAO;

import com.huateng.ebank.business.common.DAOUtils;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.report.common.service.ReportShowDetailService;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;
import com.huateng.service.pub.BctlOperateLogService;

public class BranchMngOperation extends BaseOperation {

	public static final String ID = "management.BranchMngOperation";
	public static final String CMD = "cmd";
	public static final String CMD_STATUS = "cmd_status";
	public static final String CMD_DEL = "cmd_del";
	public static final String IN_BRHID = "IN_BRHID";
	public static final String IN_PARAM = "IN_PARAM";

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
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		String brhid = (String) context.getAttribute(IN_BRHID);
		BctlDAO bctlDAO = DAOUtils.getBctlDAO();
		String message = "";
		String operateType = "";

		if (CMD_STATUS.equals(cmd)) {
			String status = (String) context.getAttribute(IN_PARAM);
			// 往bctl表中插入数据的bean
			Bctl bctl = bctlDAO.query(brhid);
			// 有效变为无效的处理
			if ("0".equals(status)) {

				// 序列华后往taskInfo中插入数据
				SysTaskInfo taskInfo;
				try {
					Bctl bctlTaskInfo = bctlDAO.query(brhid);
					bctlTaskInfo.setLock(true);
					bctlTaskInfo.setStatus(status);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100199.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							bctlTaskInfo, bctlTaskInfo.getBrcode(),
							bctlTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// bctl.setLock(true);
				bctl.setSt(ReportEnum.REPORT_ST1.ET.value);

				bctl.setStatus(ReportEnum.REPORT_VAILD.YES.value);
				bctlDAO.getHibernateTemplate().update(bctl);
				message = "银行信息有效变为无效的处理";
				operateType = SystemConstant.LOG_EDIT;

			}
			// 无效变为 有效的处理
			else {

				SysTaskInfo taskInfo;
				try {
					Bctl bctlTaskInfo = bctlDAO.query(brhid);
					bctlTaskInfo.setLock(true);
					bctlTaskInfo.setStatus(status);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100199.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							bctlTaskInfo, bctlTaskInfo.getBrcode(),
							bctlTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bctl.setSt(ReportEnum.REPORT_ST1.ET.value);

				bctl.setStatus(ReportEnum.REPORT_VAILD.NO.value);
				bctlDAO.getHibernateTemplate().update(bctl);
				message = "银行信息无效变为有效的处理";
				operateType = SystemConstant.LOG_EDIT;

			}
		} else if (CMD_DEL.equals(cmd)) {
			String del = (String) context.getAttribute(IN_PARAM);
			// 往bctl表中插入数据的bean
			Bctl bctl = bctlDAO.query(brhid);
			// 删除变为不删除的处理
			if ("F".equals(del)) {

				// 序列华后往taskInfo中插入数据
				SysTaskInfo taskInfo;
				try {
					Bctl bctlTaskInfo = bctlDAO.query(brhid);
					bctlTaskInfo.setLock(true);
					bctlTaskInfo.setDel(false);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100199.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							bctlTaskInfo, bctlTaskInfo.getBrcode(),
							bctlTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// bctl.setLock(true);
				bctl.setSt(ReportEnum.REPORT_ST1.ET.value);

				bctl.setDel(false);
				bctlDAO.getHibernateTemplate().update(bctl);
				message = "银行信息恢复删除状态的处理";
				operateType = SystemConstant.LOG_DELEATE;
			}
			// 删除的处理
			else {
				SysTaskInfo taskInfo;
				try {
					Bctl bctlTaskInfo = bctlDAO.query(brhid);
					bctlTaskInfo.setLock(true);
					bctlTaskInfo.setDel(true);

					taskInfo = ReportTaskUtil.getSysTaskInfoBean(
							ReportEnum.REPORT_TASK_FUNCID.TASK_100199.value,
							ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
							bctlTaskInfo, bctlTaskInfo.getBrcode(),
							bctlTaskInfo.getSt());
					ReportShowDetailService.getInstance().addTosystaskinfo(
							taskInfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bctl.setSt(ReportEnum.REPORT_ST1.ET.value);

				bctl.setDel(true);
				bctlDAO.getHibernateTemplate().update(bctl);
				message = "银行信息删除的处理";
				operateType = SystemConstant.LOG_DELEATE;
			}
		}

		BctlOperateLogService bctlOperateLogService = BctlOperateLogService
				.getInstance();
		bctlOperateLogService.saveBctlOperateLog(operateType, "", "", message);
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