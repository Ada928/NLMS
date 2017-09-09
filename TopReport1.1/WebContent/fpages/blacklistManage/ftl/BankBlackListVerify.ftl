<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к��������">
<@CommonQueryMacro.CommonQuery id="BankBlackListVerify" init="false"  submitMode="selected"  navigate="false">
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
			<@CommonQueryMacro.GroupBox id="BankBlackListVerifyGuoup" label="ѡ���������Ϣ" expand="true">
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
	
	//��λһ�м�¼
	function locate(id) {
		var record = BankBlackListVerify_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListVerify_dataset.setRecord(record);
		}
	}

	//չʾ��ϸ��Ϣ��js
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
			alert("������ѡ��һ����������¼��");
			return false;
		} else {
			confirm("ȷ�����ѡ�еĺ�������");
		}
	}

	function btVerify_postSubmit(button) {
		alert("��˺������ɹ���");
		button.url = "#";
		//ˢ�µ�ǰҳ
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
			alert("������ѡ��һ����������¼��");
			return false;
		} else {
			confirm("ȷ��ȡ�����ѡ�еĺ�������");
		}
	}

	function btCancelVerify_postSubmit(button) {
		alert("ȡ����˺������ɹ���");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankBlackListVerify_dataset.flushData(BankBlackListVerify_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
