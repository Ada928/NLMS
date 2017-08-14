<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="商行黑名单管理">
<@CommonQueryMacro.CommonQuery id="BankBlackList" init="false" submitMode="current"  navigate="false">
<table align="left" width="800px">
   <tr>
      	<td colspan="2">
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件"  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	 </tr>
	 <tr>
		 <td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd" 
			fieldStr="partyId,bankCode"  width="100%" hasFrame="true"/>
		</td>
	 </tr>
	 <tr>
		<td>
			<div style="display:none">
				<@CommonQueryMacro.Button id= "btDel" />
			 </div>
		</td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>
<script language="javascript">
	
</script>
</@CommonQueryMacro.page>
