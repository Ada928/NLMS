<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="�û���ɫ����">
	<table align="left" width="90%">
		<tr align="center">
			<td width="100%">
				<@CommonQueryMacro.CommonQuery id="UserInfoManage" init="true" navigate="false" submitMode="all" >
					<@CommonQueryMacro.GroupBox id="guoup1" label="����Ա��Ϣ" expand="true">
						<table frame=void class="grouptable" width="100%">
							<tr>
								<td align="center" nowrap class="labeltd"  width="25%"> �û����� </td>
								<td class="datatd"  width="25%"><@CommonQueryMacro.SingleField fId="tlrName" /></td>
								<td align="center" nowrap class="labeltd"  width="25%"> �������� </td>
								<td class="datatd"  width="25%"><@CommonQueryMacro.SingleField fId="brcode" /></td>
							</tr>
					   </table>
				   </@CommonQueryMacro.GroupBox>
				</@CommonQueryMacro.CommonQuery>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<@CommonQueryMacro.CommonQuery id="UserRoleRelSelect" init="true" submitMode="selected" navigate="false">
					<table width="100%">
						<tr>
							<td width="100%">
								<@CommonQueryMacro.GroupBox id="guoup2" label="ѡ���λ" expand="true">
									<table frame=void width="100%">
								      	<tr>
								      		<td valign="top">
													<@CommonQueryMacro.DataTable id ="datatable1" fieldStr="select[60],roleId[160],roleName" width="100%" readonly="false"/>
											</td>
									 	</tr>
									 </table>
								 </@CommonQueryMacro.GroupBox>
							 </td>
						 </tr>
					</table>
				</@CommonQueryMacro.CommonQuery>
			</td>
		</tr>
		<tr id="buttonHide" align="center">
		  	<td>
	      		<@CommonQueryMacro.Button id= "btSave" />
	      					&nbsp;&nbsp;
	            <@CommonQueryMacro.Button id= "btCancel" />
	      	</td>
		</tr>
</table>
<script language="javascript">
	var op = "${op}";
    var roleType = "${info.roleTypeList}";
	
	function initCallGetter_post(dataset) {
		UserInfoManage_dataset.setParameter("op", op);
		if(roleType.indexOf("10") > -1){
			UserInfoManage_dataset.setFieldReadOnly("brcode",false);
		} else {
			UserInfoManage_dataset.setFieldReadOnly("brcode",true);
		}
	}

	function btSave_onClickCheck() {
		var tlrName = UserInfoManage_dataset.getValue("tlrName");
		if (tlrName.length == 0) {
			alert("�û�����������д��");
			return false;
		}

		var record1 = UserRoleRelSelect_dataset.getFirstRecord();
		var chk = 0;
		var bizArr = new Array();
		while (record1) {
			var temp = record1.getValue("select");
			if (temp=="true") {
				bizArr[chk] = record1.getValue("roleId");
				chk++;
			}
			record1 = record1.getNextRecord();
		}

		if (chk == 0) {
			alert("������ѡ��һ����λ������û���");
			flushCurrentPage();
			return false;
		} else if (chk > 1) {
			alert("һ���û����ֻ��ѡ��һ����λ��");
			flushCurrentPage();
			return false;
		}

		return true;
	}

	//�����ˢ�µ�ǰҳ
	function btSave_postSubmit(button) { 
        var retParam = button.returnParam;
		alert("����ɹ� "+ retParam.SuccessInfo);
		button.url = "/fpages/blacklistManage/ftl/UserInfoEntry.ftl";
	}
	
	function flushCurrentPage() {
		UserRoleRelSelect_dataset.flushData(UserRoleRelSelect_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
