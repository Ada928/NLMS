<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
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
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDel" /></td>
		<td><@CommonQueryMacro.Button id="btModify" /></td>
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
            	tempHtml += "<a href=\"JavaScript:openModifyWindow('" + id + "')\">�޸�</a> ";
            	tempHtml += "<a href=\"JavaScript:doDel('" + id + "')\">ɾ��</a>";
            } else if (roleType.indexOf("13") > -1){
            	if(op == "2"){
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\">ͨ��</a> ";
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\">��ͨ��</a> ";
            	}
            } else if (roleType.indexOf("14") > -1){
            	if(op == "3"){
            		tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\">ͨ��</a> ";
                	tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\">��ͨ��</a> ";
            	}
            } else if (roleType.indexOf("15") > -1){
            	if(share == 'false'){
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\">ȷ�Ϲ���</a> ";
            	} else {
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\">ȡ������</a> ";
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
		BankBlackList_dataset.setParameter("id", id);
		window.location.href = "${contextPath}/fpages/blacklistManage/ftl/BankBlackListManage.ftl?opType=edit&id="+id;
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

    /* function btSave_onClickCheck(button) {
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
     */
    function btAdd_onClick(button) {
    	BankBlackList_dataset.insertRecord();
		BankBlackList_dataset.setParameter("id", "0");
		window.location.href = "${contextPath}/fpages/blacklistManage/ftl/BankBlackListManage.ftl?opType=add";
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
