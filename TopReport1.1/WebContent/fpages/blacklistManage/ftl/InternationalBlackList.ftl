<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���ʺ���������">
<@CommonQueryMacro.CommonQuery id="InternationalBlackList" init="true" submitMode="current"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4/>
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd" 
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],lastModifyOperator,opr"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr>
      	<td colspan="2">
      		<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="80%" resize="true" 
      			defaultZoom="normal" minimize="false" maximize="false" closure="true" float="true" 
      			exclusive="true" position="center" show="false" >
      			<div align="center">
      				<@CommonQueryMacro.Group id="group1" label="���ʺ�����ά��"
        			  fieldStr="id,blacklistType,accountType,certificateType,certificateNumber,clientName,clientEnglishName,isValid,validDate" colNm=4/>
        			<br/>
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
	var currentTlrno = "${info.tlrNo}";
	var type = "${info.tlrType}";
    //��λһ�м�¼
    function locate(id) {
        var record = InternationalBlackList_dataset.find(["id"], [id]);
        if (record) {
            InternationalBlackList_dataset.setRecord(record);
        }
    }

    //ϵͳˢ�µ�Ԫ��
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            //var lock = record.getValue("lock");
            var id = record.getValue("id");
            if(type == "1" || type == "2" || type == "3"){
            	cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key='�޸�' /></a> &nbsp; <a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key='ɾ��' /></a></center>";
            } else {
            	cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id + "')\"><@bean.message key='�޸�'/></a> &nbsp; <a href=\"JavaScript:doDel('" + id + "')\"><@bean.message key='ɾ��'/></a>";
            }
        } else {
            cell.innerHTML = "";
        }
    }
    
	//�޸Ĺ���
    function openModifyWindow(id) {
        locate(id);
        InternationalBlackList_dataset.setFieldReadOnly("id", true);
        InternationalBlackList_dataset.setFieldReadOnly("accountCode", false);
        InternationalBlackList_dataset.setFieldReadOnly("certificateType", false);
        InternationalBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        InternationalBlackList_dataset.setFieldReadOnly("clientName", false);
        InternationalBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        InternationalBlackList_dataset.setFieldReadOnly("blacklistType", false);
        InternationalBlackList_dataset.setFieldReadOnly("isValid", false);
        InternationalBlackList_dataset.setFieldReadOnly("validDate", false);
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
        loadPageWindows("partWin", "���ʺ�������ϸ��Ϣ", "/fpages/blacklistManage/ftl/InternationalBlackListDetail.ftl", paramMap, "winZone");
    }

    function btModOrAdd_onClickCheck(button) {
        var id = InternationalBlackList_dataset.getValue("id");
        var certificateNumber = InternationalBlackList_dataset.getValue("certificateNumber");
        var certificateType = InternationalBlackList_dataset.getValue("certificateType");
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
    function btModOrAdd_postSubmit(button) {
        button.url = "#";
        subwindow_signWindow.close();
        flushCurrentPage();
    }
    
    function btAdd_onClick(button) {
        btNewClick();
    }
    
    //��������
    function btNewClick() {
        InternationalBlackList_dataset.insertRecord("end");

        InternationalBlackList_dataset.setValue("id", "");
        InternationalBlackList_dataset.setValue("accountCode", "");
        InternationalBlackList_dataset.setValue("certificateType", "");
        InternationalBlackList_dataset.setValue("certificateNumber", "");
        InternationalBlackList_dataset.setValue("clientName", "");
        InternationalBlackList_dataset.setValue("clientEnglishName", "");
        InternationalBlackList_dataset.setValue("blacklistType", "");
        InternationalBlackList_dataset.setValue("isValid", "");
        InternationalBlackList_dataset.setValue("validDate", "");
        InternationalBlackList_dataset.setFieldReadOnly("id", false);
        InternationalBlackList_dataset.setFieldReadOnly("accountCode", false);
        InternationalBlackList_dataset.setFieldReadOnly("certificateType", false);
        InternationalBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        InternationalBlackList_dataset.setFieldReadOnly("clientName", false);
        InternationalBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        InternationalBlackList_dataset.setFieldReadOnly("blacklistType", false);
        InternationalBlackList_dataset.setFieldReadOnly("isValid", false);
        InternationalBlackList_dataset.setFieldReadOnly("validDate", false);
        subwindow_signWindow.show();
    }
    
    function btAdd_onClickCheck(button) {
        var id = InternationalBlackList_dataset.getValue("id");
        var certificateNumber = InternationalBlackList_dataset.getValue("certificateNumber");
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

	//ȡ������
    function btCancel_onClickCheck(button) {
        //�رո�������
        subwindow_signWindow.close();
    }

    //�ظ�������,�ͷ�dataset
    function signWindow_floatWindow_beforeClose(subwindow) {
        InternationalBlackList_dataset.cancelRecord();
        return true;
    }

    function signWindow_floatWindow_beforeHide(subwindow) {
        return signWindow_floatWindow_beforeClose(subwindow);
    }

    //ˢ�µ�ǰҳ
    function flushCurrentPage() {
        InternationalBlackList_dataset.flushData(InternationalBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
