<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="用户角色管理">
	<table align="left" width="90%">
		<tr align="center">
			<td width="100%">
				<@CommonQueryMacro.CommonQuery id="operMngMod" init="true" navigate="false" submitMode="all" >
					<@CommonQueryMacro.GroupBox id="guoup1" label="操作员信息" expand="true">
						<table frame=void class="grouptable" width="100%">
						<tr>
							<td align="center" nowrap class="labeltd" width="25%"> 操作员号 </td>
							<td class="datatd"  width="25%"><@CommonQueryMacro.SingleField fId="tlrno"/></td>
							<td align="center" nowrap class="labeltd"  width="25%"> 操作员名称 </td>
							<td class="datatd"  width="25%"><@CommonQueryMacro.SingleField fId="tlrName" /></td>
						</tr>
					   </table>
				   </@CommonQueryMacro.GroupBox>
				</@CommonQueryMacro.CommonQuery>
			</td>
		</tr>
		
		<tr>
			<td>
				<@CommonQueryMacro.CommonQuery id="bctlMngEntry" init="true" submitMode="selected" navigate="false">
					<@CommonQueryMacro.GroupBox id="guoup1" label="选择授权机构" expand="true">
						<table frame=void width="100%">
					      	<tr>
					      		<td valign="top">
									<@CommonQueryMacro.DataTable id ="datatable1" fieldStr="select[60],brno[160],brname" width="100%" readonly="false"/>
								</td>
						 	</tr>
							
						 </table>
					 </@CommonQueryMacro.GroupBox>
				</@CommonQueryMacro.CommonQuery>
			</td>
			
		</tr>
		<tr>
			<td width="100%">
				<@CommonQueryMacro.CommonQuery id="operMngRoleInfo" init="true" submitMode="selected" navigate="false">
					<table width="100%">
						<tr>
							<td width="100%">
								<@CommonQueryMacro.GroupBox id="guoup2" label="选择岗位" expand="true">
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
	      		<@CommonQueryMacro.Button id= "btRoleSave" />
	      					&nbsp;&nbsp;
	            <@CommonQueryMacro.Button id= "btCancel" />
	      	</td>
		</tr>
</table>
<script language="javascript">
	var op = "${op}";
    var roleType = "${info.roleTypeList}";
	function initCallGetter_post(dataset) {
		if (roleType == "1" || roleType == "2" || roleType == "3") {
			operMngMod_dataset.setFieldReadOnly("brcode", true);
		}
		operMngMod_dataset.setParameter("op", op);
	}

	function btRoleSave_onClickCheck() {
		var tlrno = operMngMod_dataset.getValue("tlrno");
		var tlrName = operMngMod_dataset.getValue("tlrName");
		if (tlrno.length == 0 || tlrName.length == 0) {
			alert("操作员号码, 操作员, 操作员类型名称和银行代码必须填写！");
			return false;
		} 
		if(tlrno.length < 8){
			alert("操作员号的长度必须大于8位！");
			return false;
		}
		
		var bctlRecord = bctlMngEntry_dataset.getFirstRecord();
		var chk = 0;
		var bctlArr = new Array();
		while (bctlRecord) {
			var v_selected = bctlRecord.getValue("select");
			if (v_selected) {
				bctlArr[chk] = bctlRecord.getValue("brno");
				chk++;
			}
			bctlRecord = bctlRecord.getNextRecord();
		}
		if (chk == 0) {
			alert("至少选择一个授权机构！");
			return false;
		}

		var record1 = operMngRoleInfo_dataset.getFirstRecord();
		var chk = 0;
		var bizArr = new Array();
		while (record1) {
			var temp = record1.getValue("select");
			if (temp) {
				bizArr[chk] = record1.getValue("roleId");
				chk++;
			}
			record1 = record1.getNextRecord();
		}

		if (chk == 0) {
			alert("请至少选择一个岗位！");
			return false;
		}

		//alert("保存成功！");
		return true;
	}
	
</script>
</@CommonQueryMacro.page>
