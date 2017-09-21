<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign op="${RequestParameters['op']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackListShare" init="true"  submitMode="current"  navigate="false">
<table align="center" width="100%">
	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="true"/></td>
	</tr>
	<tr>
		<td width="100%">
			<@CommonQueryMacro.GroupBox id="BankBlackListShareGuoup" label="ѡ���������Ϣ" expand="true">
				<table frame=void width="100%">
					<tr>
						<td colspan="2">
							<@CommonQueryMacro.DataTable id="datatable1" paginationbar="" 
									fieldStr="blacklistid,brcode,certificateType,certificateNumber,"+
										"clientName,clientEnglishName,blacklistType,editUserID,verifyUserID,approveUserID,editDate,"+
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
	
	//��λһ�м�¼
	function locate(id) {
		var record = BankBlackListShare_dataset.find([ "id" ], [ id ]);
		if (record) {
			BankBlackListShare_dataset.setRecord(record);
		}
	}

	//ϵͳˢ�µ�Ԫ��
	function datatable1_statedes_onRefresh(cell, value, record) {
		if (record) {
			var auditType = record.getValue("auditType");
			var auditState = record.getValue("auditState");
			var id = record.getValue("blacklistid");
			var select = record.getValue("select");
		
			var tempHtml = "<center>";
			if(auditType == "3" && auditState == "1"){
				tempHtml += "����δͨ�����";
			} else if(auditType == "3" && auditState == "2"){
				tempHtml += "�����ύ�У������";
			} else if(auditType == "3" && auditState == "3"){
				tempHtml += "�������ͨ����������";
			} else if(auditType == "3" && auditState == "4"){
				tempHtml += "����ͨ��������ɹ�";
			} else if(auditType == "4" && auditState == "1"){
				tempHtml += "ȡ������δͨ�����";
			} else if(auditType == "4" && auditState == "2"){
				tempHtml += "ȡ�������ύ�У������";
			} else if(auditType == "4" && auditState == "3"){
				tempHtml += "ȡ���������ͨ����������";
			} else if(auditType == "4" && auditState == "4"){
				tempHtml += "ȡ����������ͨ�������·���";
			} else if(auditType == "1" && auditState == "4"){
				tempHtml += "�༭������ɣ����Է���";
			} else if(auditType == "2" && auditState == "4"){
				tempHtml += "�༭������ɣ����Է���";
			} 
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	//ϵͳˢ�µ�Ԫ��
	function datatable1_opr_onRefresh(cell, value, record) {
		if (record) {
			var auditType = record.getValue("auditType");
			var auditState = record.getValue("auditState");
			var id = record.getValue("blacklistid");
			var select = record.getValue("select");
		
			var tempHtml = "<center>";
			
			if(auditType == "3" && auditState == "1"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">���·���</a> ";
			} else if(auditType == "3" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:cancelShareRecord('" + id
				+ "')\">ȡ������</a> ";
			} else if(auditType == "4" && auditState == "1"){
				tempHtml += "<a href=\"JavaScript:cancelShareRecord('" + id
				+ "')\">����ȡ������</a> ";
			} else if(auditType == "4" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">���·���</a> ";
			} else if(auditType == "1" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">���������</a> ";
			} else if(auditType == "2" && auditState == "4"){
				tempHtml += "<a href=\"JavaScript:shareRecord('" + id
				+ "')\">���������</a> ";
			} 
			cell.innerHTML = tempHtml + "</center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	//�޸Ĺ���
	function shareRecord(id) {
		btShare.click();
	}
	
	//�޸Ĺ���
	function cancelShareRecord(id) {
		btCancelShare.click();
	}
	
	//չʾ�Աȹ��ܵ�js
	function datatable1_blacklistid_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("blacklistid");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		//BankBlackListShare_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistDetail.ftl?op=detail&reType=share&blacklistid="+id;
	}

	function btShare_onClickCheck() {
		BankBlackListShare_dataset.setParameter("op", "shareT");
		if(!confirm("ȷ������ѡ�еĺ�������")){
			return false;
		}
	}
	
	function btShare_postSubmit(button) {
		alert("��������������ύ�ɹ�����ȴ���ˡ�");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}
	
	function btCancelShare_onClickCheck() {
		BankBlackListShare_dataset.setParameter("op", "shareF");
		if(!confirm("ȷ��ȡ������ѡ�еĺ�������")){
			return false;
		}
		//return true;
	}
	
	function btCancelShare_postSubmit(button) {
		alert("ȡ����������������ύ�ɹ�����ȴ���ˡ�");
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankBlackListShare_dataset.flushData(BankBlackListShare_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
