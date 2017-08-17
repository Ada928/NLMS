<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign ost="${RequestParameters['osta']}" />
<@CommonQueryMacro.page title="���к���������">
<#if ost=="2">
	<@CommonQueryMacro.CommonQuery id="BankBlackListDetail" init="true" submitMode="all" navigate="false">
	<table align="left">
	    <tr valign="top">
	  		<td valign="top">
	  			<FIELDSET name='group6' style="padding: 6px;">
					<LEGEND>���к�����������ϸ��Ϣ</LEGEND>
					<table frame=void width="100%" class="grouptable" id="detailTable">
						<tr>
		                  <td nowrap class="labeltd" colspan=2>�޸�ǰ</td>
						  <td nowrap class="labeltd" colspan=2>�޸ĺ�</td>
						</tr>
		            	<tr>
		                  <td nowrap class="labeltd">���������</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_id"/></td>
						  <td nowrap class="labeltd">���������</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="id"/></td>
						</tr>
		            	<tr>
		                  <td nowrap class="labeltd">�˻�����</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_accountCode"/></td>
						  <td nowrap class="labeltd">�˻�����</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="accountCode"/></td>
						</tr>
						<tr>
		                  <td nowrap class="labeltd">֤������</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_certificateType"/></td>
		                  <td nowrap class="labeltd">֤������</td>
		                  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="certificateType"/></td>
		                </tr>
		                <tr>
		                  <td nowrap class="labeltd">֤������</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_certificateNumber"/></td>
		                  <td nowrap class="labeltd">֤������</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="certificateNumber"/></td>
		                </tr>        <tr>
		                  <td nowrap class="labeltd">�ͻ�����</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_clientName"/></td>
						  <td nowrap class="labeltd">�ͻ�����</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="clientName"/></td>
						</tr>
		            	<tr>
		                  <td nowrap class="labeltd">��������</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_blacklistType"/></td>
						  <td nowrap class="labeltd">��������</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="blacklistType"/></td>
						</tr>
						<tr>
		                  <td nowrap class="labeltd">�Ƿ���</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_isShare"/></td>
		                  <td nowrap class="labeltd">�Ƿ���</td>
		                  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="isShare"/></td>
		                </tr>
		                <tr>
		                  <td nowrap class="labeltd">��Ч״̬</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_isValid"/></td>
		                  <td nowrap class="labeltd">��Ч״̬</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="isValid"/></td>
		                </tr> 
		            	<tr>
		                  <td nowrap class="labeltd">��Ч��</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_validDate"/></td>
						  <td nowrap class="labeltd">��Ч��</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="validDate"/></td>
						</tr>
						<tr>
		                  <td nowrap class="labeltd">����ʱ��</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_blacklistedDate"/></td>
		                  <td nowrap class="labeltd">����ʱ��</td>
		                  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="blacklistedDate"/></td>
		                </tr>
		                <tr>
		                  <td nowrap class="labeltd">���ڲ���Ա</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_blacklistedOperator"/></td>
		                  <td nowrap class="labeltd">���ڲ���Ա</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="blacklistedOperator"/></td>
		                </tr>        <tr>
		                  <td nowrap class="labeltd">����ԭ��</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_blacklistedReason"/></td>
						  <td nowrap class="labeltd">����ԭ��</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="blacklistedReason"/></td>
						</tr>
		            	<tr>
		                  <td nowrap class="labeltd">���ʱ��</td>
						  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_unblacklistedDate"/></td>
						  <td nowrap class="labeltd">���ʱ��</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="unblacklistedDate"/></td>
						</tr>
						<tr>
		                  <td nowrap class="labeltd">�������Ա</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_unblacklistedOperator"/></td>
		                  <td nowrap class="labeltd">�������Ա</td>
		                  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="unblacklistedOperator"/></td>
		                </tr>
		                <tr>
		                  <td nowrap class="labeltd">���ԭ��</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_unblacklistedReason"/></td>
		                  <td nowrap class="labeltd">���ԭ��</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="unblacklistedReason"/></td>
		                </tr>   
		                <tr>
		                  <td nowrap class="labeltd">����޸��û�</td>
		                  <td class="datatd" nowrap><@CommonQueryMacro.SingleField fId="old_lastModifyOperator"/></td>
		                  <td nowrap class="labeltd">����޸��û�</td>
						  <td class="datatd" nowrap ><@CommonQueryMacro.SingleField fId="lastModifyOperator"/></td>
		                </tr>        
					</table>
				</FIELDSET>
			<tr valign="top">
      			<td valign="CENTER">
					<left><@CommonQueryMacro.Button id= "btClose"/></left>
      			</td>
      		</tr> 
	  	</td>
	  </tr>
	</table>
	</@CommonQueryMacro.CommonQuery>
<#else>
	<@CommonQueryMacro.CommonQuery id="BankBlackListDetail" init="true" submitMode="all"  navigate="false">
	<table align="left">
      	<tr valign="top">
  			<td valign="center">
  				<@CommonQueryMacro.Group id="group1" label="���к�����������ϸ��Ϣ" fieldStr="old_id,old_accountCode,old_certificateType,old_certificateNumber,old_clientName,old_blacklistType,old_isShare,old_isValid,old_validDate,old_blacklistedDate,old_blacklistedOperator,old_blacklistedReason,old_unblacklistedDate,old_unblacklistedOperator,old_unblacklistedReason,old_lastModifyOperator" colNm=2/>
  			</td>
  		</tr>
  		<tr valign="top">
      		   <td valign="left">
					<left><@CommonQueryMacro.Button id= "btClose"/></left>
      			</td>
      		</tr>
	</table>
	</@CommonQueryMacro.CommonQuery>
</#if>
 <script language="javascript">
     function btClose_onClickCheck(button){
       unloadPageWindows("partWin");
       return false;
     }
</script>

</@CommonQueryMacro.page>
