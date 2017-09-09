<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单审核">
<@CommonQueryMacro.CommonQuery id="BankBlackListVerify" init="false"  submitMode="selected"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	</tr>
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListVerifyGuoup" label="选择黑名单信息" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="" 
									fieldStr="select,id[100],accountType[60],certificateType,certificateNumber[100],clientName[200],"+
										"clientEnglishName[200],operateState[100],blacklistType,share,valid"  
									width="100%" hasFrame="true"/><br/>
						</td>
					</tr>
					<tr align="center" style="display:none">
						<td><@CommonQueryMacro.Button id="btDetail" /></td>
					</tr>
					<tr align="center" >
						<td><@CommonQueryMacro.Button id="btVerify" />
	      					&nbsp;&nbsp;
						<@CommonQueryMacro.Button id="btCancelVerify" /></td>
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
	
	function initCallGetter_post(dataset) {
		BankBlackListVerify_dataset.setParameter("op", op);
	}
	
	//定位一行记录
	function locate(id) {
		var record = BankBlackListVerify_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListVerify_dataset.setRecord(record);
		}
	}

	//展示详细信息的js
	function datatable1_id_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		locate(id);
		btDetail.click();
	}

	function btVerify_onClickCheck(button) {
		var record1 = BankBlackListVerify_dataset.getFirstRecord();
		BankBlackListVerify_dataset.setParameter("op", "verifyT");
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
			confirm("确定审核选中的黑名单？");
		}
	}

	function btVerify_postSubmit(button) {
		alert("审核黑名单成功。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}
	
	function btCancelVerify_onClickCheck(button) {
		var record1 = BankBlackListVerify_dataset.getFirstRecord();
		BankBlackListVerify_dataset.setParameter("op", "verifyF");
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
			confirm("确定取消审核选中的黑名单？");
		}
	}

	function btCancelVerify_postSubmit(button) {
		alert("取消审核黑名单成功。");
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}

	//刷新当前页
	function flushCurrentPage() {
		BankBlackListVerify_dataset.flushData(BankBlackListVerify_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
