<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="银行机构信息维护">
<@CommonQueryMacro.CommonQuery id="BankInfoManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td align="left">
				<@CommonQueryMacro.Group id="BankInfoManageGroup" label="银行机构信息维护"
	        			  fieldStr="brcode,brno,brname,address,postno,teleno" colNm=4/>
		</td>
	</tr>
	<tr >
		<td align="center">
	  		<@CommonQueryMacro.Button id="btSave" />
		 	&nbsp;&nbsp;&nbsp;&nbsp;
			<@CommonQueryMacro.Button id="btCancel" />
	  	</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="javascript">
	
	var roleType = "${info.roleTypeList}";
	var op = "${op}";

	function initCallGetter_post(dataset) {
		var brcode = BankInfoManage_dataset.getValue("brcode");
		if (brcode == null || brcode == "") {
			BankInfoManage_dataset.setFieldReadOnly("brcode", false);
			//document.getElementById("unLock").style.disabled = "none";
			//document.getElementById("btResetPwd").style.disabled = "none";
		} else {
			BankInfoManage_dataset.setFieldReadOnly("brcode", true);
		}
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		v_postno = BankInfoManage_dataset.getValue("postno");
		if (isNaN(v_postno)) {
			alert("字段【邮政编码】必须为数字");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		} else if (v_postno.indexOf('-') != -1) {
			alert("字段【邮政编码】必须为数字");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		} else if (v_postno.length < 6 && v_postno.length != 0) {
			alert("字段【邮政编码】必须为6位");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		}
		var v_teleno = BankInfoManage_dataset.getValue("teleno");
		var validChar = "0123456789-";
		for (var i = 0; i < v_teleno.length; i++) {
			var c = v_teleno.charAt(i);
			if (validChar.indexOf(c) == -1) {
				alert("字段【联系电话】只能包含-和数字");
				BankInfoManage_dataset.setValue2("teleno", "");
				return false;
			}
		}
		return true;
	}
	
	//保存后刷新当前页
	function btSave_postSubmit(button) {
		alert("保存成功");
		//button.url = "/fpages/blacklistManage/ftl/BankInfoEntry.ftl";
		flushCurrentPage();
	}
	
	function btCancel_onClickCheck(button) {
		//unloadPageWindows("partWin");
		//button.url = "/fpages/blacklistManage/ftl/BankInfoEntry.ftl";
		flushCurrentPage();
		//return false;
	}
	
	function flushCurrentPage() {
		BankInfoManage_dataset
				.flushData(BankInfoManage_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>