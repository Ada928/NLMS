<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="国际黑名单操作日志查询">
<@CommonQueryMacro.CommonQuery id="InternationBlackListLogQuery" init="true" submitMode="selected" navigate="false">
<table align="left" width="80%">
    <tr>
		<td>
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4 />
		</td>
	</tr>
    <tr>
		<td>
			<@CommonQueryMacro.PagePilot id="pagePilot1" maxpagelink="9" showArrow="true" pageCache="false"/>
		</td>
	</tr>
	<tr>
		<td>
			<@CommonQueryMacro.DataTable id ="datatable1" 
			fieldStr="tlrno,brcode,tlrIP,operateType,queryType,queryRecordNumber,message,createDate" readonly="true" width="100%"/></br>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript">


</script>
</@CommonQueryMacro.page>
