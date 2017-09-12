<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���л�����Ϣά��">
<@CommonQueryMacro.CommonQuery id="BankInfoManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td align="left">
				<@CommonQueryMacro.Group id="BankInfoManageGroup" label="���л�����Ϣά��"
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
			alert("�ֶΡ��������롿����Ϊ����");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		} else if (v_postno.indexOf('-') != -1) {
			alert("�ֶΡ��������롿����Ϊ����");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		} else if (v_postno.length < 6 && v_postno.length != 0) {
			alert("�ֶΡ��������롿����Ϊ6λ");
			BankInfoManage_dataset.setValue2("postno", "");
			return false;
		}
		var v_teleno = BankInfoManage_dataset.getValue("teleno");
		var validChar = "0123456789-";
		for (var i = 0; i < v_teleno.length; i++) {
			var c = v_teleno.charAt(i);
			if (validChar.indexOf(c) == -1) {
				alert("�ֶΡ���ϵ�绰��ֻ�ܰ���-������");
				BankInfoManage_dataset.setValue2("teleno", "");
				return false;
			}
		}
		return true;
	}
	
	//�����ˢ�µ�ǰҳ
	function btSave_postSubmit(button) {
		alert("����ɹ�");
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