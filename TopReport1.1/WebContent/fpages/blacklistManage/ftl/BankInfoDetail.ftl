<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro> 
<@CommonQueryMacro.page title="������ϸ��Ϣ">
<@CommonQueryMacro.CommonQuery id="BankInfoDetail" init="true" submitMode="current" navigate="false">
<table align="center" width="100%">
	<tr>
		<td align="left">
			<@CommonQueryMacro.Group id="BankInfoDetailGroup" label="������ϸ��Ϣ"
				fieldStr="brno,brname,address,postno,teleno,status" colNm=4/>
		</td>
	</tr>
	<tr>
		<td align="center">
			<@CommonQueryMacro.Button id= "btClose"/>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>


<script language="javascript">
	function btClose_onClickCheck(button) {
		//window.history.go(-1);
		 //window.history.back(-1);
	}
</script>
</@CommonQueryMacro.page>
