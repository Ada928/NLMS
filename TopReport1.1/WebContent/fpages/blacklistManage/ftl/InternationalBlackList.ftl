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
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],opr"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr>
	    <td colspan="2">
	      	<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="100%" resize="true" defaultZoom="normal" minimize="false"
					maximize="false" closure="true" float="true" exclusive="true" position="center" show="false" >
	      		<div align="center">
	      			<@CommonQueryMacro.Group id="group1" label="���ʺ���������" width="100%" height="100%"
	        			  fieldStr="id,accountType,certificateType,certificateNumber,clientName,clientEnglishName,lastModifyOperator" colNm=4/>
	      		</div>
	     	</@CommonQueryMacro.FloatWindow>	
	  	</td>
  	</tr>
	<tr style="display:none">
		<td><@CommonQueryMacro.Button id= "btDel" /></td>
		<td><@CommonQueryMacro.Button id= "btModify" /></td>
		<td><@CommonQueryMacro.Button id= "btDetail" /></td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var currentTlrno = "${info.tlrNo}";
	var roleType = "${info.roleTypeList}";
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
            if (roleType.indexOf("12") > -1 
					|| roleType.indexOf("13") > -1
					|| roleType.indexOf("14") > -1
					|| roleType.indexOf("15") > -1) {
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
        //locate(id);
        btModify.click();  
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

   /*  function showDetail(id, osta) {
		locate(id);
		btDetail.click();
    } */
    
    function showDetail(id) {
		//locate(id);
		//btDetail.click();
		subwindow_signWindow.show();
	}
	//�ظ�������,�ͷ�dataset
	function signWindow_floatWindow_beforeClose(subwindow) {
		InternationalBlackList_dataset.cancelRecord();
		return true;
	}
	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}

    function btSave_onClickCheck(button) {
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
   
    function btAdd_onClick() {
		locate(id);
		InternationalBlackList_dataset.insertRecord();
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
        flushCurrentPage();
    }

    //ˢ�µ�ǰҳ
    function flushCurrentPage() {
        InternationalBlackList_dataset.flushData(InternationalBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
