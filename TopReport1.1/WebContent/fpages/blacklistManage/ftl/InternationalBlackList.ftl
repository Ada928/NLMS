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
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],opr"  
				width="100%" hasFrame="true"/>
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
            if (roleType.indexOf("12") > -1 
					|| roleType.indexOf("13") > -1
					|| roleType.indexOf("14") > -1
					|| roleType.indexOf("15") > -1) {
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
        //locate(id);
        btModify.click();  
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
		locate(id);
		btDetail.click();
    }

    function btSave_onClickCheck(button) {
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
   
    function btAdd_onClick() {
		locate(id);
		InternationalBlackList_dataset.insertRecord();
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
        flushCurrentPage();
    }

    //刷新当前页
    function flushCurrentPage() {
        InternationalBlackList_dataset.flushData(InternationalBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
