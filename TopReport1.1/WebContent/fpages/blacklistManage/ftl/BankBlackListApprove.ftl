<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackListApprove" init="false"  submitMode="selected"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	</tr>
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListApproveGuoup" label="ѡ���������Ϣ" expand="true">
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
	
	//��λһ�м�¼
	function locate(id) {
		var record = BankBlackListApprove_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListApprove_dataset.setRecord(record);
		}
	}

	//չʾ�Աȹ��ܵ�js
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
			alert("������ѡ��һ����������¼��");
			return false;
		} else {
			confirm("ȷ������ѡ�еĺ�������");
			//return true;
		}
	}
	
	function btApprove_postSubmit(button) {
		alert("���������ɹ���");
		button.url = "#";
		//ˢ�µ�ǰҳ
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
			alert("������ѡ��һ����������¼��");
			return false;
		}
		confirm("ȷ��ȡ������ѡ�еĺ�������");
		//return true;
	}
	
	function btCancelApprove_postSubmit(button) {
		alert("ȡ�����������ɹ���");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankBlackListApprove_dataset.flushData(BankBlackListApprove_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
