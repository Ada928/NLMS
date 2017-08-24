<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="国际黑名单管理">
<@CommonQueryMacro.CommonQuery id="InternationalBlackList" init="true" submitMode="current"  navigate="false">
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
      				<@CommonQueryMacro.Group id="group1" label="国际黑名单维护"
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
    //定位一行记录
    function locate(id) {
        var record = InternationalBlackList_dataset.find(["id"], [id]);
        if (record) {
            InternationalBlackList_dataset.setRecord(record);
        }
    }

    //系统刷新单元格
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            //var lock = record.getValue("lock");
            var id = record.getValue("id");
            if(type == "1" || type == "2" || type == "3"){
            	cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\"><@bean.message key='修改' /></a> &nbsp; <a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\"><@bean.message key='删除' /></a></center>";
            } else {
            	cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id + "')\"><@bean.message key='修改'/></a> &nbsp; <a href=\"JavaScript:doDel('" + id + "')\"><@bean.message key='删除'/></a>";
            }
        } else {
            cell.innerHTML = "";
        }
    }
    
	//修改功能
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
        loadPageWindows("partWin", "国际黑名单详细信息", "/fpages/blacklistManage/ftl/InternationalBlackListDetail.ftl", paramMap, "winZone");
    }

    function btModOrAdd_onClickCheck(button) {
        var id = InternationalBlackList_dataset.getValue("id");
        var certificateNumber = InternationalBlackList_dataset.getValue("certificateNumber");
        var certificateType = InternationalBlackList_dataset.getValue("certificateType");
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
        InternationalBlackList_dataset.cancelRecord();
        return true;
    }

    function signWindow_floatWindow_beforeHide(subwindow) {
        return signWindow_floatWindow_beforeClose(subwindow);
    }

    //刷新当前页
    function flushCurrentPage() {
        InternationalBlackList_dataset.flushData(InternationalBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
