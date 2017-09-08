<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="银行机构信息维护">
<@CommonQueryMacro.CommonQuery id="BankInfoEntry" init="true" submitMode="current"  navigate="false">
	<table width="100%" align="left">
		<tr>
   			<td valign="top" colspan="2">
   				<@CommonQueryMacro.Interface id="intface" label="银行机构查询" colNm=4 />
        	</td>
        </tr>
		<tr>
   			<td valign="top">
   				<@CommonQueryMacro.PagePilot id="PagePilot"/>
   			</td>
  		</tr>
  		<tr>
      		<td colspan="2">
          		<@CommonQueryMacro.DataTable id ="datatable1" paginationbar="btAdd,-,btStatus" 
          			fieldStr="brno,brname,lock,opr" width="100%"  readonly="true"/><br/>
        	</td>
        </tr>
  		<tr align="center" style="display:none">
			<td><@CommonQueryMacro.Button id= "btDel" /></td>
			<td><@CommonQueryMacro.Button id= "btModify" /></td>
			<td><@CommonQueryMacro.Button id= "btDetail" /></td>
		</tr>
   </table>
</@CommonQueryMacro.CommonQuery>

<script language="javascript">
	var roleType = "${info.roleTypeList}";
	
	//定位一条记录
	function locate(id) {
		var record = BankInfoEntry_dataset.find(["brcode"], [id]);
		if (record) {
			BankInfoEntry_dataset.setRecord(record);
		}
	}
	
	function datatable1_opr_onRefresh(cell, value, record) {
		if (record) {//当存在记录时
			var lock = record.getValue("lock");
			var id = record.getValue("brcode");
			if (roleType.indexOf("12") > -1 || roleType.indexOf("13") > -1
					|| roleType.indexOf("14") > -1
					|| roleType.indexOf("15") > -1) {
				cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\">修改</a></center>";
			} else {
				cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id
						+ "')\">修改</a>" + " <a href=\"JavaScript:doDel('" + id
						+ "')\">删除</a></center>";
			}
		} else {//当不存在记录时
			cell.innerHTML = "&nbsp;";
		}
	}

	function doDel(id) {
		locate(id);
		btDel.click();
	}

	function btDel_onClickCheck(button) {
		var del = BankInfoEntry_dataset.getValue("del");
		if (del == 'false') {
			if (confirm("确认删除该条记录？")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认恢复该条记录？")) {
				return true;
			} else {
				return false;
			}
		}
	}

	function btDel_postSubmit(button) {
		var del = BankInfoEntry_dataset.getValue("del");
		if (del == 'false') {
			alert("删除记录成功 !");
		} else {
			alert("删除记录失败 !");
		}
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}
	
	//修改功能
	function openModifyWindow(id) {
		locate(id);
		btModify.click();
	}

	function btAdd_onClick() {
		locate(id);
		BankInfoEntry_dataset.insertRecord();
	}
	
	//展示对比功能的js
	function datatable1_brno_onRefresh(cell, value, record) {
		if (record != null) {
			var sta = record.getValue("st");
			var id = record.getValue("brcode");
			var brno = record.getValue("brno");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "','"
					+ sta + "')\">" + brno + "</a>";
		} else {
			cell.innerHTML = ""
		}
	}

	function showDetail(id, sta) {
		locate(id);
		btDetail.click();
	}

	function btStatus_onClickCheck(button) {
		var status = BankInfoEntry_dataset.getValue("status");
		if (status == '0') {
			if (confirm("确认将该机构设置为有效?")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认将该机构设置为无效?")) {
				return true;
			} else {
				return false;
			}
		}
	}

	function btStatus_postSubmit(button) {
		alert("设置成功");
		flushCurrentPage();
	}

	function BankInfoEntry_dataset_afterScroll(dataset) {
		var lock = BankInfoEntry_dataset.getValue("lock");
		if (isTrue(lock)) {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}

	//刷新当前页
	function flushCurrentPage() {
		BankInfoEntry_dataset
				.flushData(BankInfoEntry_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>