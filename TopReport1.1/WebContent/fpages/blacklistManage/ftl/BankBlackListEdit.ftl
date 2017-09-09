<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к������༭">
<@CommonQueryMacro.CommonQuery id="BankBlackListEdit" init="false"  submitMode="selected"  navigate="false">
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
			<@CommonQueryMacro.GroupBox id="BankBlackListEditGuoup" label="ѡ���������Ϣ" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd,-,btDel" 
									fieldStr="select,id[100],blacklistType,accountType[60],certificateType,certificateNumber[100],clientName[200],"+
										"clientEnglishName[200],operateState[100],shareState[100],delState[100],opr[150]"  
									width="100%" hasFrame="true"/><br/>
						</td>
					</tr>
					<tr align="center" style="display:none">
						<td><@CommonQueryMacro.Button id="btModify" /></td>
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
	
	function initCallGetter_post(dataset) {
		BankBlackListEdit_dataset.setParameter("op", op);
	}
	
	//��λһ�м�¼
	function locate(id) {
		var record = BankBlackListEdit_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListEdit_dataset.setRecord(record);
		}
	}

	//ϵͳˢ�µ�Ԫ��
	function datatable1_opr_onRefresh(cell, value, record) {
		if (record) {
			var op = record.getValue("operateState");
			var share = record.getValue("share");
			var shareState = record.getValue("shareState");
			var id = record.getValue("id");
			var tempHtml = "<center>";
			tempHtml += "<a href=\"JavaScript:openModifyWindow('" + id
					+ "')\">�޸�</a> ";
			if (share == "F" && shareState == "1") {
				tempHtml += "<a href=\"JavaScript:doShare('" + id
						+ "')\">����</a> ";
			} else if (share == "T" && shareState == "4") {
				tempHtml += "<a href=\"JavaScript:doCancelShare('" + id
						+ "')\">ȡ������</a> ";
			}
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}

	//�޸Ĺ���
	function openModifyWindow(id) {
		locate(id);
		btModify.click();
	}

	function doShare(id) {
		locate(id);
		confirm("ȷ������ѡ�еĺ�������");
		BankBlackListEdit_dataset.setParameter("op", "shareT");
		btShare.click();
	}

	function doCancelShare(id) {
		locate(id);
		confirm("ȷ��ȡ������ѡ�еĺ�������");
		BankBlackListEdit_dataset.setParameter("op", "shareF");
		btCancelShare.click();
	}

	function btShare_postSubmit(button) {
		alert("��������������ύ�ɹ�����ȴ���ˡ�");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}

	function btCancelShare_postSubmit(button) {
		alert("ȡ����������������ύ�ɹ�����ȴ���ˡ�");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}

	function btDel_onClickCheck(button) {
		var record1 = BankBlackListEdit_dataset.getFirstRecord();
		BankBlackListEdit_dataset.setParameter("op", "delT");
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
			confirm("ȷ��ɾ��ѡ�еĺ�������");
		}
	}

	function btDel_postSubmit(button) {
		alert("ɾ�������������ύ�ɹ�����ȴ���ˡ�");
		button.url = "#";
		flushCurrentPage();
	}

	//չʾ�Աȹ��ܵ�js
	function datatable1_id_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">"
					+ value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		locate(id);
		btDetail.click();
	}

	function btAdd_onClick(button) {
		BankBlackListEdit_dataset.insertRecord();
	}

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankBlackListEdit_dataset
				.flushData(BankBlackListEdit_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
