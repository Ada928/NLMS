<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="���к�����������ϸ��Ϣ" 
  					fieldStr="id,accountType,certificateType,certificateNumber,clientName,blacklistType,share,valid,validDate,blacklistedDate,blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,unblacklistedReason,lastModifyOperator" colNm=2/>
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
