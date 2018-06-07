/**
 *
 */
package com.huateng.report.imports.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topexpression.ExpressionEvaluator;
import org.topexpression.IllegalExpressionException;
import org.topexpression.datameta.Variable;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.report.BiImportFieldConfig;
import resource.bean.report.BiImportLog;
import resource.bean.report.BiImportLogDtl;
import resource.report.dao.ROOTDAO;
import resource.report.dao.ROOTDAOUtils;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.huateng.common.DateUtil;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.operation.SingleOPCaller;
import com.huateng.ebank.framework.util.ExceptionUtil;
import com.huateng.report.imports.common.Constants;
import com.huateng.report.imports.common.ConvertMean;
import com.huateng.report.imports.common.FileImportUtil;
import com.huateng.report.imports.common.ReadWriteFile;
import com.huateng.report.imports.model.Constant;
import com.huateng.report.imports.model.LogInfo;
import com.huateng.report.imports.model.SqlInfo;
import com.huateng.report.imports.model.TFieldSetInfo;
import com.huateng.report.imports.model.TFieldValueInfo;
import com.huateng.report.imports.model.TFileDataInfo;
import com.huateng.report.imports.model.TFilterData;
import com.huateng.report.imports.model.TOutValue;
import com.huateng.report.imports.operation.ImportFileOP;
import com.huateng.report.imports.service.FileImportService;
import com.ist.util.DateUtils;

import east.utils.tools.ColsBean;

/**
 * 导入文件
 *
 * @author chl_seu
 *
 */
@SuppressWarnings("unchecked")
public class ImportFileToTable {

	private final static Logger logger = LoggerFactory.getLogger(ImportFileToTable.class);

	private TFileDataInfo curImpFileInfo; // 当前导入文件信息

	private Constant constant;

	private LogInfo logInfo = new LogInfo();

	private List<SqlInfo> sqlList = new ArrayList();

	/**
	 * @param constant2
	 * @param logName
	 * @function 导入单个文件
	 * @param lcurImpFileInfo当前导入文件信息
	 * @throws IOException
	 */
	public boolean importFile(TFileDataInfo lcurImpFileInfo, String sPath, Constant constant2) throws Exception {
		constant = constant2;
		curImpFileInfo = lcurImpFileInfo;
		constant.id = curImpFileInfo.getGuid();
		int c1 = FileImportService.getInstance().getMaxSeqNoFromLog(curImpFileInfo.getGuid(),
				curImpFileInfo.getTradeDate());
		constant.sericalNo = c1 + 1;
		 
		
		Map logMap = new HashMap();
		logMap.put("guid", curImpFileInfo.getGuid());
		logMap.put("fileName", curImpFileInfo.getFileName());// 导入文件名称
		logMap.put("tableName", curImpFileInfo.getTableName());
		logMap.put("workDate", lcurImpFileInfo.getTradeDate());
		logMap.put("fileOwner", curImpFileInfo.getFileOwner());
		logMap.put("sericalNo", constant.sericalNo);
		logMap.put("beginTime", curImpFileInfo.getBeginTime());
		logMap.put("batchNo", curImpFileInfo.getBatchNo());
		logMap.put("errorNumber", 0);
		logMap.put("totalRows", 0);
		logMap.put("correctRows", 0);
		logMap.put("filterRows", 0);
		String errorFileName = "";
		String errorFilePath = "";
		if (curImpFileInfo.getErrFlg() == 0) {
			errorFileName = new StringBuffer("[").append(curImpFileInfo.getFileName()).append("-")
					.append(curImpFileInfo.getBatchNo()).append("-").append(constant.sericalNo).append("].txt")
					.toString();
			errorFilePath = new StringBuffer(sPath).append("error").append(File.separator).toString();
		} else {
			String tmpFileName = curImpFileInfo.getFileName();
			tmpFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf("-") + 1);
			errorFileName = new StringBuffer(tmpFileName).append(constant.sericalNo).append("].txt").toString();
			errorFilePath = sPath;
		}
		StringBuffer strLog = new StringBuffer();
		strLog.append("表名").append(curImpFileInfo.getTableName()).append(",路径:").append(sPath);
		logger.debug(strLog.toString());
		if (!FileImportService.getInstance().isTableExist(curImpFileInfo.getTableName())) {
			logger.debug("数据库中目的表不存在!");
			constant.errorMessage = "数据库中目的表不存在";
			logMap.put("importStatus", ImportFileVar.IMPORT_STATUS_FALSE);
			logMap.put("errorMessage", "数据库中目的表不存在");
			logMap.put("endTime", FileImportUtil.getCurTime());
			saveLog(logMap);
			return false;
		}
		// 初始化内部变量
		curImpFileInfo = lcurImpFileInfo;
		// 读取Excel文件并导入
		boolean flag = fileImp(sPath + curImpFileInfo.getFileName());
		if(flag){
			logMap.put("correctRows", constant.rightRow);
			logMap.put("filterRows", constant.filterRow);
			logMap.put("importStatus", ImportFileVar.IMPORT_STATUS_TRUE);
			logMap.put("endTime", FileImportUtil.getCurTime());
			saveLog(logMap);
			// importStatus(0);//标志为导入完成
			constant.progress = "100%";
			return true;
		}else{
			logMap.put("filterRows", constant.filterRow);
			logMap.put("importStatus", ImportFileVar.IMPORT_STATUS_FALSE);
			logMap.put("endTime", FileImportUtil.getCurTime());
			saveLog(logMap);
			return false;
		}	
		
		
		
	}
		
		//记录日志
		public void saveLog(Map map) {
			logInfo.setLogMan(map);
			saveLogInfo(this.logInfo);
		}
		
		public void saveLogInfo(LogInfo logInfo) {
			BiImportLog importLog = new BiImportLog();
			Map mainLog = logInfo.getLogMan();
			importLog.setFileName((String) mainLog.get("fileName"));
			importLog.setTableName((String) mainLog.get("tableName"));
			importLog.setWorkDate((String) mainLog.get("workDate"));
			// importLog.setFileOwner((String) mainLog.get("fileOwner"));
			importLog.setBatchNo((Integer) mainLog.get("batchNo"));
			importLog.setSeqNo((Integer) mainLog.get("sericalNo"));
			importLog.setBeginTime((String) mainLog.get("beginTime"));
			importLog.setErrorRows((Integer) mainLog.get("errorNumber"));
			importLog.setTotalRows((Integer) mainLog.get("totalRows"));
			importLog.setCorrectRows((Integer) mainLog.get("correctRows"));
			importLog.setFilterRows((Integer) mainLog.get("filterRows"));
			importLog.setImportStatus((String) mainLog.get("importStatus"));
			importLog.setErrorMessage((String) mainLog.get("errorMessage"));
			importLog.setEndTime((String) mainLog.get("endTime"));
			importLog.setErrFilePath((String) mainLog.get("errFileNamePath"));
			importLog.setErrFile((String) mainLog.get("errFileName"));
			importLog.setFuid((String) mainLog.get("guid"));

			BiImportLogDtl logDtl = null;
			for (int i = 0; i < logInfo.getLogDtl().size(); i++) {
				logDtl = new BiImportLogDtl();
				Map dtlMap = (Map) logInfo.getLogDtl().get(i);
				logDtl.setLog(importLog);
				logDtl.setBeginTime((String) dtlMap.get("beginTime"));
				logDtl.setFileName((String) dtlMap.get("fileName"));
				logDtl.setTableName((String) mainLog.get("tableName"));
				logDtl.setWorkDate((String) dtlMap.get("workDate"));
				// logDtl.setFileOwner((String) dtlMap.get("fileOwner"));
				logDtl.setSeqNo((Integer) dtlMap.get("sericalNo"));
				logDtl.setLineNo((Integer) dtlMap.get("lineNo"));
				logDtl.setPosNo((String) dtlMap.get("posNo"));
				logDtl.setErrFile((String) dtlMap.get("errFileName"));
				logDtl.setErrorMessage((String) dtlMap.get("errorMessage"));
				logDtl.setEndTime((String) dtlMap.get("endTime"));
				importLog.getLogDtls().add(logDtl);
			}

			OperationContext context = new OperationContext();
			context.setAttribute(ImportFileOP.CMD, ImportFileOP.DO_SAVELOG);
			context.setAttribute(ImportFileOP.PARAM, importLog);
			try {
				SingleOPCaller.call(ImportFileOP.ID, context);
			} catch (CommonException e) {
				logger.error(e.getMessage());
			}

		}

		public boolean fileImp(String fileRealPath){
			//开始导入文件数据到表中
				NsBankBlackList blackList = new NsBankBlackList();
					InputStream stream = null;
					try {
						stream=new FileInputStream(fileRealPath);
						HSSFWorkbook workbook = new HSSFWorkbook(stream);
						HSSFSheet sheet = workbook.getSheetAt(0);
						
						
						for(int i = 1; i<=sheet.getLastRowNum()+1;i++){
							HSSFRow row = sheet.getRow(i);
							if(row!=null){
								HSSFCell cell = row.getCell((short) 0);				
								String cellValue = this.getCellValue(cell);
								blackList.setBankCode(cellValue);
								if ("".equals(cellValue)) {
									break;
								}	
								cell = row.getCell((short) 1);
								if (cell != null)
									cellValue = this.getCellValue(cell);
								else{
									cellValue = "";
									}
								    blackList.setAccountType(cellValue);
								    cell = row.getCell((short) 2);
								    cellValue = this.getCellValue(cell);
								    blackList.setAccountCode(cellValue);
								    
								    cell = row.getCell((short) 3);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
								    	blackList.setClientName(cellValue);
								    }else{
								    	return false;
								    }
								    cell = row.getCell((short) 4);
								    cellValue = this.getCellValue(cell);
								    blackList.setClientEnglishName(cellValue);
								    cell = row.getCell((short) 5);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
								    	 blackList.setCertificateType(cellValue);
								    }else{
								    	return false;
								    }
								   
								    cell = row.getCell((short) 6);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
								    	blackList.setCertificateNumber(cellValue);
								    }else{
								    	return false;
								    }
								    
								    cell = row.getCell((short) 7);
								    cellValue = this.getCellValue(cell);
								    blackList.setBlacklistType(cellValue);
								    cell = row.getCell((short) 8);
								    cellValue = this.getCellValue(cell);
								    blackList.setShare(cellValue);
								    cell = row.getCell((short) 9);
								    cellValue = this.getCellValue(cell);
								    blackList.setValid(cellValue);
								    cell = row.getCell((short) 10);
								    cellValue = this.getCellValue(cell);
								    blackList.setDel(cellValue);
								    cell = row.getCell((short) 11);
								    cellValue = this.getCellValue(cell);
								    blackList.setApprove(cellValue);
								    cell = row.getCell((short) 12);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
								    	blackList.setValidDate(DateUtil.stringToDate(cellValue));
								    }else{
								    	blackList.setValidDate(null);

								    }
								    cell = row.getCell((short) 13);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
									    blackList.setBlacklistedDate(DateUtil.stringToDate(cellValue));
								    }else{
								    	blackList.setBlacklistedDate(DateUtil.getCurrentDate());
								    }
								    cell = row.getCell((short) 14);
								    cellValue = this.getCellValue(cell);
								    blackList.setBlacklistedReason(cellValue);
								    cell = row.getCell((short) 15);
								    cellValue = this.getCellValue(cell);
								    if(null != cellValue && !"".equals(cellValue)){
									    blackList.setUnblacklistedDate(DateUtil.stringToDate(cellValue));
								    }else{
								    	blackList.setUnblacklistedDate(null);
								    }
								    cell = row.getCell((short) 16);
								    cellValue = this.getCellValue(cell);
								    blackList.setBlacklistedReason(cellValue);
								    blackList.setId(blackList.getCertificateNumber());
								    BankBlackListService service = BankBlackListService.getInstance();
								    service.modOrAddEntity(blackList);
								    
					}

				}
						
				return true;
						
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}		
					
		}
		
		//获取单元格的值

		public String getCellValue(HSSFCell cell) throws Exception {

			String cellvalue = "";
			System.out.println("cell:::"+cell);
		  if(cell!=null){
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					cellvalue = DateUtils
							.dateToStringShort(cell.getDateCellValue());
				} else {
					double numcell = cell.getNumericCellValue();
					String formatstr = "########.# ";
					java.text.DecimalFormat formatter = new java.text.DecimalFormat(
							formatstr);
					cellvalue = formatter.format(numcell);
					// System.out.println("num::"+cellvalue);

				}
				break;
			}
			case HSSFCell.CELL_TYPE_STRING: {
				cellvalue = cell.getStringCellValue().trim();
				break;
			}

			default:
				cellvalue = "";
			}
			cellvalue = cellvalue.replaceAll(" ", "");
			//if (cellvalue.indexOf('.') > 0)
			//	cellvalue = cellvalue.substring(0, cellvalue.indexOf('.'));
		  }
			return cellvalue;
		}
		
}
