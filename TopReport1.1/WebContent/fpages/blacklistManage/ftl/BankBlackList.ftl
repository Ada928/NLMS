<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackList" init="false" submitMode="current"  navigate="false">
<table align="left" width="800px">
   <tr>
      	<td colspan="2">
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����"  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	 </tr>
	 <tr>
		 <td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd" 
			fieldStr="id[200],accountCode,certificateType,certificateNumber[200],clientName[260],blacklistedDate,blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,unblacklistedReason,lastModifyOperator,opr"  
			width="100%" hasFrame="true"/>
		</td>
	 </tr>
	
</table>

</@CommonQueryMacro.CommonQuery>
<script language="javascript">
	
</script>
</@CommonQueryMacro.page>
