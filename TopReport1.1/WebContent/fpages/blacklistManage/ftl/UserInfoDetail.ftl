<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro> 
<#assign st=RequestParameters["st"]?default("")>
<#assign flag=RequestParameters["flag"]?default("")>
<@CommonQueryMacro.page title="用户详细信息">
	<table width="100%" align="left">
		<tr>
			<td width="50%" valign="top">
				<table align="left" width="100%">
					<tr align="center">
						<td width="100%" valign="top">
							<@CommonQueryMacro.CommonQuery id="UserInfoDetail" init="true" navigate="false" submitMode="all">
									<table frame=void class="grouptable" width="100%">
										<tr>
											<td align="center" nowrap class="labeltd" width="25%">操作员号 </td>
											<td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="tlrno"/></td>
											<td align="center" nowrap class="labeltd"  width="25%">操作员名称 </td>
											<td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="tlrName" /></td>
										</tr>
										<tr>
						                    <td align="center" nowrap class="labeltd" width="25%">有效状态</td>
										    <td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="flag"/></td>
										    <td align="center" nowrap class="labeltd" width="25%">锁定状态</td>
										    <td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="lock"/></td>
										</tr>
										<tr>
						                    <td align="center" nowrap class="labeltd" width="25%">登录状态</td>
										    <td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="status"/></td>
						                    <td align="center" nowrap class="labeltd" width="25%">生效日期</td>
										    <td class="datatd" width="25%"><@CommonQueryMacro.SingleField fId="effectDate"/></td>
										</tr>
								   </table>
							</@CommonQueryMacro.CommonQuery>
						</td>
					</tr>
					<tr>
						<td width="100%">
							<@CommonQueryMacro.CommonQuery id="UserInfoDetailRoleRel" init="true" submitMode="selected" navigate="false">
								<@CommonQueryMacro.GroupBox id="guoup2" label="岗位信息" expand="true">
									<table frame=void width="100%">
								      	<tr>
								      		<td valign="top">
													<@CommonQueryMacro.DataTable id ="datatable1" fieldStr="id[160],roleName" hasFrame="true" height="120" width="100%" readonly="false"/>
											</td>
									 	</tr>
									 </table>
								 </@CommonQueryMacro.GroupBox>
								 <br/>
							</@CommonQueryMacro.CommonQuery>
						</td>
					</tr>
					<tr align="center">
						<td>
							<@CommonQueryMacro.Button id="btCancel"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>		
<script language="javascript">
	function btCancel_postSubmit(button) {
		button.url = "/fpages/blacklistManage/ftl/UserInfoEntry.ftl";
	}
</script>
</@CommonQueryMacro.page>