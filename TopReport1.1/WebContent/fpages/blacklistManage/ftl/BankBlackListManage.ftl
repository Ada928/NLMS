<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<@CommonQueryMacro.page title="商行黑名单編輯">
<script type="text/javascript" src="${contextPath}/js/certifict_ident.js"></script>

<@CommonQueryMacro.CommonQuery id="BankBlackListManage" init="true" submitMode="current">
<table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id ="bankBlackListManageGroup" label="银行黑名单信息" 
				fieldStr="accountType,blacklistType,certificateType,certificateNumber,clientName,"+
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
        var clientName = BankBlackListManage_dataset.getValue("clientName");
        BankBlackListManage_dataset.setParameter("opSave", "save");
        
        if (certificateType == null || "" == certificateType) {
            alert("证件类型不能为空");
            return false;
        }
        
        if (certificateNumber == null || "" == certificateNumber) {
            alert("证件号不能为空");
            return false;
        }
        
		if (certificateType == "11") {
		  	Javascript:checkCard();
		 	
		 	//return false;
			        	
		}
        
        if (clientName == null || "" == clientName) {
            alert("客户名字不能为空");
            return false;
        }
        
        return true;
        
    }

    //保存后刷新当前页
    function btSave_postSubmit(button) {
    	alert("保存成功。");
    	closeWin(true);
    }
    
    
    function btQueryVerify_onClickCheck(button) {
        var id = BankBlackListManage_dataset.getValue("id");
        var certificateNumber = BankBlackListManage_dataset.getValue("certificateNumber");
        var certificateType = BankBlackListManage_dataset.getValue("certificateType");
        var clientName = BankBlackListManage_dataset.getValue("clientName");
        
        BankBlackListManage_dataset.setParameter("opSave", "queryVerify");
        if (certificateType == null || "" == certificateType) {
            alert("证件类型不能为空");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("证件号不能为空");
            return false;
        } 
        if (certificateType == "11" && 18 > certificateNumber.length) {
            alert("身份证号不能短于18位");
            return false;
        }
         if (clientName == null || "" == clientName) {
            alert("客户名字不能为空");
            return false;
        }
        return true;
    }
	
	//取消
	function btCancel_onClick(button){
		closeWin();
	}
	
    //提交后刷新当前页
    function btQueryVerify_postSubmit(button) {
    	alert("提交审核成功，请等待审核通过。");
    	closeWin(true);
    }

</script>
</@CommonQueryMacro.page>
