<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="����������������">
<@CommonQueryMacro.CommonQuery id="PoliceBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="������������������ϸ��Ϣ" 
  					fieldStr="id,accountType,certificateType,certificateNumber,clientName,blacklistType,valid,validDate,lastModifyOperator" colNm=2/>
  			</td>
  		</tr>
  		<tr valign="top">
      		   <td align="center">
					<left><@CommonQueryMacro.Button id= "btClose"/></left>
      			</td>
      		</tr>
	</table>
	</@CommonQueryMacro.CommonQuery>
</#if>
<script language="javascript">
</script>

</@CommonQueryMacro.page>
