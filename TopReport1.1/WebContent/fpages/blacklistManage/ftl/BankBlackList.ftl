<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign opType="${RequestParameters['opType']?default('')}" />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="商行黑名单管理">
<@CommonQueryMacro.CommonQuery id="BankBlackList" init="true" submitMode="current" navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td valign="top" colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4  />
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="btAdd" 
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],operateState,opr[200]"  
				width="100%" hasFrame="true"/><br/>
		</td>
	</tr>
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDel" /></td>
		<td><@CommonQueryMacro.Button id="btModify" /></td>
		<td><@CommonQueryMacro.Button id="btVerify" /></td>
		<td><@CommonQueryMacro.Button id="btApprove" /></td>
		<td><@CommonQueryMacro.Button id= "btDetail" /></td>
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

    function showDetail(id) {
		locate(id);
		btDetail.click();
    }

    function btAdd_onClick(button) {
    	BankBlackList_dataset.insertRecord();
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
    
    //刷新当前页
    function flushCurrentPage() {
        BankBlackList_dataset.flushData(BankBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
