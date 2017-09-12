<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<@CommonQueryMacro.page title="商行黑名单編輯">
<@CommonQueryMacro.CommonQuery id="BankBlackListManage" init="true">
<table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id ="bankBlackListManageGroup" label="银行黑名单信息" 
				fieldStr="bankCode,bankName,accountType,blacklistType,certificateType,certificateNumber,clientName,"+
					"clientEnglishName,valid,validDate,blacklistedReason,unblacklistedReason" 
        	    colNm=4/>
		</td>
	</tr>
	<tr >
		<td  align="center">
	  		<@CommonQueryMacro.Button id="btSave" />
		 	&nbsp;&nbsp;&nbsp;&nbsp;
	  		<@CommonQueryMacro.Button id="btQueryVerify" />
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
        BankBlackListManage_dataset.setParameter("opSave", "save");
    
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
    	alert("保存成功。");
    }
    
    
    function btQueryVerify_onClickCheck(button) {
        var id = BankBlackListManage_dataset.getValue("id");
        var certificateNumber = BankBlackListManage_dataset.getValue("certificateNumber");
        var certificateType = BankBlackListManage_dataset.getValue("certificateType");
        BankBlackListManage_dataset.setParameter("opSave", "queryVerify");
   
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
    function btQueryVerify_postSubmit(button) {
    	alert("提交审核成功，请等待审核通过。");
    }

</script>
</@CommonQueryMacro.page>
