<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="岗位管理">
<@CommonQueryMacro.CommonQuery id="RoleInfoEntry" init="true" submitMode="current">
<table align="left" width="100%">
	<tr valign="center">
		<td valign="top" colspan="2">
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=6 />
		</td>
	</tr>
	<tr>
    	<td valign="top">
      		<@CommonQueryMacro.PagePilot id="pagePilot1" maxpagelink="9" showArrow="true" pageCache="false"/>
      	</td>	
	</tr>
    <tr>
      	<td valign="top" colspan="2">
      		<@CommonQueryMacro.DataTable id ="datatable1" paginationbar="btAdd,-,btStatus"
      			 fieldStr="roleName,status,opr" readonly="true" width="100%"/></br>
      	</td>
    </tr>
     <tr>
      	<td style="display:none">
      		<@CommonQueryMacro.Button id= "btDel"/>
      		<@CommonQueryMacro.Button id= "btShowUser"/>
      		<@CommonQueryMacro.Button id= "btRoleAuthorityManagement"/>
      	</td>
      </tr>
</table>
</@CommonQueryMacro.CommonQuery>
<span style="display:none">
<@CommonQueryMacro.CommonQuery id="PosiNameCheck" init="false" navigate="false" >
</@CommonQueryMacro.CommonQuery>
</span>

<script language="JavaScript">
	var roleType = "${info.roleTypeList}";
	//定位一条记录
	function locate(id) {
		var record = RoleInfoEntry_dataset.find([ "id" ], [ id ]);
		if (record) {
			RoleInfoEntry_dataset.setRecord(record);
		}
	}

	function btAdd_onClick() {
		RoleInfoEntry_dataset.insertRecord();
		RoleInfoEntry_dataset.setParameter("op", "add");
		RoleInfoEntry_dataset.setParameter("id", "0");
	}

	function btStatus_onClickCheck(button) {
		var status = RoleInfoEntry_dataset.getValue("status");
		if (status == '0') {
			if (!confirm("确认将该岗位设置为有效?")) {
				return false;
			}
		} else {
			if (!confirm("确认将该岗位设置为无效?")) {
				return false;
			}
		}
	}

	function btStatus_postSubmit(button) {
		alert("设置成功");
		flushCurrentPage();
	}

	function datatable1_opr_onRefresh(cell, value, record) {
		if (record && record != null) {
			var id = record.getValue("id");
			var innerText = "";
			
			if (roleType.indexOf("10") >- 1  || roleType.indexOf("11") >- 1) {
				innerText = "<center><a href=\"JavaScript:rolePrivShow(" + id + ")\">岗位功能分配</a> "
			} else {
				innerText = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"记录已锁定，不能操作\">岗位功能分配</a> ";
			}
			
			innerText = innerText + " <a href=\"JavaScript:btRoleUserShow(" + id + ")\">查看人员</a>";
			cell.innerHTML = innerText + " <a href=\"JavaScript:doDel('" + id + "')\">删除</a></center>";
		} else {
			cell.innerHTML = "";
		}
	}
	
	function rolePrivShow(id) {
		locate(id);
		btRoleAuthorityManagement.click();
	}
	
	function btRoleUserShow(id) {
		locate(id);
		btShowUser.click();
	}
	
	function doDel(id) {
        locate(id);
        btDel.click();
    }

    function btDel_onClickCheck(button) {
    	var delet = RoleInfoEntry_dataset.getValue("del");
		if (delet == 'F') {
			if (!confirm("确认删除该岗位?")) {
				return false;
			}
		} else {
			if (!confirm("确认恢复该岗位?")) {
				return false;
			}
		}
    }
    
    function btDel_postSubmit(button) {
        alert("删除记录成功");
        button.url = "#";
        //刷新当前页
        flushCurrentPage();
    }
	
	function RoleInfoEntry_dataset_afterInsert(dataset, mode) {
		RoleInfoEntry_dataset.setValue2("status", "1");
	}

	function RoleInfoEntry_dataset_afterChange(dataset, field, value) {
		if (field.fieldName == "roleName") {
			PosiNameCheck_dataset.setParameter("roleName",
					RoleInfoEntry_dataset.getValue("roleName"));
			PosiNameCheck_dataset.flushData(0);
			var v_flag = PosiNameCheck_dataset.getValue("flag");
			if (v_flag == "true") {
				alert("该岗位名称已存在，请重新输入");
				RoleInfoEntry_dataset.setValue("roleName", "");
				return false;
			}
		}
	}
	
	function RoleInfoEntry_dataset_afterScroll(dataset) {
		var Lock = dataset.getValue("lock");
		if (Lock == "1") {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}
	
	//刷新当前页
	function flushCurrentPage() {
		RoleInfoEntry_dataset.flushData(RoleInfoEntry_dataset.pageIndex);
	}

	/* function datatable1_rolename_onRefresh(cell, value, record) {
		if (record != null) {
			var st = record.getValue("st");
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "','"
					+ st + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = ""
		}
	}
	
	//详细
	function showDetail(id, st) {
		locate(id);
		showWin("角色管理详细信息",
				"${contextPath}/fpages/system/ftl/RoleFuncMngWithEdit.ftl?id="
						+ id + "&st=" + st + "&flag=0", null, null, window);
	} */
</script>
</@CommonQueryMacro.page>
