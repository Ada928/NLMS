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
				fieldStr="id[200],accountCode,certificateType,certificateNumber[200],clientName[260],clientEnglishName,blacklistedDate,blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,unblacklistedReason,lastModifyOperator,operateState,opr"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr>
      	<td colspan="2">
      		<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="" resize="true" 
      			defaultZoom="normal" minimize="false" maximize="false" closure="true" float="true" 
      			exclusive="true" position="center" show="false" >
      			<div align="center">
      				<@CommonQueryMacro.Group id="group1" label="���к�����ά��"
        			  fieldStr="id,accountCode,certificateType,certificateNumber,clientName,clientEnglishName,blacklistType,isShare,isValid,validDate,unblacklistedReason" colNm=4/>
        			<br/>
      			</div>
      			<div align="center">
      				<@CommonQueryMacro.Button id="btModOrAdd" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			</div>
     		</@CommonQueryMacro.FloatWindow>
  		</td>
  	</tr>
	<tr style="display:none">
		<td><@CommonQueryMacro.Button id="btDel" /></td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	//��λһ�м�¼
	function locate(id) {
		var record = BankBlackList_dataset.find(["id"],[id]);
		if(record) {
			BankBlackList_dataset.setRecord(record);
		}
	}
	
	//ϵͳˢ�µ�Ԫ��
	function datatable1_opr_onRefresh(cell,value,record) {
		if(record) {
			//var lock = record.getValue("lock");
			var id = record.getValue("id");
			//if(false){
			//	cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key="ɾ��" /></a> &nbsp; <a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key="ɾ��" /></a></center>";
			//} else {
				cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('"+id+"')\"><@bean.message key='�޸�'/></a> &nbsp; <a href=\"JavaScript:doDel('"+id+"')\"><@bean.message key='ɾ��'/></a>";
			//}
		}else{
			cell.innerHTML = "";
		}
	}
	
	function btAdd_onClick(button) {
			btNewClick();
	}
	
	//ȡ������
	function btCancel_onClickCheck(button) {
		//�رո�������
		subwindow_signWindow.close();
	}
	
	//�ظ�������,�ͷ�dataset
	function signWindow_floatWindow_beforeClose(subwindow) {
		BankBlackList_dataset.cancelRecord();
		return true;
	}
	
	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}
	
	//չʾ�Աȹ��ܵ�js
	function datatable1_sid_onRefresh(cell, value, record){
		if(record!=null){
			var osta = record.getValue("operateState");
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('"+id+"','"+osta+"')\">"+value+"</a>";
		} else {
			cell.innerHTML = ""
		}
	}
	
	function btModOrAdd_onClickCheck(button){
		var id = BankBlackList_dataset.getValue("id");
		if(id == null || "" == id ) {
				alert("����������Ϊ��");
				return false;
			}
		return true;
	}
	
	function showDetail(id,osta){
		var paramMap = new Map();
		paramMap.put("id",id);
		paramMap.put("operateState",osta);
		paramMap.put("action","detail");
		paramMap.put("flag","0");
		loadPageWindows("partWin", "���к�������ϸ��Ϣ","/fpages/blacklistManage/ftl/BankBlackListDetail.ftl", paramMap, "winZone");
	}


	//��������
	function btNewClick() {
	    BankBlackList_dataset.insertRecord("end"); 
			
		BankBlackList_dataset.setValue("id","");
		BankBlackList_dataset.setValue("accountCode","");
		BankBlackList_dataset.setValue("certificateType","");
		BankBlackList_dataset.setValue("certificateNumber","");
		BankBlackList_dataset.setValue("clientName","");
		BankBlackList_dataset.setValue("clientEnglishName","");
		BankBlackList_dataset.setValue("blacklistType","");
		BankBlackList_dataset.setValue("isShare","");
		BankBlackList_dataset.setValue("isValid","");
		BankBlackList_dataset.setValue("validDate","");
		BankBlackList_dataset.setValue("blacklistedReason","");
		subwindow_signWindow.show();
	}
	
	//�޸Ĺ���
	function openModifyWindow(id) {
		locate(id);
		BankBlackList_dataset.setFieldReadOnly("id",true);
		BankBlackList_dataset.setFieldReadOnly("accountCode",false);
		BankBlackList_dataset.setFieldReadOnly("certificateType",false);
		BankBlackList_dataset.setFieldReadOnly("certificateNumber",false);
		BankBlackList_dataset.setFieldReadOnly("clientName",false);
		BankBlackList_dataset.setFieldReadOnly("clientEnglishName",false);
		BankBlackList_dataset.setFieldReadOnly("blacklistType",false);
		BankBlackList_dataset.setFieldReadOnly("isShare",false);
		BankBlackList_dataset.setFieldReadOnly("isValid",false);
		BankBlackList_dataset.setFieldReadOnly("validDate",false);
		BankBlackList_dataset.setFieldReadOnly("unblacklistedReason",false);
		subwindow_signWindow.show();
	}

	function doDel(id) {
		locate(id);
		btDel.click();
	}
	
	function btDel_onClickCheck(button) {
		return confirm("ȷ��ɾ��������¼��");
	}
	function btDel_postSubmit(button) {
		alert("ɾ����¼�ɹ�");
		button.url="#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}
	
	//�����ˢ�µ�ǰҳ
	function btModOrAdd_postSubmit(button) {
		button.url="#";
		subwindow_signWindow.close();
		flushCurrentPage();
	}
	
	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankBlackList_dataset.flushData(BankBlackList_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
