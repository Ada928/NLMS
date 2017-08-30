<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackList" init="true" submitMode="current">
<table align="center" width="100%">
   	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4  showButton="true" />
		</td>
	</tr>
  	<tr>
  		<td valign="top">
  			<@CommonQueryMacro.PagePilot id="PagePilot"/>
  		</td>
	</tr>
	<tr>
		<td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd" 
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,unblacklistedReason,lastModifyOperator,operateState,opr[200]"  
				width="100%" hasFrame="true"/><br/>
		</td>
	</tr>
	<tr>
      	<td colspan="2">
      		<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="50%" resize="true" 
      			defaultZoom="normal" minimize="false" maximize="false" closure="true" float="true" 
      			exclusive="true" position="center" show="false" >
      			<div align="center">
      				<@CommonQueryMacro.Group id="group1" label="���к�����ά��"
        			  fieldStr="id,accountType,certificateType,certificateNumber,clientName,clientEnglishName,blacklistType,share,valid,validDate,blacklistedReason,unblacklistedReason" colNm=4/>
        			<br/>
      				<@CommonQueryMacro.Button id="btSave" />
      			</div>
     		</@CommonQueryMacro.FloatWindow>
  		</td>
  	</tr>
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDel" /></td>
		<td><@CommonQueryMacro.Button id="btVerify" /></td>
		<td><@CommonQueryMacro.Button id="btApprove" /></td>
		<td><@CommonQueryMacro.Button id="btShare" /></td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var currentTlrno = "${info.tlrNo}";
	var roleType = "${info.roleTypeList}";
    //��λһ�м�¼
    function locate(id) {
        var record = BankBlackList_dataset.find(["id"], [id]);
        if (record) {
            BankBlackList_dataset.setRecord(record);
        }
    }

    //ϵͳˢ�µ�Ԫ��
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            var op = record.getValue("operateState");
            var share = record.getValue("share");
            var id = record.getValue("id");
            var tempHtml = "<center>";
            if (roleType.indexOf("10") > -1 || roleType.indexOf("11") > -1 || roleType.indexOf("12") > -1 ) {
            	tempHtml += "<a href=\"JavaScript:openModifyWindow('" + id + "')\"><@bean.message key='�޸�'/></a> ";
            	tempHtml += "<a href=\"JavaScript:doDel('" + id + "')\"><@bean.message key='ɾ��'/></a>";
            } else if (roleType.indexOf("13") > -1){
            	if(op == "2"){
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\"><@bean.message key='ͨ��'/></a> ";
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\"><@bean.message key='��ͨ��'/></a> ";
            	}
            } else if (roleType.indexOf("14") > -1){
            	if(op == "3"){
            		tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\"><@bean.message key='ͨ��'/></a> ";
                	tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\"><@bean.message key='��ͨ��'/></a> ";
            	}
            } else if (roleType.indexOf("15") > -1){
            	if(share == 'false'){
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\"><@bean.message key='ȷ�Ϲ���'/></a> ";
            	} else {
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\"><@bean.message key='ȡ������'/></a> ";
            	}
            } else {
            	cell.innerHTML = "";
            }
            cell.innerHTML = tempHtml + "</center>";
        } else {
            cell.innerHTML = "";
        }
    }
    
	//�޸Ĺ���
    function openModifyWindow(id) {
        locate(id);
        BankBlackList_dataset.setFieldReadOnly("id", true);
        BankBlackList_dataset.setFieldReadOnly("accountType", false);
        BankBlackList_dataset.setFieldReadOnly("certificateType", false);
        BankBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        BankBlackList_dataset.setFieldReadOnly("clientName", false);
        BankBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        BankBlackList_dataset.setFieldReadOnly("blacklistType", false);
        if(roleType.indexOf("12") > -1){
        	BankBlackList_dataset.setFieldReadOnly("share", true);
            BankBlackList_dataset.setFieldReadOnly("unblacklistedReason", true);
        } else{
        	BankBlackList_dataset.setFieldReadOnly("share", false);
            BankBlackList_dataset.setFieldReadOnly("unblacklistedReason", false);
        }
        BankBlackList_dataset.setFieldReadOnly("valid", false);
        BankBlackList_dataset.setFieldReadOnly("validDate", false);
        BankBlackList_dataset.setFieldReadOnly("blacklistedReason", false);
        subwindow_signWindow.show();
    }

    //չʾ�Աȹ��ܵ�js
    function datatable1_id_onRefresh(cell, value, record) {
        if (record) {
            var osta = record.getValue("operateState");
            var id = record.getValue("id");
            cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "','" + osta + "')\">" + value + "</a>";
        } else {
            cell.innerHTML = "";
        }
    }

    function showDetail(id, osta) {
        var paramMap = new Map();
        paramMap.put("id", id);
        paramMap.put("osta", osta);
        paramMap.put("action", "detail");
        paramMap.put("flag", "0");
        loadPageWindows("partWin", "���к�������ϸ��Ϣ", "/fpages/blacklistManage/ftl/BankBlackListDetail.ftl", paramMap, "winZone");
    }

    function btSave_onClickCheck(button) {
        var id = BankBlackList_dataset.getValue("id");
        var certificateNumber = BankBlackList_dataset.getValue("certificateNumber");
        var certificateType = BankBlackList_dataset.getValue("certificateType");
        if (id == null || "" == id) {
            alert("����������Ϊ��");
            return false;
        }
        if (certificateType == null || "" == certificateType) {
            alert("֤�����Ͳ���Ϊ��");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("֤���Ų���Ϊ��");
            return false;
        }
        return true;
    }

    //�����ˢ�µ�ǰҳ
    function btSave_postSubmit(button) {
    	alert("����ɹ�");
        button.url = "#";
        subwindow_signWindow.close();
        flushCurrentPage();
    }
    
    function btAdd_onClick(button) {
        btNewClick();
    }
    
    //��������
    function btNewClick() {
        BankBlackList_dataset.insertRecord("end");

        BankBlackList_dataset.setValue("id", "");
        BankBlackList_dataset.setValue("accountType", "");
        BankBlackList_dataset.setValue("certificateType", "");
        BankBlackList_dataset.setValue("certificateNumber", "");
        BankBlackList_dataset.setValue("clientName", "");
        BankBlackList_dataset.setValue("clientEnglishName", "");
        BankBlackList_dataset.setValue("blacklistType", "");
        BankBlackList_dataset.setValue("share", "");
        BankBlackList_dataset.setValue("valid", "");
        BankBlackList_dataset.setValue("validDate", "");
        BankBlackList_dataset.setValue("blacklistedReason", "");
        BankBlackList_dataset.setFieldReadOnly("id", false);
        BankBlackList_dataset.setFieldReadOnly("accountType", false);
        BankBlackList_dataset.setFieldReadOnly("certificateType", false);
        BankBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        BankBlackList_dataset.setFieldReadOnly("clientName", false);
        BankBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        BankBlackList_dataset.setFieldReadOnly("blacklistType", false);
        BankBlackList_dataset.setFieldReadOnly("share", false);
        BankBlackList_dataset.setFieldReadOnly("valid", false);
        BankBlackList_dataset.setFieldReadOnly("validDate", false);
        BankBlackList_dataset.setFieldReadOnly("blacklistedReason", false);
        BankBlackList_dataset.setFieldReadOnly("unblacklistedReason", true);
        subwindow_signWindow.show();
    }
    
    function btAdd_onClickCheck(button) {
        var id = BankBlackList_dataset.getValue("id");
        var certificateNumber = BankBlackList_dataset.getValue("certificateNumber");
        if (id == null || "" == id) {
            alert("����������Ϊ��");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("֤���Ų���Ϊ��");
            return false;
        }
        return true;
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
        button.url = "#";
        //ˢ�µ�ǰҳ
        flushCurrentPage();
    }

    function doVerify(id) {
        locate(id);
        btVerify.click();
    }
    
    function btVerify_onClickCheck(button) {
        var opState = BankBlackList_dataset.getValue("operateState");
        if (opState == '2') {
            if (confirm("ȷ��ͨ����˸�����¼��")) {
            	BankBlackList_dataset.setParameter("sure", "T");
                return true;
            } else {
                return false;
            }
        } else if (opState == '3') {
            if (confirm("ȷ��ȡ����˸�����¼��")) {
            	BankBlackList_dataset.setParameter("sure", "F");
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btVerify_postSubmit(button) {
        alert("���óɹ�");
        button.url = "#";
        //ˢ�µ�ǰҳ
        flushCurrentPage();
    }
    
    function doApprove(id) {
        locate(id);
        btApprove.click();
    }

    function btApprove_onClickCheck(button) {
        var opState = BankBlackList_dataset.getValue("operateState");
        if (opState == '3') {
            if (confirm("ȷ��ͨ������������¼��")) {
            	BankBlackList_dataset.setParameter("sure", "T");
                return true;
            } else {
                return false;
            }
        } else if (opState == '4') {
            if (confirm("ȷ��ȡ������������¼��")) {
            	BankBlackList_dataset.setParameter("sure", "F");
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btApprove_postSubmit(button) {
        alert("���óɹ�");
        button.url = "#";
        //ˢ�µ�ǰҳ
        flushCurrentPage();
    }
    
    function doShare(id) {
        locate(id);
        btShare.click();
    }

    function btShare_onClickCheck(button) {
        var share = BankBlackList_dataset.getValue("share");
        if (share == false) {
            if (confirm("ȷ�Ϸ��������¼��")) {
                return true;
            } else {
                return false;
            }
        } else if (share == true) {
            if (confirm("ȷ�Ϸ��������¼��")) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btShare_postSubmit(button) {
        alert("���óɹ�");
        button.url = "#";
        //ˢ�µ�ǰҳ
        flushCurrentPage();
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

    //ˢ�µ�ǰҳ
    function flushCurrentPage() {
        BankBlackList_dataset.flushData(BankBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
