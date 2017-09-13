<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign reType="${RequestParameters['reType']?default('')}" />
<@CommonQueryMacro.page title="商行黑名单管理">
<@CommonQueryMacro.CommonQuery id="BankBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left" width="100%">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="商行黑名单管理详细信息" 
  					fieldStr="id,bankCode,bankName,accountType,brattr,certificateType,blacklistType,share,valid,del,"+
						"approve,certificateNumber,clientName,clientEnglishName,validDate,blacklistedDate,"+
						"blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,"+
						"unblacklistedReason,createDate,lastModifyDate,lastModifyOperator,remarks"
					colNm=4/>
  			</td>
  		</tr>
  		<tr align="center">
      		   <td valign="left">
					<left><@CommonQueryMacro.Button id= "btClose"/></left>
      			</td>
      		</tr>
	</table>
</@CommonQueryMacro.CommonQuery>
 <script language="javascript">
 	var reType = "${reType}";
 	//alert(reType);
 	function btClose_onClick(button){
 		if(reType == "entry"){
			window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistEntry.ftl";
		} else if(reType == "approve"){
			window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistApprove.ftl";
		} else if(reType == "verify"){
			window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistVerify.ftl";
		} else if(reType == "edit"){
			window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistEdit.ftl";
		}
 	}
</script>

</@CommonQueryMacro.page>
