/*
 * ==================================================================
 * The Huateng Software License
 *
 * Copyright (c) 2004-2005 Huateng Software System.  All rights
 * reserved.
 * ==================================================================
 */
package com.huateng.ebank.business.pageqryexp.action;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.common.Code;
import com.huateng.common.DataFormat;
import com.huateng.commquery.common.CommonQueryConstants;
import com.huateng.commquery.config.CommonQueryUtil;
import com.huateng.commquery.config.bean.base.ICommonQueryBean;
import com.huateng.ebank.business.common.ConfigReader;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.util.WebDownloadFile;
import com.huateng.ebank.framework.util.ftp.FtpUtil;
import com.huateng.ebank.framework.web.struts.ActionExceptionHandler;
import com.huateng.ebank.framework.web.struts.BaseAction;
import com.huateng.ebank.framework.web.struts.QueryExportForm;
import com.huateng.exception.AppException;
import com.huateng.report.imports.service.ImportConfigService;
import com.huateng.service.message.Array;
import com.huateng.service.pub.QryExpService;

import resource.bean.report.BiImportFileConfig;

/**
 *
 * 更新文件信息的Action.
 */
public class FileInsertAction extends BaseAction {
	private static final Log log = LogFactory.getLog(FileInsertAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			// 初始化GlobalInfo
			//this.init(request);
			String downloadInfo = request.getParameter("downloadinfo");
			
			
			List updateList = new ArrayList();
			List delList = new ArrayList();
			List insertList = new ArrayList();
			BiImportFileConfig importFileBean = new BiImportFileConfig();
			importFileBean.setFileName(downloadInfo);
			//暂时写死，目前只导入商行黑名单
			importFileBean.setTableName("NLMS_BANKBLACKLIST");
			importFileBean.setBatchNo(0);
			importFileBean.setUpdateType("7");
			importFileBean.setStatus("1");
			if(downloadInfo.endsWith("xls")){
				importFileBean.setFileType("XLS");
				insertList.add(importFileBean);
				ImportConfigService importConfigService = ImportConfigService.getInstance();

				importConfigService.saveDelUpdata(delList, insertList, updateList);
				//response.getWriter().write("<script>alert('上传成功!');</script>");
				
			}else{
				response.getWriter().write("<script>alert('上传文件格式错误!');</script>");
			}
			//request.getRequestDispatcher("../fpages/imports/ftl/UpData.ftl").forward(request,response); 
			//response.sendRedirect("../fpages/imports/ftl/UpData.ftl");
			// mod by zhaozhiguo 如果本地文件不存在,则通过Ftp同步 end
			//WebDownloadFile.downloadFile(response, file, displayName + ext);
			return null;
		} catch (Exception e) {
			log.error(e);
			String errmsg = Code.encode("上传失败!");
			//response.getWriter().write("<script>alert('上传失败!');</script>");
			//request.setAttribute("errormsg", errmsg);
			//return mapping.findForward("error");
		} 
		return null;
	}
	
}