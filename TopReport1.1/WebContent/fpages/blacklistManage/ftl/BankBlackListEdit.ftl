<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к������༭">
<@CommonQueryMacro.CommonQuery id="BankBlackListEdit" init="true"  submitMode="selected"  navigate="false">
<table align="center" width="100%">
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListEditGuoup" label="ѡ���������Ϣ" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd,-,btDel" 
									fieldStr="select,blacklistid,brcode,auditType,certificateNumber,"+
										"clientName,clientEnglishName,blacklistType,editUserID,verifyUserID,approveUserID,editDate,"+
										"verifyDate,approveDate,auditState,certificateType,opr"  
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
			var share = record.getValue("auditType");
			var shareState = record.getValue("auditState");
			var id = record.getValue("blacklistid");
			var tempHtml = "<center>";
			tempHtml += "<a href=\"JavaScript:openModifyWindow('" + id
					+ "')\">�޸�</a> ";
			if (share != "3" && shareState == "4") {
				tempHtml += "<a href=\"JavaScript:doShare('" + id
						+ "')\">����</a> ";
			} else if (share == "3" && shareState == "4") {
				tempHtml += "<a href=\"JavaScript:doCancelShare('" + id
						+ "')\">ȡ������</a> ";
			}
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	//չʾ�Աȹ��ܵ�js
	function datatable1_blacklistid_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("blacklistid");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">"
					+ value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		//BankBlackListEdit_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		window.location.href = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistDetail.ftl?op=detail&reType=edit&blacklistid="+id;
	}

	//�޸Ĺ���
	function openModifyWindow(id) {
		BankBlackListEdit_dataset.setParameter("blacklistid", id);
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
