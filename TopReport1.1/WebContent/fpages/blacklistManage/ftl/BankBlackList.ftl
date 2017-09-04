<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单管理">
<@CommonQueryMacro.CommonQuery id="BankBlackList" init="true" submitMode="current">
<table align="center" width="100%">
   	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4  showButton="true" />
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
    //定位一行记录
    function locate(id) {
        var record = BankBlackList_dataset.find(["id"], [id]);
        if (record) {
            BankBlackList_dataset.setRecord(record);
        }
    }

    //系统刷新单元格
    function datatable1_opr_onRefresh(cell, value, record) {
        if (record) {
            var op = record.getValue("operateState");
            var share = record.getValue("share");
            var id = record.getValue("id");
            var tempHtml = "<center>";
            if (roleType.indexOf("10") > -1 || roleType.indexOf("11") > -1 || roleType.indexOf("12") > -1 ) {
            	tempHtml += "<a href=\"JavaScript:openModifyWindow('" + id + "')\">修改</a> ";
            	tempHtml += "<a href=\"JavaScript:doDel('" + id + "')\">删除</a>";
            } else if (roleType.indexOf("13") > -1){
            	if(op == "2"){
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\">通过</a> ";
            		tempHtml += "<a href=\"JavaScript:doVerify('" + id + "')\">不通过</a> ";
            	}
            } else if (roleType.indexOf("14") > -1){
            	if(op == "3"){
            		tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\">通过</a> ";
                	tempHtml += "<a href=\"JavaScript:doApprove('" + id + "')\">不通过</a> ";
            	}
            } else if (roleType.indexOf("15") > -1){
            	if(share == 'false'){
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\">确认共享</a> ";
            	} else {
            		tempHtml += "<a href=\"JavaScript:doShare('" + id + "')\">取消共享</a> ";
            	}
            } else {
            	cell.innerHTML = "";
            }
            cell.innerHTML = tempHtml + "</center>";
        } else {
            cell.innerHTML = "";
        }
    }
    
	//修改功能
    function openModifyWindow(id) {
        locate(id);
		BankBlackList_dataset.setParameter("id", id);
		window.location.href = "${contextPath}/fpages/blacklistManage/ftl/BankBlackListManage.ftl?opType=edit&id="+id;
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
        loadPageWindows("partWin", "商行黑名单详细信息", "/fpages/blacklistManage/ftl/BankBlackListDetail.ftl", paramMap, "winZone");
    }

    /* function btSave_onClickCheck(button) {
        var id = BankBlackList_dataset.getValue("id");
        var certificateNumber = BankBlackList_dataset.getValue("certificateNumber");
        var certificateType = BankBlackList_dataset.getValue("certificateType");
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
    function btSave_postSubmit(button) {
    	alert("保存成功");
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

    function doVerify(id) {
        locate(id);
        btVerify.click();
    }
    
    function btVerify_onClickCheck(button) {
        var opState = BankBlackList_dataset.getValue("operateState");
        if (opState == '2') {
            if (confirm("确认通过审核该条记录？")) {
            	BankBlackList_dataset.setParameter("sure", "T");
                return true;
            } else {
                return false;
            }
        } else if (opState == '3') {
            if (confirm("确认取消审核该条记录？")) {
            	BankBlackList_dataset.setParameter("sure", "F");
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btVerify_postSubmit(button) {
        alert("设置成功");
        button.url = "#";
        //刷新当前页
        flushCurrentPage();
    }
    
    function doApprove(id) {
        locate(id);
        btApprove.click();
    }

    function btApprove_onClickCheck(button) {
        var opState = BankBlackList_dataset.getValue("operateState");
        if (opState == '3') {
            if (confirm("确认通过审批该条记录？")) {
            	BankBlackList_dataset.setParameter("sure", "T");
                return true;
            } else {
                return false;
            }
        } else if (opState == '4') {
            if (confirm("确认取消审批该条记录？")) {
            	BankBlackList_dataset.setParameter("sure", "F");
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btApprove_postSubmit(button) {
        alert("设置成功");
        button.url = "#";
        //刷新当前页
        flushCurrentPage();
    }
    
    function doShare(id) {
        locate(id);
        btShare.click();
    }

    function btShare_onClickCheck(button) {
        var share = BankBlackList_dataset.getValue("share");
        if (share == false) {
            if (confirm("确认分享该条记录？")) {
                return true;
            } else {
                return false;
            }
        } else if (share == true) {
            if (confirm("确认分享该条记录？")) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    function btShare_postSubmit(button) {
        alert("设置成功");
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
        BankBlackList_dataset.cancelRecord();
        return true;
    }

    function signWindow_floatWindow_beforeHide(subwindow) {
        return signWindow_floatWindow_beforeClose(subwindow);
    }

    //刷新当前页
    function flushCurrentPage() {
        BankBlackList_dataset.flushData(BankBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
