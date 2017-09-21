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
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],opr"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr>
	    <td colspan="2">
	      	<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="100%" resize="true" defaultZoom="normal" minimize="false"
					maximize="false" closure="true" float="true" exclusive="true" position="center" show="false" >
	      		<div align="center">
	      			<@CommonQueryMacro.Group id="group1" label="公安部黑名单详情" width="100%" height="100%"
	        			  fieldStr="id,blacklistType,accountType,certificateType,certificateNumber,"+
	        			  "clientName,clientEnglishName,bankCode,blackListOrganization,valid,"+
	        			  "del,validDate,operateState,createDate,lastModifyDate,lastModifyOperator,remarks" colNm=4/>
	      		</div>
	     	</@CommonQueryMacro.FloatWindow>	
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

   /*  function showDetail(id) {
       //locate(id);
       btDetail.click();
    } */
    function showDetail(id) {
		//locate(id);
		//btDetail.click();
		subwindow_signWindow.show();
	}
	//关浮动窗口,释放dataset
	function signWindow_floatWindow_beforeClose(subwindow) {
		PoliceBlackList_dataset.cancelRecord();
		return true;
	}
	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}

    function btAdd_onClick(id) {
		//locate(id);
		PoliceBlackList_dataset.insertRecord();
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
