<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign op=RequestParameters["op"]?default("")>
<@CommonQueryMacro.page title="���к�������݋">
<@CommonQueryMacro.CommonQuery id="BankBlackListManage" init="true">
<table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id ="bankBlackListManageGroup" label="���к�������Ϣ" 
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
    	alert("����ɹ���");
    }
    
    
    function btQueryVerify_onClickCheck(button) {
        var id = BankBlackListManage_dataset.getValue("id");
        var certificateNumber = BankBlackListManage_dataset.getValue("certificateNumber");
        var certificateType = BankBlackListManage_dataset.getValue("certificateType");
        BankBlackListManage_dataset.setParameter("opSave", "queryVerify");
   
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
    function btQueryVerify_postSubmit(button) {
    	alert("�ύ��˳ɹ�����ȴ����ͨ����");
    }

</script>
</@CommonQueryMacro.page>
