package com.huateng.report.imports.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cibfintech.blacklist.bankblacklist.service.BankBlackListService;
import com.huateng.ebank.framework.exceptions.CommonException;
import com.huateng.ebank.framework.util.DataFormat;
import com.huateng.ebank.framework.util.DateUtil;
import com.huateng.report.imports.common.FileImportUtil;
import com.huateng.report.imports.service.ImportConfigService;
import com.huateng.report.imports.service.ImportFieldConfigService;
import com.ist.util.DateUtils;

import resource.bean.blacklist.NsBankBlackList;
import resource.bean.report.BiImportFileConfig;

public class Uploadify extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NsBankBlackList blackList = new NsBankBlackList();
		// 设置接收的编码格式
		request.setCharacterEncoding("UTF-8");
		Date date = new Date();// 获取当前时间
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfFolder = new SimpleDateFormat("yyMM");
		String newfileName = sdfFileName.format(date);// 文件名称
		String fileRealPath = "";// 文件存放真实地址

		String fileRealResistPath = "";// 文件存放真实相对路径
		// 名称 界面编码 必须 和request 保存一致..否则乱码
		String name = request.getParameter("name");
		String firstFileName = "";
		// 获得容器中上传文件夹所在的物理路径
		String savePath = null;

		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 获取多个上传文件
			List  fileList = upload.parseRequest(request);
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				// 如果item是文件上传表单域
				// 获得文件名及路径
				String fileName = item.getName();
				if (fileName != null) {
					firstFileName = item.getName();
					if (savePath == null) {
						try {
							// modified by xuhong 2015-4-24 修改上传路径 begin
							// savePath =
							// FileImportUtil.getFilePath(firstFileName
							// .substring(
							// firstFileName.lastIndexOf("_") + 1,
							// firstFileName.lastIndexOf(".")));
							String workdate = DataFormat.dateToNumber(DateUtil.getCurrentDate());
							savePath = FileImportUtil.getFilePath(workdate);
							// modified by xuhong 2015-4-24 修改上传路径 end
						} catch (CommonException e) {
							e.printStackTrace();
							return;
						}
						System.out.println("路径" + savePath);
						File file = new File(savePath);
						if (!file.exists()) {
							file.mkdir();
						}
					}
					
					
					fileRealPath = savePath + firstFileName;// 文件存放真实地址
					String downloadInfo = fileName;
					List updateList = new ArrayList();
					List delList = new ArrayList();
					List insertList = new ArrayList();
					BiImportFileConfig importFileBean = new BiImportFileConfig();
					importFileBean.setFileName(downloadInfo);
					//暂时写死，目前只导入商行黑名单
					importFileBean.setTableName("NLMS_BANKBLACKLIST");
					
					ImportConfigService importConfigService = ImportConfigService.getInstance();
					//根据文件是否存在插入/更新文件信息
						if(downloadInfo.endsWith("xls")){
							
							//文件存在的情况下，删除原文件信息
							if (new File(fileRealPath).exists()) { 
								delList.add(importFileBean);
								importConfigService.saveDelUpdata(delList, insertList, updateList);
							}
							importFileBean.setFileType("XLS");
							importFileBean.setBatchNo(0);
							importFileBean.setUpdateType("7");
							importFileBean.setStatus("1");
							importFileBean.setImportTime(DateUtil.Time14ToString2(DateUtil.getCurrentDateWithTime()));

							//保证文件信息表中只有唯一一条文件信息再插入
							List delNewList = new ArrayList();
							insertList.add(importFileBean);
							importConfigService.saveDelUpdata(delNewList, insertList, updateList);
							//end 插入文件信息后将文件上传到指定位置
							
							BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入流
							File f = new File(fileRealPath);
							f.createNewFile();
							BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(f));// 获得文件输出流
							Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
							// 上传成功
							
						}else{
							response.getWriter().write("<script>alert('上传文件格式错误!');</script>");
						}
						
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
			System.out.println("没有上传文件");
			return;
		}catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write("1");

	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}