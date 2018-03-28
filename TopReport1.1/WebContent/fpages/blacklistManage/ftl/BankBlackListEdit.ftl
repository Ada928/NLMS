<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单编辑">
<@CommonQueryMacro.CommonQuery id="BankBlackListEdit" init="true"  submitMode="selected"  navigate="false">
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
			<@CommonQueryMacro.GroupBox id="BankBlackListEditGuoup" label="选择黑名单信息" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd,-,btDel" 
									fieldStr="select,id,blacklistid,brcode,certificateType,certificateNumber,"+
										"clientName,clientEnglishName,blacklistType,editDate,"+
										"verifyDate,approveDate,auditType,auditState,statedes,opr"  
									width="100%" hasFrame="true"/><br/>
						</td>
					</tr>
					<tr align="center" style="display:none">
						<td><@CommonQueryMacro.Button id="btModify" /></td>
						<td><@CommonQueryMacro.Button id="btDetail" /></td>
					</tr>
				</table>
		 	</@CommonQueryMacro.GroupBox>
		 </td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var op ="${op}"; 
	function initCallGetter_post(dataset) {
		BankBlackListEdit_dataset.setParameter("op", op);
	}

	//定位一行记录
	function locate(id) {
		var record = BankBlackListEdit_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListEdit_dataset.setRecord(record);
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
			if(auditType == "1" && auditState == "1"){
				tempHtml += "已保存！可修改";
			} else if(auditType == "2" && auditState == "1"){
				tempHtml += "已打回！可修改";
			}else{
				tempHtml += "已提交！不可修改";
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
			var id = record.getValue("id");
			var blacklistid = record.getValue("blacklistid");
			var select = record.getValue("select");
			var tempHtml = "<center>";
			if(auditType == "1" && auditState == "1"){
			tempHtml += "<a href=\"JavaScript:openModifyWindow('"+id+"','"+blacklistid+"')\">修改</a> " ;
			}else if(auditType == "2" && auditState == "1"){
				tempHtml += "<a href=\"JavaScript:openModifyWindow('"+id+"','"+blacklistid+"')\">修改</a> " ;
			}
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	

	//展示对比功能的js
	function datatable1_blacklistid_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("blacklistid");
			if(id != null && "" != id){
				cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">"
					+ "查看详细" + "</a>";
			}
			
		} else {
			cell.innerHTML = "";
		}
	}
	
	function showDetail(id) {
		//BankBlackListEdit_dataset.setParameter("blacklistid", id);
		showWin("黑名单信息详细","${contextPath}/fpages/blacklistManage/ftl/BankBlackListDetail.ftl?op=detail&reType=edit&blacklistid="+ id,"window","flushCurrentPage()",window);
	}

	//修改功能
	function openModifyWindow(id,blacklistid) {
		//BankBlackListEdit_dataset.setParameter("blacklistid", id);
		showWin("黑名单信息修改","${contextPath}/fpages/blacklistManage/ftl/BankBlackListManage.ftl?id="+ id +"&op=edit&blacklistid="+ blacklistid,"window","flushCurrentPage()",window);
	}
	
	
	function btDel_onClickCheck(button) {
		var record1 = BankBlackListEdit_dataset.getFirstRecord();
		BankBlackListEdit_dataset.setParameter("op", "delT");
		var chk = 0;
		var chkSta = 0;
		var bizArr = new Array();
		while (record1) {
			var temp = record1.getValue("select");
			if (temp) {
				bizArr[chk] = record1.getValue("id");
				chk++;
				if(record1.getValue("auditState") != "1"){
					chkSta++;
				}
				
			}
			record1 = record1.getNextRecord();
		}
		
		if(chkSta != 0){
			alert("有"+chkSta+"条已提交，不可删除！");
			return false;
		}
		
		if (chk == 0) {
			alert("请至少选择一条黑名单记录！");
			return false;
		} else {
			if (!confirm("确定删除选中的黑名单？")) {
				return false;
			}
		}
	}

	function btDel_postSubmit(button) {
		alert("删除黑名单成功。");
		button.url = "#";
		flushCurrentPage();
	}
	
	function btAdd_onClick(button) {
		//BankBlackListEdit_dataset.insertRecord();
		showWin("新增黑名单信息","${contextPath}/fpages/blacklistManage/ftl/BankBlackListManage.ftl?op=add","window","flushCurrentPage()",window);
		//flushCurrentPage();
	}

	//刷新当前页
	function flushCurrentPage() {
		BankBlackListEdit_dataset
				.flushData(BankBlackListEdit_dataset.pageIndex);
	}

	
</script>
</@CommonQueryMacro.page>
