<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign opType=RequestParameters["opType"]?default("")>
<@CommonQueryMacro.page title="商行黑名单管理">
<@CommonQueryMacro.CommonQuery id="BankBlackListManage" init="true">
<table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id ="bankBlackListManageGroup" label="岗位信息" 
				fieldStr="id,accountType,certificateType,certificateNumber,clientName,clientEnglishName,blacklistType,share,valid,validDate,blacklistedReason,unblacklistedReason" 
        	    colNm=4/>
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

<script language="JavaScript">
    
    function btSave_onClickCheck(button) {
        var id = BankBlackListManage_dataset.getValue("id");
        var certificateNumber = BankBlackListManage_dataset.getValue("certificateNumber");
        var certificateType = BankBlackListManage_dataset.getValue("certificateType");
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
        button.url = "/fpages/blacklistManage/ftl/BankBlackList.ftl";
    }
    

</script>
</@CommonQueryMacro.page>
