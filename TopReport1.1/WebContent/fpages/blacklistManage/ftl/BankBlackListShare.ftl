<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单分享">
<@CommonQueryMacro.CommonQuery id="BankBlackListShare" init="true"  submitMode="current"  navigate="false">
<table align="center" width="100%">
	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="true"/></td>
	</tr>
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListShareGuoup" label="选择黑名单信息" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="" 
									fieldStr="id,blacklistid,brcode,accountType,accountCode,certificateType,certificateNumber,"+
										"clientName,clientEnglishName,cardBkBookNo,blacklistType,editUserID,verifyUserID,approveUserID,editDate,"+
										"auditType,auditState,verifyDate,approveDate,statedes,opr"  
									width="100%" hasFrame="true"/><br/>
						</td>
					</tr>
					<tr align="center" style="display:none">
						<td><@CommonQueryMacro.Button id="btDetail" /></td>
						<td><@CommonQueryMacro.Button id="btShare" /></td>
						<td><@CommonQueryMacro.Button id="btCancelShare" /></td>
					</tr>
				</table>
		 	</@CommonQueryMacro.GroupBox>
		 </td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var op ="${op}"; 
	var opType ="${opType}"; 
	var currentTlrno = "${info.tlrNo}";
	var roleType = "${info.roleTypeList}";
	
	function initCallGetter_post(dataset) {
		BankBlackListShare_dataset.setParameter("op", op);
	}
	
	//定位一行记录
	function locate(id) {
		var record = BankBlackListShare_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListShare_dataset.setRecord(record);
		}
	}

	//系统刷新单元格
	function datatable1_statedes_onRefresh(cell, value, record) {
		if (record) {
			var auditType = record.getValue("auditType");
			var auditState = record.getValue("auditState");
			var id = record.getValue("blacklistid");
			var select = record.getValue("select");
		
			var tempHtml = "<center>";
			if(auditType == "3" && auditState == "1"){
				tempHtml += "分享未通过审核";
			} else if(auditType == "3" && auditState == "2"){
				tempHtml += "分享提交中，待审核";
			} else if(auditType == "3" && auditState == "3"){
				tempHtml += "分享审核通过，待审批";
			} else if(auditType == "3" && auditState == "4"){
				tempHtml += "审批通过，分享成功";
			} else if(auditType == "4" && auditState == "1"){
				tempHtml += "取消分享未通过审核";
			} else if(auditType == "4" && auditState == "2"){
				tempHtml += "取消分享提交中，待审核";
			} else if(auditType == "4" && auditState == "3"){
				tempHtml += "取消分享审核通过，待审批";
			} else if(auditType == "4" && auditState == "4"){
				tempHtml += "取消分享审批通过，重新分享";
			} else if(auditType == "1" && auditState == "4"){
				tempHtml += "编辑审批完成，可以分享";
			} else if(auditType == "2" && auditState == "4"){
				tempHtml += "编辑审批完成，可以分享";
			} 
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	//系统刷新单元格
	function datatable1_opr_onRefresh(cell, value, record) {
		if (record) {
			var auditType = record.getValue("auditType");
			var auditState = record.getValue("auditState");
			var id = record.getValue("blacklistid");
			var select = record.getValue("select");
		
			var tempHtml = "<center>";
			
			if(auditType == "3" && auditState == "1"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">重新分享</a> ";
			} else if(auditType == "3" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:cancelShareRecord('" + id
				+ "')\">取消分享</a> ";
			} else if(auditType == "4" && auditState == "1"){
				tempHtml += "<a href=\"JavaScript:cancelShareRecord('" + id
				+ "')\">重新取消分享</a> ";
			} else if(auditType == "4" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">重新分享</a> ";
			} else if(auditType == "1" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">分享该名单</a> ";
			} else if(auditType == "2" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">分享该名单</a> ";
			} 
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	//修改功能
	function shareRecord(id) {
		btShare.click();
	}
	
	//修改功能
	function cancelShareRecord(id) {
		btCancelShare.click();
	}
	
	//展示对比功能的js
	function datatable1_blacklistid_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("blacklistid");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">" + "查看详细" + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		//BankBlackListShare_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		showWin("黑名单信息详细","${contextPath}/fpages/blacklistManage/ftl/BankBlackListDetail.ftl?op=detail&reType=share&blacklistid="+ id,"window","flushCurrentPage()",window);
		//window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlackListDetail.ftl?op=detail&reType=share&blacklistid="+id;
	}

	function btShare_onClickCheck() {
		BankBlackListShare_dataset.setParameter("op", "shareT");
		if(!confirm("确定分享选中的黑名单？")){
			return false;
		}
	}
	
	function btShare_postSubmit(button) {
		alert("分享黑名单申请提交成功，请等待审核。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}
	
	function btCancelShare_onClickCheck() {
		BankBlackListShare_dataset.setParameter("op", "shareF");
		if(!confirm("确定取消审批选中的黑名单？")){
			return false;
		}
		//return true;
	}
	
	function btCancelShare_postSubmit(button) {
		alert("取消分享黑名单申请提交成功，请等待审核。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}

	//刷新当前页
	function flushCurrentPage() {
		BankBlackListShare_dataset.flushData(BankBlackListShare_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
