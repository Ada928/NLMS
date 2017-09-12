<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro> 
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] /> <#assign op="${RequestParameters['op']?default('')}" /> 
<#assign info = Session["USER_SESSION_INFO"]> 
<@CommonQueryMacro.page title="���к��������"> 
<@CommonQueryMacro.CommonQuery id="BankBlackListVerify" init="true" submitMode="selected" navigate="false">
<table align="center" width="100%">
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListVerifyGuoup" label="ѡ���������Ϣ" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2"><@CommonQueryMacro.DataTable id="datatable1" paginationbar="-"
							fieldStr="select,blacklistid,brcode,certificateType,certificateNumber,"+
							"clientName,clientEnglishName,blacklistType,editUserID,verifyUserID,approveUserID,editDate,"+
							"auditType,auditState,verifyDate,approveDate" width="100%" hasFrame="true"/><br />
						</td>
					</tr>
					<tr align="center" style="display: none">
						<td><@CommonQueryMacro.Button id="btDetail" /></td>
					</tr>
					<tr align="center">
						<td><@CommonQueryMacro.Button id="btVerify" /> 
						&nbsp;&nbsp; 
						<@CommonQueryMacro.Button id="btCancelVerify" /></td>
					</tr>
				</table> 
			</@CommonQueryMacro.GroupBox>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var op = "${op}";

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
		//BankBlackListVerify_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		window.location.href = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistDetail.ftl?op=detail&reType=verify&blacklistid="
				+ id;
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
		BankBlackListVerify_dataset
				.flushData(BankBlackListVerify_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
