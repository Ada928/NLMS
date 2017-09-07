<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="岗位管理">
<@CommonQueryMacro.CommonQuery id="ebankCustRoleMng" init="true" submitMode="current">
<table align="left" width="100%">
	<tr>
    	<td valign="top">
      		<@CommonQueryMacro.PagePilot id="pagePilot1" maxpagelink="9" showArrow="true" pageCache="false"/>
      	</td>	
	</tr>
    <tr>
      	<td valign="top" colspan="2">
      		<@CommonQueryMacro.DataTable id ="datatable1" paginationbar="btAddRole,-,btStatus"
      			 fieldStr="roleName,status,opr" readonly="true" width="100%"/></br>
      	</td>
    </tr>
    <tr>
     	<td valign="top" colspan="2">
      		<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="60%" resize="true" defaultZoom="normal" 
      			minimize="false" maximize="false" closure="true" float="true" exclusive="true" position="center" show="false" >
      			<div align="center">
      				<@CommonQueryMacro.Group id ="group1" label="详细信息" fieldStr="roleName"/>
        			<div align="left"><font color="red">*</font>保存后,请设置相关权限信息.</div>
      				<@CommonQueryMacro.Button id= "btSave"/>
      			</div>
     		 </@CommonQueryMacro.FloatWindow>
      	</td>
     </tr>
     <tr>
      	<td style="display:none">
      		<@CommonQueryMacro.Button id= "btDeleteRole"/>
      		<@CommonQueryMacro.Button id= "btRoleAuthorityManagement"/>
      		<@CommonQueryMacro.Button id= "btSelectRole"/>
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
		var record = ebankCustRoleMng_dataset.find([ "id" ], [ id ]);
		if (record) {
			ebankCustRoleMng_dataset.setRecord(record);
		}
	}

	function openRoleDtl(id) {
		locate(id);
		subwindow_signWindow.show();
	}

	function btAddRole_onClick() {
		ebankCustRoleMng_dataset.insertRecord();
		ebankCustRoleMng_dataset.setParameter("id", "0");
	}

	function btStatus_onClickCheck(button) {
		var status = ebankCustRoleMng_dataset.getValue("status");
		if (status == '0') {
			if (confirm("确认将该岗位设置为有效?")) {
				ebankCustRoleMng_dataset.setParameter("statu", "1");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认将该岗位设置为无效?")) {
				ebankCustRoleMng_dataset.setParameter("statu", "0");
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

	function datatable1_opr_onRefresh(cell, value, record) {
		//var roleType = ebankCustRoleMng_dataset.getValue("roleType");
		if (record && record != null) {
			var id = record.getValue("id");
			//var lock = record.getValue("isLock");
			var innerText = "";
			
			//if (roleType == "1") {
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
	
	function doDel(id) {
        locate(id);
        btDeleteRole.click();
    }

    function btDeleteRole_onClickCheck(button) {
    	var delet = ebankCustRoleMng_dataset.getValue("del");
		if (delet == 'false') {
			if (confirm("确认删除该岗位?")) {
				ebankCustRoleMng_dataset.setParameter("delet", "T");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认恢复该岗位?")) {
				ebankCustRoleMng_dataset.setParameter("delet", "F");
				return true;
			} else {
				return false;
			}
		}
    	
        return confirm("确认删除该条记录？");
    }
    
    function btDeleteRole_postSubmit(button) {
        alert("删除记录成功");
        button.url = "#";
        //刷新当前页
        flushCurrentPage();
    }
	
	function rolePrivShow(id) {
		locate(id);
		btRoleAuthorityManagement.click();
	}
	
	function btRoleUserShow(id) {
	/* 	var paramMap = new Map();
		paramMap.put("roleId", id);
		loadPageWindows("userWin", "查看人员",
				"/fpages/management/ftl/ebankCustRoleMngUser.ftl", paramMap,
				"winZone");
		return; */
		window.location.href = "${contextPath}/fpages/management/ftl/ebankCustRoleMngUser.ftl?roleId="+id;
	}
	
	function datatable1_rolename_onRefresh(cell, value, record) {
		if (record != null) {
			var st = record.getValue("st");
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "','"
					+ st + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = ""
		}
	}
	
	//function showDetail(id,st){
	//var paramMap = new Map();
	//paramMap.put("id",id);
	//paramMap.put("st",st);
	//paramMap.put("op","detail");
	//loadPageWindows("partWin", "角色管理详细信息","/fpages/management/ftl/RoleFuncMng.ftl", paramMap, "winZone");
	//}
	
	//详细
	function showDetail(id, st) {
		locate(id);
		showWin("角色管理详细信息",
				"${contextPath}/fpages/system/ftl/RoleFuncMngWithEdit.ftl?id="
						+ id + "&st=" + st + "&flag=0", null, null, window);
		//window.location.href = "${contextPath}/fpages/management/ftl/ebankCustRoleMngUser.ftl?roleId="+id;
	}

	function btSave_postSubmit() {
		alert('保存成功！');
		subwindow_signWindow.hide();
		ebankCustRoleMng_dataset.flushData(ebankCustRoleMng_dataset.pageIndex);
	}
	
	function signWindow_floatWindow_beforeClose(subwindow) {
		ebankCustRoleMng_dataset.cancelRecord();
		return true;
	}
	
	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}
	
	function ebankCustRoleMng_dataset_afterInsert(dataset, mode) {
		ebankCustRoleMng_dataset.setValue2("status", "1");
	}

	function ebankCustRoleMng_dataset_afterChange(dataset, field, value) {
		if (field.fieldName == "roleName") {
			PosiNameCheck_dataset.setParameter("roleName",
					ebankCustRoleMng_dataset.getValue("roleName"));
			PosiNameCheck_dataset.flushData(0);
			var v_flag = PosiNameCheck_dataset.getValue("flag");
			if (v_flag == "true") {
				alert("该岗位名称已存在，请重新输入");
				ebankCustRoleMng_dataset.setValue("roleName", "");
				return false;
			}
		}
	}
	
	function ebankCustRoleMng_dataset_afterScroll(dataset) {

		var Lock = dataset.getValue("lock");
		if (Lock == "1") {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}
	
	//刷新当前页
	function flushCurrentPage() {
		ebankCustRoleMng_dataset.flushData(ebankCustRoleMng_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>
