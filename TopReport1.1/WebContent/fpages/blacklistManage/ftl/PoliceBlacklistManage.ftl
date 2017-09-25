<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign opType=RequestParameters["opType"]?default("")>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="公安部黑名单维护">
<@CommonQueryMacro.CommonQuery id="PoliceBlacklistManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td align="left">
	       <@CommonQueryMacro.Group id="PoliceBlacklistManageGroup" label="公安部黑名单维护"
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
		var id = PoliceBlacklistManage_dataset.getValue("id");
		if (id == null || id == "") {
			PoliceBlacklistManage_dataset.setFieldReadOnly("id", false);
		} else {
			PoliceBlacklistManage_dataset.setFieldReadOnly("id", true);
		}
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		var id = PoliceBlacklistManage_dataset.getValue("id");
        var certificateNumber = PoliceBlacklistManage_dataset.getValue("certificateNumber");
        var certificateType = PoliceBlacklistManage_dataset.getValue("certificateType");
        if (id == null || "" == id) {
            alert("黑名单不能为空");
            return false;
        }
        if (certificateType == null || "" == certificateType) {
            alert("证件类型不能为空");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("证件号不能为空");
            return false;
        }
        return true;
	}
	
	//保存后刷新当前页
	function btSave_postSubmit(button) {
		alert("保存成功");
		//button.url = "/fpages/blacklistManage/ftl/PoliceBlacklist.ftl";
		flushCurrentPage();
	}
	
	function btCancel_onClickCheck(button) {
		//unloadPageWindows("partWin");
		//button.url = "/fpages/blacklistManage/ftl/PoliceBlacklist.ftl";
		flushCurrentPage();
		//return false;
	}
	
	function flushCurrentPage() {
		PoliceBlacklistManage_dataset
				.flushData(PoliceBlacklistManage_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>