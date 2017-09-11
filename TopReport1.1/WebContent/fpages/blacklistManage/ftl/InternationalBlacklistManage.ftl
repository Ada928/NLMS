<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign opType=RequestParameters["opType"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���ʺ�����ά��">
<@CommonQueryMacro.CommonQuery id="InternationalBlacklistManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td align="left">
	       <@CommonQueryMacro.Group id="InternationalBlacklistGroup" label="���ʺ�����ά��"
        		fieldStr="id,blacklistType,accountType,certificateType,certificateNumber,clientName,clientEnglishName,valid,validDate" colNm=4/>
        	<br/>
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
	var opType = "${opType}";

	function initCallGetter_post(dataset) {
		var id = InternationalBlacklistManage_dataset.getValue("id");
		if (id == null || id == "") {
			InternationalBlacklistManage_dataset.setFieldReadOnly("id", false);
		} else {
			InternationalBlacklistManage_dataset.setFieldReadOnly("id", true);
		}
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		var id = InternationalBlacklistManage_dataset.getValue("id");
        var certificateNumber = InternationalBlacklistManage_dataset.getValue("certificateNumber");
        var certificateType = InternationalBlacklistManage_dataset.getValue("certificateType");
        if (id == null || "" == id) {
            alert("����������Ϊ��");
            return false;
        }
        if (certificateType == null || "" == certificateType) {
            alert("֤�����Ͳ���Ϊ��");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("֤���Ų���Ϊ��");
            return false;
        }
        return true;
	}
	
	//�����ˢ�µ�ǰҳ
	function btSave_postSubmit(button) {
		alert("����ɹ�");
		//button.url = "/fpages/blacklistManage/ftl/InternationalBlacklist.ftl";
		flushCurrentPage();
	}
	
	function btCancel_onClickCheck(button) {
		//unloadPageWindows("partWin");
		//button.url = "/fpages/blacklistManage/ftl/InternationalBlacklist.ftl";
		flushCurrentPage();
		//return false;
	}
	
	function flushCurrentPage() {
		InternationalBlacklistManage_dataset
				.flushData(InternationalBlacklistManage_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>