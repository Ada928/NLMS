<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单审批">
<@CommonQueryMacro.CommonQuery id="BankBlackListApprove" init="true"  submitMode="selected"  navigate="false">
<table align="center" width="100%">
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListApproveGuoup" label="选择黑名单信息" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="" 
									fieldStr="select,blacklistid,brcode,certificateType,certificateNumber,"+
										"clientName,clientEnglishName,blacklistType,editUserID,verifyUserID,approveUserID,editDate,"+
										"auditType,auditState,verifyDate,approveDate"  
									width="100%" hasFrame="true"/><br/>
						</td>
					</tr>
					<tr align="center" style="display:none">
						<td><@CommonQueryMacro.Button id="btDetail" /></td>
					</tr>
					<tr align="center" >
						<td><@CommonQueryMacro.Button id="btApprove" />
	      					&nbsp;&nbsp;
						<@CommonQueryMacro.Button id="btCancelApprove" /></td>
	      					&nbsp;&nbsp;
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
		BankBlackListApprove_dataset.setParameter("op", op);
	}
	
	//定位一行记录
	function locate(id) {
		var record = BankBlackListApprove_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListApprove_dataset.setRecord(record);
		}
	}

	//展示对比功能的js
	function datatable1_blacklistid_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("blacklistid");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		//BankBlackListApprove_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistDetail.ftl?op=detail&reType=approve&blacklistid="+id;
	}

	function btApprove_onClickCheck() {
		var record1 = BankBlackListApprove_dataset.getFirstRecord();
		BankBlackListApprove_dataset.setParameter("op", "approveT");
		var chk = 0;
		var bizArr = new Array();
		while (record1) {
			var temp = record1.getValue("select");
			if (temp) {
				bizArr[chk] = record1.getValue("id");
				chk++;
			}
			record1 = record1.getNextRecord();
		}

		if (chk == 0) {
			alert("请至少选择一条黑名单记录！");
			return false;
		} else {
			if(!confirm("确定审批选中的黑名单？")){
				return false;
			}
		}
	}
	
	function btApprove_postSubmit(button) {
		alert("审批操作成功。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}
	
	function btCancelApprove_onClickCheck() {
		var record1 = BankBlackListApprove_dataset.getFirstRecord();
		BankBlackListApprove_dataset.setParameter("op", "approveF");
		var chk = 0;
		var bizArr = new Array();
		while (record1) {
			var temp = record1.getValue("select");
			if (temp) {
				bizArr[chk] = record1.getValue("id");
				chk++;
			}
			record1 = record1.getNextRecord();
		}

		if (chk == 0) {
			alert("请至少选择一条黑名单记录！");
			return false;
		}
		confirm("确定取消审批选中的黑名单？");
		//return true;
	}
	
	function btCancelApprove_postSubmit(button) {
		alert("取消审批操作成功。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}

	//刷新当前页
	function flushCurrentPage() {
		BankBlackListApprove_dataset.flushData(BankBlackListApprove_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
