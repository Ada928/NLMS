<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="���ʺ���������">
<@CommonQueryMacro.CommonQuery id="InternationalBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left" width="100%">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="���ʺ�����������ϸ��Ϣ" 
  					fieldStr="id,accountType,certificateType,certificateNumber,clientName,blacklistType,valid,validDate,lastModifyOperator" colNm=4/>
  			</td>
  		</tr>
  		<tr valign="top">
      		<td align="center">
				<left><@CommonQueryMacro.Button id= "btClose"/></left>
      		</td>
      	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
 <script language="javascript">
</script>

</@CommonQueryMacro.page>
