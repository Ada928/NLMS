<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign reType="${RequestParameters['reType']?default('')}" />
<@CommonQueryMacro.page title="公安部黑名单管理">
<@CommonQueryMacro.CommonQuery id="PoliceBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left"  width="100%">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="公安部黑名单详细信息" 
  					fieldStr="id,accountType,accountCode,certificateType,certificateNumber,clientName,clientEnglishName,"+
  					"cardBkBookNo,blackListedOrganization,blacklistType,valid,validDate,lastModifyOperator,remarks" colNm=4/>
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

	var reType = "${reType}";
 	function btClose_onClick(button){
 		closeWin();
// 		if(reType == "query"){
//			window.location = "${contextPath}/fpages/blacklistManage/ftl/PoliceBlackListQuery.ftl";
//		} else if(reType == "manage"){
//			window.location = "${contextPath}/fpages/blacklistManage/ftl/PoliceBlackList.ftl";
//		} 
 	}
 	
</script>

</@CommonQueryMacro.page>
