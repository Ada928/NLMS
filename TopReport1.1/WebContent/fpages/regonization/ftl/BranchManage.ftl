<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="机构信息维护">
<@CommonQueryMacro.CommonQuery id="BranchManage" init="true" submitMode="current">
   <table align="center" width="100%">
	<tr>
		<td  align="left">
				<@CommonQueryMacro.Group id="BranchManageGroup" label="机构信息维护"
	        			  fieldStr="brcode,brno,brname,address,postno,teleno,brclass,blnUpBrcode,blnManageBrcode,brattr,otherAreaFlag" colNm=4/>
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

<script language="javascript">
</script>
</@CommonQueryMacro.page>