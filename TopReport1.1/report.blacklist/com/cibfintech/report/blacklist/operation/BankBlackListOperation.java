package com.cibfintech.report.blacklist.operation;

import java.io.IOException;

import com.cibfintech.report.blacklist.service.BankBlackListService;
import com.huateng.common.DateUtil;
import com.huateng.common.log.HtLog;
import com.huateng.common.log.HtLogFactory;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.BaseOperation;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.report.utils.ReportEnum;
import com.huateng.report.utils.ReportTaskUtil;

import resource.bean.report.NlmsBankBlackList;
import resource.bean.report.SysTaskInfo;

public class BankBlackListOperation extends BaseOperation {
	public static final String ID = "BankBlacklistOperation";
	public static final String CMD = "CMD";
	public static final String IN_PARAM = "IN_PARAM";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_MOD = "CMD_MOD";
	public final static String CMD_DEL = "CMD_DEL";
	private static final HtLog htlog = HtLogFactory.getLogger(BankBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		NlmsBankBlackList bankBlackList = (NlmsBankBlackList) context.getAttribute(IN_PARAM);
		// 调用服务类
		BankBlackListService service = BankBlackListService.getInstance();
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(bankBlackList);
			NlmsBankBlackList sys1 = service.selectById(bankBlackList.getId());
			// sysCurService.update(sysCurrency);
			sys1.setCreateDate(DateUtil.getDate());
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			sys1.setLastModifyDate(DateUtil.getDate());
			service.modEntity(sys1);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value, bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名單的删除" });
			htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名單的删除" });
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			// service.addEntity(bankBlackList);
			bankBlackList.setCreateDate(DateUtil.getDate());
			bankBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			bankBlackList.setLastModifyDate(DateUtil.getDate());

			service.addEntity(bankBlackList);

			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.NEW.value, bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名單的增加" });
			htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名單的增加" });
		} else if (CMD_MOD.equals(cmd)) {
			// service.modEntity(bankBlackList);
			// Iterator it=service.selectByid(bankBlackList.getId());
			NlmsBankBlackList sys1 = service.selectById(bankBlackList.getId());
			// sysCurService.update(sysCurrency);
			sys1.setCreateDate(DateUtil.getDate());
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			sys1.setLastModifyDate(DateUtil.getDate());
			service.modEntity(sys1);

			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, bankBlackList, bankBlackList.getId(),
						bankBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "商行黑名單的编辑" });
		htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "商行黑名單的编辑" });
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub

	}

}
