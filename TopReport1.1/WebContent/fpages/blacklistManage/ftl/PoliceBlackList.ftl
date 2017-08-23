<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="公安部黑名单管理">
<@CommonQueryMacro.CommonQuery id="PoliceBlackList" init="false" submitMode="current"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4/>
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
      				<@CommonQueryMacro.Group id="group1" label="公安部黑名单维护"
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
    //定位一行记录
    function locate(id) {
        var record = PoliceBlackList_dataset.find(["id"], [id]);
        if (record) {
            PoliceBlackList_dataset.setRecord(record);
        }
    }

    //系统刷新单元格
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            //var lock = record.getValue("lock");
            var id = record.getValue("id");
            //if(false){
            //	cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\"><@bean.message key="删除" /></a> &nbsp; <a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\"><@bean.message key="删除" /></a></center>";
            //} else {
            cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id + "')\"><@bean.message key='修改'/></a> &nbsp; <a href=\"JavaScript:doDel('" + id + "')\"><@bean.message key='删除'/></a>";
            //}
        } else {
            cell.innerHTML = "";
        }
    }
    
	//修改功能
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

    //展示对比功能的js
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
        loadPageWindows("partWin", "公安部黑名单详细信息", "/fpages/blacklistManage/ftl/PoliceBlackListDetail.ftl", paramMap, "winZone");
    }

    function btModOrAdd_onClickCheck(button) {
        var id = PoliceBlackList_dataset.getValue("id");
        var certificateNumber = PoliceBlackList_dataset.getValue("certificateNumber");
        var certificateType = PoliceBlackList_dataset.getValue("certificateType");
        if (id == null || "" == id) {
            alert("黑名单不能为空");
            return false;
        }
        if (certificateType == null || "" == certificateType) {
            alert("证件类型不能为空");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("证件号不能为空");
            return false;
        }
        return true;
    }

    //保存后刷新当前页
    function btModOrAdd_postSubmit(button) {
        button.url = "#";
        subwindow_signWindow.close();
        flushCurrentPage();
    }
    
    function btAdd_onClick(button) {
        btNewClick();
    }
    
    //新增功能
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
            alert("黑名单不能为空");
            return false;
        }
        if (certificateNumber == null || "" == certificateNumber) {
            alert("证件号不能为空");
            return false;
        }
        return true;
    }

    function doDel(id) {
        locate(id);
        btDel.click();
    }

    function btDel_onClickCheck(button) {
        return confirm("确认删除该条记录？");
    }
    
    function btDel_postSubmit(button) {
        alert("删除记录成功");
        button.url = "#";
        //刷新当前页
        flushCurrentPage();
    }

	//取消功能
    function btCancel_onClickCheck(button) {
        //关闭浮动窗口
        subwindow_signWindow.close();
    }

    //关浮动窗口,释放dataset
    function signWindow_floatWindow_beforeClose(subwindow) {
        PoliceBlackList_dataset.cancelRecord();
        return true;
    }

    function signWindow_floatWindow_beforeHide(subwindow) {
        return signWindow_floatWindow_beforeClose(subwindow);
    }

    //刷新当前页
    function flushCurrentPage() {
        PoliceBlackList_dataset.flushData(PoliceBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
