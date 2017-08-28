package com.cibfintech.report.blacklist.operation;

import java.io.IOException;

import com.cibfintech.report.blacklist.service.InternationalBlackListService;
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

import resource.bean.report.InternationalBlackList;
import resource.bean.report.SysTaskInfo;

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
		InternationalBlackList internationalBlackList = (InternationalBlackList) context.getAttribute(IN_PARAM);
		// 调用服务类
		InternationalBlackListService service = InternationalBlackListService.getInstance();
		if (CMD_DEL.equals(cmd)) {
			// 删除
			// service.removeEntity(internationalBlackList);
			InternationalBlackList sys1 = service.selectById(internationalBlackList.getId());
			// sysCurService.update(sysCurrency);
			sys1.setOperateState(ReportEnum.REPORT_ST1.DE.value);
			sys1.setDel(SystemConstant.TRUE);
			sys1.setLastModifyOperator(GlobalInfo.getCurrentInstance().getTlrno());
			sys1.setLastModifyDate(DateUtil.getCurrentDate());
			service.modEntity(sys1);
			SysTaskInfo taskInfo;
			try {
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.DEL.value, internationalBlackList, internationalBlackList.getId(),
						internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "国际黑名单的删除" });
			htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "国际黑名单的删除" });
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
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.NEW.value, internationalBlackList, internationalBlackList.getId(),
						internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalInfo gi = GlobalInfo.getCurrentInstance();
			gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "国际黑名单的增加" });
			htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "国际黑名单的增加" });
		} else if (CMD_MOD.equals(cmd)) {
			// service.modEntity(internationalBlackList);
			// Iterator it=service.selectByid(internationalBlackList.getId());
			InternationalBlackList sys1 = service.selectById(internationalBlackList.getId());
			
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
				taskInfo = ReportTaskUtil.getSysTaskInfoBean(ReportEnum.REPORT_TASK_FUNCID.TASK_110599.value,
						ReportEnum.REPORT_TASK_TRANS_CD.EDIT.value, internationalBlackList, internationalBlackList.getId(),
						internationalBlackList.getOperateState());
				service.addTosystaskinfo(taskInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GlobalInfo gi = GlobalInfo.getCurrentInstance();
		gi.addBizLog("Updater.log", new String[] { gi.getTlrno(), gi.getBrcode(), "国际黑名单的编辑" });
		htlog.info("Updater.log", new String[] { gi.getBrcode(), gi.getTlrno(), "国际黑名单的编辑" });
	}

	@Override
	public void afterProc(OperationContext context) throws CommonException {
		// TODO Auto-generated method stub
	}
}
