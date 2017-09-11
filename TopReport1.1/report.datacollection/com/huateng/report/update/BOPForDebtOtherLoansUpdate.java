package com.huateng.report.update;

import java.util.Date;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import resource.bean.report.BopCfaCreditorDs;
import resource.bean.report.BopCfaExdebtDs;
import resource.bean.report.BopProjectInfo;

import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.report.bean.BOPForDebtBilLoanCreditor;
import com.huateng.report.constants.TopReportConstants;

import com.huateng.report.operation.BOPForDebtOtherLoansOperation;
import com.huateng.report.service.BOPForDebtBilLoanService;
import com.huateng.report.service.BOPForDebtProjectService;

import com.huateng.report.service.BopCfaCreditorDsService;
import com.huateng.report.utils.ReportUtils;

/**
 * 外债信息-双边贷款-签约信息 operation
 * 
 * @author cwenao
 * @version 1.0 2012-8-28
 */
public class BOPForDebtOtherLoansUpdate extends BaseUpdate {

	private static final String DATASET_ID = "BOPForDebtOtherLoansCol";

	private static final String RECORD_DELETE = "del";
	private static final String RECORD_ADD = "new";
	private static final String RECORD_MOD = "mod";

	@SuppressWarnings("rawtypes")
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean arg0, HttpServletRequest arg1, HttpServletResponse arg2)
			throws AppException {
		// 返回对象
		UpdateReturnBean updateReturnBean = new UpdateReturnBean();
		// 取得外债信息结果集对象
		UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
		// 开始处理
		// 外债信息表
		BopCfaExdebtDs bpExdebt = null;
		// 债权人信息表
		BopCfaCreditorDs bcc = null;
		// 项目信息表
		BopProjectInfo bproject = null;

		BopCfaCreditorDsService creditorServ = null;
		BOPForDebtBilLoanService debtServ = null;
		BOPForDebtProjectService projectServ = null;

		OperationContext oc = new OperationContext();
		GlobalInfo gInfo = GlobalInfo.getCurrentInstance();
		String guid = ReportUtils.getUUID();

		if (updateResultBean.hasNext()) {
			// 自定义bean ： 外债信息+单债权人信息
			BOPForDebtBilLoanCreditor bpExdebtTemp = new BOPForDebtBilLoanCreditor();

			String op = updateResultBean.getParameter("op");

			Map map = updateResultBean.next();
			mapToObject(bpExdebtTemp, map);

			if (!StringUtils.isEmpty(op)) {
				if (RECORD_ADD.equalsIgnoreCase(op)) {
					bpExdebt = new BopCfaExdebtDs();
					// 项目信息表
					bcc = new BopCfaCreditorDs();
					bproject = new BopProjectInfo();

					bpExdebt.setExdebtcode(bpExdebtTemp.getExdebtcode());
					bpExdebt.setDebtorcode(bpExdebtTemp.getDebtorcode());
					bpExdebt.setDebtype(bpExdebtTemp.getDebtype());
					bpExdebt.setContractamount(bpExdebtTemp.getContractamount());
					bpExdebt.setContractcurr(bpExdebtTemp.getContractcurr());
					bpExdebt.setValuedate(bpExdebtTemp.getValuedate());
					bpExdebt.setMaturity(bpExdebtTemp.getMaturity());
					bpExdebt.setFloatrate(bpExdebtTemp.getFloatrate());
					bpExdebt.setAnninrate(bpExdebtTemp.getAnninrate());
					bpExdebt.setInprterm(bpExdebtTemp.getInprterm());
					bpExdebt.setSpapfeboindex(bpExdebtTemp.getSpapfeboindex());
					bpExdebt.setDebtyperema(bpExdebtTemp.getDebtyperema());

					bpExdebt.setRemark(bpExdebtTemp.getRemark());
					bpExdebt.setWorkDate(gInfo.getTxdate().toString().replaceAll("-", ""));
					bpExdebt.setFiller2(bpExdebtTemp.getFiller2().toString());

					bcc.setCreditorcode(bpExdebtTemp.getCreditorcode());
					bcc.setCreditorname(bpExdebtTemp.getCreditorname());
					bcc.setCreditornamen(bpExdebtTemp.getCreditornamen());
					bcc.setCreditortype(bpExdebtTemp.getCreditortype());
					bcc.setOpercode(bpExdebtTemp.getOpercode());
					bcc.setCrehqcode(bpExdebtTemp.getCrehqcode());

					bproject.setProjectname(bpExdebtTemp.getProjectname());
					/*
					 * 操作状态=A-创建 记录状态=02-编辑待确认 审核状态=00-未审核 回执状态=00-未返回
					 * 是否已成功上报=0-否
					 */
					bpExdebt.setId(guid);
					bpExdebt.setCrtTm(new Date());
					bpExdebt.setLstUpdTm(new Date());
					bpExdebt.setLstUpdTlr(gInfo.getTlrno());
					bpExdebt.setBrNo(gInfo.getBrno());

					bpExdebt.setApptype(TopReportConstants.REPORT_APP_TYPE_CFA);
					bpExdebt.setCurrentfile(TopReportConstants.REPORT_FILE_TYPE_CFA_AI);

					bpExdebt.setActiontype(TopReportConstants.REPORT_ACTIONTYPE_A);
					bpExdebt.setRecStatus(TopReportConstants.REPORT_RECSTATUS_02);
					bpExdebt.setApproveStatus(TopReportConstants.REPORT_APPROVESTATUS_00);
					bpExdebt.setRepStatus(TopReportConstants.REPORT_REPSTATUS_00);
					bpExdebt.setSubSuccess(TopReportConstants.REPORT_IS_SUB_SUCCESS_NO);

					bcc.setRecId(guid);
					bcc.setCrtTm(new Date());
					bcc.setId(ReportUtils.getUUID());

					bproject.setRecId(guid);
					bproject.setCrtTm(new Date());
					bproject.setId(ReportUtils.getUUID());

					oc.setAttribute(BOPForDebtOtherLoansOperation.CMD, BOPForDebtOtherLoansOperation.CMD_INSERT);
				} else if (RECORD_MOD.equalsIgnoreCase(op)) {
					creditorServ = BopCfaCreditorDsService.getInstance();
					debtServ = BOPForDebtBilLoanService.getInstance();
					projectServ = BOPForDebtProjectService.getInstance();

					bpExdebt = debtServ.load(bpExdebtTemp.getId());
					bcc = creditorServ.load(bpExdebtTemp.getCreditorid());
					bproject = projectServ.load(bpExdebtTemp.getProjid());

					bpExdebt.setExdebtcode(bpExdebtTemp.getExdebtcode());
					bpExdebt.setDebtorcode(bpExdebtTemp.getDebtorcode());
					bpExdebt.setDebtype(bpExdebtTemp.getDebtype());
					// bpExdebt.setContractdate(bpExdebtTemp.getContractdate());
					bpExdebt.setContractamount(bpExdebtTemp.getContractamount());
					bpExdebt.setContractcurr(bpExdebtTemp.getContractcurr());
					bpExdebt.setValuedate(bpExdebtTemp.getValuedate());
					bpExdebt.setMaturity(bpExdebtTemp.getMaturity());
					bpExdebt.setFloatrate(bpExdebtTemp.getFloatrate());
					bpExdebt.setAnninrate(bpExdebtTemp.getAnninrate());
					bpExdebt.setInprterm(bpExdebtTemp.getInprterm());
					bpExdebt.setSpapfeboindex(bpExdebtTemp.getSpapfeboindex());
					bpExdebt.setRemark(bpExdebtTemp.getRemark());
					bpExdebt.setWorkDate(gInfo.getTxdate().toString().replaceAll("-", ""));
					bpExdebt.setDebtyperema(bpExdebtTemp.getDebtyperema());
					bpExdebt.setFiller2(bpExdebtTemp.getFiller2().toString());

					bcc.setCreditorcode(bpExdebtTemp.getCreditorcode());
					bcc.setCreditorname(bpExdebtTemp.getCreditorname());
					bcc.setCreditornamen(bpExdebtTemp.getCreditornamen());
					bcc.setCreditortype(bpExdebtTemp.getCreditortype());
					bcc.setOpercode(bpExdebtTemp.getOpercode());
					bcc.setCrehqcode(bpExdebtTemp.getCrehqcode());

					bproject.setProjectname(bpExdebtTemp.getProjectname());

					if (!StringUtils.isEmpty(bpExdebtTemp.getSubSuccess())
							&& TopReportConstants.REPORT_IS_SUB_SUCCESS_YES
									.equalsIgnoreCase(bpExdebtTemp.getSubSuccess())) {
						/*
						 * 上报已成功
						 *
						 * 操作状态=C-修改 记录状态=02-编辑待确认 审核状态=00-未审核 回执状态=00-未返回
						 * 是否已成功上报=不变化
						 */
						bpExdebt.setActiontype(TopReportConstants.REPORT_ACTIONTYPE_C);
						bpExdebt.setRecStatus(TopReportConstants.REPORT_RECSTATUS_02);
						bpExdebt.setApproveStatus(TopReportConstants.REPORT_APPROVESTATUS_00);
						bpExdebt.setRepStatus(TopReportConstants.REPORT_REPSTATUS_00);
					} else if (!StringUtils.isEmpty(bpExdebtTemp.getSubSuccess())
							&& TopReportConstants.REPORT_IS_SUB_SUCCESS_NO
									.equalsIgnoreCase(bpExdebtTemp.getSubSuccess())) {
						/*
						 * 上报未成功
						 *
						 * 操作状态=A-创建 记录状态=02-编辑待确认 审核状态=00-未审核 回执状态=00-未返回
						 * 是否已成功上报=不变化
						 */
						bpExdebt.setActiontype(TopReportConstants.REPORT_ACTIONTYPE_A);
						bpExdebt.setRecStatus(TopReportConstants.REPORT_RECSTATUS_02);
						bpExdebt.setApproveStatus(TopReportConstants.REPORT_APPROVESTATUS_00);
						bpExdebt.setRepStatus(TopReportConstants.REPORT_REPSTATUS_00);
					}
					bpExdebt.setActiondesc(null);// 新增或修改时不需要填写删除原因
					bpExdebt.setLstUpdTlr(gInfo.getTlrno());
					bpExdebt.setLstUpdTm(new Date());

					oc.setAttribute(BOPForDebtOtherLoansOperation.CMD, BOPForDebtOtherLoansOperation.CMD_UPDATE);
				} else if (RECORD_DELETE.equalsIgnoreCase(op)) {
					/*
					 * 操作状态=D-删除 记录状态=02-编辑待确认 审核状态=00-未审核 回执状态=00-未返回
					 * 是否已成功上报=不变化
					 */
					debtServ = BOPForDebtBilLoanService.getInstance();
					creditorServ = BopCfaCreditorDsService.getInstance();
					projectServ = BOPForDebtProjectService.getInstance();

					bpExdebt = debtServ.load(bpExdebtTemp.getId());
					bcc = creditorServ.load(bpExdebtTemp.getCreditorid());
					bproject = projectServ.load(bpExdebtTemp.getProjid());

					bpExdebt.setWorkDate(gInfo.getTxdate().toString().replaceAll("-", ""));

					bpExdebt.setLstUpdTlr(gInfo.getTlrno());
					bpExdebt.setLstUpdTm(new Date());

					bpExdebt.setActiontype(TopReportConstants.REPORT_ACTIONTYPE_D);
					bpExdebt.setRecStatus(TopReportConstants.REPORT_RECSTATUS_02);
					bpExdebt.setApproveStatus(TopReportConstants.REPORT_APPROVESTATUS_00);
					bpExdebt.setRepStatus(TopReportConstants.REPORT_REPSTATUS_00);
					bpExdebt.setActiondesc(bpExdebtTemp.getActiondesc());

					oc.setAttribute(BOPForDebtOtherLoansOperation.CMD, BOPForDebtOtherLoansOperation.CMD_DELETE);
				}
			}
		}
		oc.setAttribute(BOPForDebtOtherLoansOperation.IN_PARAM_EXDEBT, bpExdebt);
		oc.setAttribute(BOPForDebtOtherLoansOperation.IN_PARAM_BCC, bcc);
		oc.setAttribute(BOPForDebtOtherLoansOperation.IN_PARAM_BPROJECT, bproject);

		OPCaller.call(BOPForDebtOtherLoansOperation.ID, oc);
		return updateReturnBean;
	}
}