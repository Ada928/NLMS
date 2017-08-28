package com.cibfintech.report.blacklist.operation;

import java.io.IOException;

import com.cibfintech.report.blacklist.service.PoliceBlackListService;
import com.huateng.common.DateUtil;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.business.common.SystemConstant;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

import resource.bean.report.PoliceBlackList;
import resource.bean.report.SysTaskInfo;

public class PoliceBlackListOperation extends BaseOperation {
	public static final String ID = "BankBlackListOperation";
	public static final String CMD = "CMD";
	public static final String IN_PARAM = "IN_PARAM";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_MOD = "CMD_MOD";
	public final static String CMD_DEL = "CMD_DEL";
	private static final HtLog htlog = HtLogFactory
			.getLogger(PoliceBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		PoliceBlackList policeBlackList = (PoliceBlackList) context
				.getAttribute(IN_PARAM);
		// 调用服务类
		PoliceBlackListService service = PoliceBlackListService.getInstance();
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(policeBlackList);
			PoliceBlackList sys1 = service.selectById(policeBlackList.getId());
			// sysCurService.update(sysCurrency);
			sys1.setOperateState(ReportEnum.REPORT_ST1.DE.value);
			sys1.setDel(SystemConstant.TRUE);
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			sys1.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(sys1);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						policeBlackList, policeBlackList.getId(),
						policeBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "公安部黑名单的删除" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "公安部黑名单的删除" });
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			// service.addEntity(policeBlackList);
			policeBlackList.setOperateState(ReportEnum.REPORT_ST1.CR.value);
			policeBlackList.setCreateDate(DateUtil.getCurrentDate());
			policeBlackList.setDel(SystemConstant.FALSE);
			policeBlackList.setBankCode(GlobalInfo.getCurrentInstance()
					.getBrcode());
			policeBlackList.setLastModifyOperator(GlobalInfo
					.getCurrentInstance().getTlrno());
			policeBlackList.setLastModifyDate(DateUtil.getCurrentDate());

			service.addEntity(policeBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.NEW.value,
						policeBlackList, policeBlackList.getId(),
						policeBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log",
					new String[] { gi.getTlrno(), gi.getBrcode(), "公安部黑名单的增加" });
			htlog.info("Updater.log",
					new String[] { gi.getBrcode(), gi.getTlrno(), "公安部黑名单的增加" });
		} else if (CMD_MOD.equals(cmd)) {
			// service.modEntity(policeBlackList);
			// Iterator it=service.selectByid(policeBlackList.getId());
			PoliceBlackList sys1 = service.selectById(policeBlackList.getId());
			if (policeBlackList.getBankCode().trim() == "") {
				sys1.setBankCode(GlobalInfo.getCurrentInstance().getBrcode());
			} else {
				sys1.setBankCode(policeBlackList.getBankCode());
			}
			sys1.setOperateState(ReportEnum.REPORT_ST1.ET.value);
			sys1.setAccountType(policeBlackList.getAccountType());
			sys1.setAccountCode(policeBlackList.getAccountCode());
			sys1.setCertificateType(policeBlackList.getCertificateType());
			sys1.setCertificateNumber(policeBlackList.getCertificateNumber());
			sys1.setClientName(policeBlackList.getClientName());
			sys1.setClientEnglishName(policeBlackList.getClientEnglishName());
			sys1.setBlacklistType(policeBlackList.getBlacklistType());
			sys1.setValid(policeBlackList.isValid());
			sys1.setValidDate(policeBlackList.getValidDate());
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance()
					.getTlrno());
			sys1.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(sys1);

			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(
						ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
						policeBlackList, policeBlackList.getId(),
						policeBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog("Updater.log",
				new String[] { gi.getTlrno(), gi.getBrcode(), "公安部黑名单的编辑" });
		htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(),
				"公安部黑名单的编辑" });
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}
}
