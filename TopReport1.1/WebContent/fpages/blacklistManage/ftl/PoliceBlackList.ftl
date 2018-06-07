<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="公安部黑名单管理">
<@CommonQueryMacro.CommonQuery id="PoliceBlackList" init="true" submitMode="current" navigate="false">
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
				fieldStr="id[140],accountType,accountCode[140],certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],cardBkBookNo[240],opr"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDel" /></td>
		<td><@CommonQueryMacro.Button id="btModify" /></td>
		<td><@CommonQueryMacro.Button id="btDetail" /></td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
	var currentTlrno = "${info.tlrNo}";
	var roleType = "${info.roleTypeList}";
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
        //btModify.click();
        showWin("公安部黑名单修改","${contextPath}/fpages/blacklistManage/ftl/PoliceBlacklistManage.ftl?op=edit&id="+id,"window","flushCurrentPage()",window);
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

    function showDetail(id) {
       //locate(id);
       //btDetail.click();
       //window.location = "${contextPath}/fpages/blacklistManage/ftl/PoliceBlackListDetail.ftl?op=detail&reType=manage&id="+id;
       showWin("公安部黑名单详细信息","${contextPath}/fpages/blacklistManage/ftl/PoliceBlackListDetail.ftl?op=detail&reType=manage&id="+id,"window","flushCurrentPage()",window);
    }
	
	
	
    function btAdd_onClick(button){
		//locate(id);
		//PoliceBlackList_dataset.insertRecord();
		showWin("新增公安部黑名单信息","${contextPath}/fpages/blacklistManage/ftl/PoliceBlacklistManage.ftl?op=add","window","flushCurrentPage()",window);
    }
 

    function doDel(id) {
        //locate(id);
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
        PoliceBlackList_dataset.flushData(PoliceBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
