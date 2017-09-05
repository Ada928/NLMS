package com.cibfintech.blacklist.operation;

import java.io.IOException;

import resource.bean.blacklist.NsInternationalBlackList;
import resource.bean.report.SysTaskInfo;

import com.cibfintech.blacklist.service.InternationBlackListOperateLogService;
import com.cibfintech.blacklist.service.InternationalBlackListService;
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

public class InternationalBlackListOperation extends BaseOperation {
	public static final String ID = "InternationalBlackListOperation";
	public static final String CMD = "CMD";
	public static final String IN_PARAM = "IN_PARAM";
	public final static String CMD_ADD = "CMD_ADD";
	public final static String CMD_MOD = "CMD_MOD";
	public final static String CMD_DEL = "CMD_DEL";
	private static final HtLog htlog = HtLogFactory.getLogger(InternationalBlackListOperation.class);

	@Override
	public void beforeProc(OperationContext context) throws CommonException {

	}

	@Override
	public void execute(OperationContext context) throws CommonException {
		String cmd = (String) context.getAttribute(CMD);
		NsInternationalBlackList internationalBlackList = (NsInternationalBlackList) context.getAttribute(IN_PARAM);
		// 调用服务类
		InternationalBlackListService service = InternationalBlackListService.getInstance();
		String operateType = "";
		String message = "";
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(internationalBlackList);
			NsInternationalBlackList sys1 = service.selectById(internationalBlackList.getId());
			// sysCurService.update(sysCurrency);
			sys1.setOperateState(ReportEnum.REPORT_ST1.DE.value);
			sys1.setDel(SystemConstant.TRUE);
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			sys1.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(sys1);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value, ReportEnum.REPORT_TASK_TRANS_CD.DEL.value,
						internationalBlackList, internationalBlackList.getId(), internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			operateType = SystemConstant.LOG_DELEATE;
			message = "国际黑名单的删除";
			recordRunningLog("Deleter.log", message);
		} else if (CMD_ADD.equals(cmd)) {
			// 插入或者更新
			// service.addEntity(internationalBlackList);
			internationalBlackList.setOperateState(ReportEnum.REPORT_ST1.CR.value);
			internationalBlackList.setCreateDate(DateUtil.getCurrentDate());
			internationalBlackList.setDel(SystemConstant.FALSE);
			internationalBlackList.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			internationalBlackList.setLastModifyDate(DateUtil.getCurrentDate());

			service.addEntity(internationalBlackList);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value, ReportEnum.REPORT_TASK_TRANS_CD.NEW.value,
						internationalBlackList, internationalBlackList.getId(), internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			operateType = SystemConstant.LOG_ADD;
			message = "国际黑名单的增加";
			recordRunningLog("Adder.log", message);
		} else if (CMD_MOD.equals(cmd)) {
			// service.modEntity(internationalBlackList);
			// Iterator it=service.selectByid(internationalBlackList.getId());
			NsInternationalBlackList sys1 = service.selectById(internationalBlackList.getId());

			sys1.setOperateState(ReportEnum.REPORT_ST1.ET.value);
			sys1.setAccountType(internationalBlackList.getAccountType());
			sys1.setCertificateType(internationalBlackList.getCertificateType());
			sys1.setCertificateNumber(internationalBlackList.getCertificateNumber());
			sys1.setClientName(internationalBlackList.getClientName());
			sys1.setClientEnglishName(internationalBlackList.getClientEnglishName());
			sys1.setBlacklistType(internationalBlackList.getBlacklistType());
			sys1.setValid(internationalBlackList.isValid());
			sys1.setValidDate(internationalBlackList.getValidDate());
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			sys1.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(sys1);

			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value, ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value,
						internationalBlackList, internationalBlackList.getId(), internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			operateType = SystemConstant.LOG_EDIT;
			message = "国际黑名单的编辑";
			recordRunningLog("Updater.log", message);
		}

		InternationBlackListOperateLogService interBLOperateLogService = InternationBlackListOperateLogService.getInstance();
		interBLOperateLogService.saveInternationBLOperateLog(operateType, "", "", message);
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unused")
	private void recordRunningLog(String type, String message) throws CommonException {
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog(type, new String[] { gi.getTlrno(), gi.getBrcode(), message });
		htlog.info(type, new String[] { gi.getBrcode(), gi.getTlrno(), message });
	}
}
