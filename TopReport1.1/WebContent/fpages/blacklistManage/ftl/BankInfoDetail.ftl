<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro> 
<@CommonQueryMacro.page title="银行详细信息">
<@CommonQueryMacro.CommonQuery id="BankInfoDetail" init="true" submitMode="current" navigate="false">
<table align="center" width="100%">
	<tr>
		<td align="left">
			<@CommonQueryMacro.Group id="BankInfoDetailGroup" label="银行详细信息"
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
		closeWin(true);
		//window.history.go(-1);
		 //window.history.back(-1);
	}
</script>
</@CommonQueryMacro.page>
