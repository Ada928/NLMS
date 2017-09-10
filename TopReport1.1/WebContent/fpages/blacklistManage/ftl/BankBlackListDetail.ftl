<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
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
</script>

</@CommonQueryMacro.page>
