<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign opType=RequestParameters["opType"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="银行机构信息维护">
<@CommonQueryMacro.CommonQuery id="BranchManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id="BranchManageGroup" label="银行机构信息维护"
	        			  fieldStr="brcode,brno,brname,address,postno,teleno" colNm=4/>
		</td>
	</tr>
	<tr >
		<td  align="center">
	  		<@CommonQueryMacro.Button id="btSave" />
		 	&nbsp;&nbsp;&nbsp;&nbsp;
			<@CommonQueryMacro.Button id="btCancel" />
	  	</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="javascript">
	
	var roleType = "${info.roleTypeList}";
	var opType = "${opType}";

	function initCallGetter_post(dataset) {
		var brcode = BranchManage_dataset.getValue("brcode");
		if (brcode == null || brcode == "") {
			BranchManage_dataset.setFieldReadOnly("brcode", false);
			//document.getElementById("unLock").style.disabled = "none";
			//document.getElementById("btResetPwd").style.disabled = "none";
		} else {
			BranchManage_dataset.setFieldReadOnly("brcode", true);
		}
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		if (BranchManage_dataset.getValue("blnUpBrcode") == ""
				&& BranchManage_dataset.getValue("brclass") != "1") {
			alert("字段[上级机构]不应为空。");
			return false;
		}

		if (BranchManage_dataset.getValue("brclass") == "") {
			alert("字段[机构级别]不应为空。");
			return false;
		}
		return true;
	}
	
	//保存后刷新当前页
	function btSave_postSubmit(button) {
		alert("保存成功");
		button.url = "/fpages/regonization/ftl/BranchEntry.ftl";
	}
</script>
</@CommonQueryMacro.page>