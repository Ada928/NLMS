<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="����������������">
<@CommonQueryMacro.CommonQuery id="PoliceBlackList" init="false" submitMode="current"  navigate="false">
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
      				<@CommonQueryMacro.Group id="group1" label="������������ά��"
        			  fieldStr="id,accountType,certificateType,certificateNumber,clientName,clientEnglishName,blacklistType,isValid,validDate" colNm=4/>
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
    //��λһ�м�¼
    function locate(id) {
        var record = PoliceBlackList_dataset.find(["id"], [id]);
        if (record) {
            PoliceBlackList_dataset.setRecord(record);
        }
    }

    //ϵͳˢ�µ�Ԫ��
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            //var lock = record.getValue("lock");
            var id = record.getValue("id");
            //if(false){
            //	cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key="ɾ��" /></a> &nbsp; <a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\"><@bean.message key="ɾ��" /></a></center>";
            //} else {
            cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id + "')\"><@bean.message key='�޸�'/></a> &nbsp; <a href=\"JavaScript:doDel('" + id + "')\"><@bean.message key='ɾ��'/></a>";
            //}
        } else {
            cell.innerHTML = "";
        }
    }
    
	//�޸Ĺ���
    function openModifyWindow(id) {
        locate(id);
        PoliceBlackList_dataset.setFieldReadOnly("id", true);
        PoliceBlackList_dataset.setFieldReadOnly("accountCode", false);
        PoliceBlackList_dataset.setFieldReadOnly("certificateType", false);
        PoliceBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        PoliceBlackList_dataset.setFieldReadOnly("clientName", false);
        PoliceBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        PoliceBlackList_dataset.setFieldReadOnly("blacklistType", false);
        PoliceBlackList_dataset.setFieldReadOnly("isValid", false);
        PoliceBlackList_dataset.setFieldReadOnly("validDate", false);
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
        loadPageWindows("partWin", "��������������ϸ��Ϣ", "/fpages/blacklistManage/ftl/PoliceBlackListDetail.ftl", paramMap, "winZone");
    }

    function btModOrAdd_onClickCheck(button) {
        var id = PoliceBlackList_dataset.getValue("id");
        var certificateNumber = PoliceBlackList_dataset.getValue("certificateNumber");
        var certificateType = PoliceBlackList_dataset.getValue("certificateType");
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
        PoliceBlackList_dataset.insertRecord("end");

        PoliceBlackList_dataset.setValue("id", "");
        PoliceBlackList_dataset.setValue("accountCode", "");
        PoliceBlackList_dataset.setValue("certificateType", "");
        PoliceBlackList_dataset.setValue("certificateNumber", "");
        PoliceBlackList_dataset.setValue("clientName", "");
        PoliceBlackList_dataset.setValue("clientEnglishName", "");
        PoliceBlackList_dataset.setValue("blacklistType", "");
        PoliceBlackList_dataset.setValue("isValid", "");
        PoliceBlackList_dataset.setValue("validDate", "");
        PoliceBlackList_dataset.setFieldReadOnly("id", false);
        PoliceBlackList_dataset.setFieldReadOnly("accountCode", false);
        PoliceBlackList_dataset.setFieldReadOnly("certificateType", false);
        PoliceBlackList_dataset.setFieldReadOnly("certificateNumber", false);
        PoliceBlackList_dataset.setFieldReadOnly("clientName", false);
        PoliceBlackList_dataset.setFieldReadOnly("clientEnglishName", false);
        PoliceBlackList_dataset.setFieldReadOnly("blacklistType", false);
        PoliceBlackList_dataset.setFieldReadOnly("isValid", false);
        PoliceBlackList_dataset.setFieldReadOnly("validDate", false);
        subwindow_signWindow.show();
    }
    
    function btAdd_onClickCheck(button) {
        var id = PoliceBlackList_dataset.getValue("id");
        var certificateNumber = PoliceBlackList_dataset.getValue("certificateNumber");
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
        PoliceBlackList_dataset.cancelRecord();
        return true;
    }

    function signWindow_floatWindow_beforeHide(subwindow) {
        return signWindow_floatWindow_beforeClose(subwindow);
    }

    //ˢ�µ�ǰҳ
    function flushCurrentPage() {
        PoliceBlackList_dataset.flushData(PoliceBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
