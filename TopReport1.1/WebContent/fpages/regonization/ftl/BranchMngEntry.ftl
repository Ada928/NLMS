<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="机构信息维护">
<@CommonQueryMacro.CommonQuery id="Management_branchManage" init="true" submitMode="current">
	<table width="100%" align="left">
		<tr>
   			<td valign="top" colspan="2">
   				<@CommonQueryMacro.Interface id="intface" label="机构查询" colNm=4 showButton="true" />
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
          			fieldStr="brno,brname,brclass,status,opr" width="100%"  readonly="true"/><br/>
        	</td>
        </tr>
        <tr>
      		<td colspan="2">
	      		<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="50%" resize="true" 
	      			defaultZoom="normal" minimize="false" maximize="false" closure="true" float="true" 
	      			exclusive="true" position="center" show="false" >
	      			<div align="center">
	      				<@CommonQueryMacro.Group id="group1" label="机构信息维护"
	        			  fieldStr="brno,brname,address,postno,teleno,brclass,blnUpBrcode,blnManageBrcode,brattr,otherAreaFlag" colNm=4/>
	        			 <br/>
	      				<@CommonQueryMacro.Button id="btSave"/>
	      			</div>
	     		 </@CommonQueryMacro.FloatWindow>
  			</td>
  		</tr>
  		<tr align="center">
		<td>
			<div style="display:none">
				<@CommonQueryMacro.Button id= "btDel" />
			 </div>
		</td>
	</tr>
   </table>
</@CommonQueryMacro.CommonQuery>

<script language="javascript">
	var roleType = "${info.roleTypeList}";
	//定位一条记录
	function locate(id) {
		var record = Management_branchManage_dataset.find(["brcode"], [id]);
		if (record) {
			Management_branchManage_dataset.setRecord(record);
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
				cell.innerHTML = "<a href=\"JavaScript:openModifyWindow('" + id
						+ "')\">修改</a>" + " <a href=\"JavaScript:doDel('" + id
						+ "')\">删除</a>";
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
		var del = Management_branchManage_dataset.getValue("del");
		if (del == 'false') {
			if (confirm("确认删除该条记录？")) {
				Management_branchManage_dataset.setParameter("delet", "T");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认恢复该条记录？")) {
				Management_branchManage_dataset.setParameter("delet", "F");
				return true;
			} else {
				return false;
			}
		}
	}

	function btDel_postSubmit(button) {
		var del = Management_branchManage_dataset.getValue("del");
		if (del == 'false') {
			alert("删除记录成功 !");
		} else {
			alert("删除记录失败 !");
		}
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}

	function openModifyWindow(id) {
		locate(id);
		Management_branchManage_dataset.setFieldReadOnly("brno", true);
		Management_branchManage_dataset.setFieldReadOnly("brname", false);
		Management_branchManage_dataset.setFieldReadOnly("address", false);
		Management_branchManage_dataset.setFieldReadOnly("postno", false);
		Management_branchManage_dataset.setFieldReadOnly("teleno", false);
		Management_branchManage_dataset.setFieldReadOnly("brclass", false);
		Management_branchManage_dataset.setFieldReadOnly("blnUpBrcode", false);
		Management_branchManage_dataset.setFieldReadOnly("blnManageBrcode",
				false);
		Management_branchManage_dataset.setFieldReadOnly("brattr", false);
		Management_branchManage_dataset
				.setFieldReadOnly("otherAreaFlag", false);

		subwindow_signWindow.show();
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
		var paramMap = new Map();
		paramMap.put("id", id);
		paramMap.put("st", sta);
		paramMap.put("action", "detail");
		paramMap.put("flag", "0");
		loadPageWindows("partWin", "机构管理",
				"/fpages/regonization/ftl/branchManageDetail.ftl", paramMap,
				"winZone");
	}

	function btStatus_onClickCheck(button) {
		var status = Management_branchManage_dataset.getValue("status");
		if (status == '0') {
			if (confirm("确认将该机构设置为有效?")) {
				Management_branchManage_dataset.setParameter("statu", "1");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认将该机构设置为无效?")) {
				Management_branchManage_dataset.setParameter("statu", "0");
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

	function Management_branchManage_dataset_afterScroll(dataset) {
		/*
		var  v_brcode = Management_branchManage_dataset.getValue("brcode");
		var  v_brclass = Management_branchManage_dataset.getValue("brclass");
		 //数据库中的记录。
		 if ( v_brcode!="" ){
		   Management_branchManage_dataset.setFieldReadOnly("brno",true);
		   Management_branchManage_dataset.setFieldReadOnly("brname",false);
		 }else{
		   Management_branchManage_dataset.setFieldReadOnly("brno",false);
		   Management_branchManage_dataset.setFieldReadOnly("brname",false);
		 }
		 if ( v_brclass =="1" ){
		 	Management_branchManage_dataset.setFieldReadOnly("blnUpBrcode",true);
		 }else{
		 	Management_branchManage_dataset.setFieldReadOnly("blnUpBrcode",false);
		 }
		 return true;
		 */
		var lock = Management_branchManage_dataset.getValue("lock");
		if (isTrue(lock)) {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}

	function Management_branchManage_dataset_afterChange(dataset, field) {
		if (field.name == "postno") {
			v_postno = Management_branchManage_dataset.getValue("postno");
			if (isNaN(v_postno)) {
				alert("字段【邮政编码】必须为数字");
				Management_branchManage_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.indexOf('-') != -1) {
				alert("字段【邮政编码】必须为数字");
				Management_branchManage_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.length < 6 && v_postno.length != 0) {
				alert("字段【邮政编码】必须为6位");
				Management_branchManage_dataset.setValue2("postno", "");
				return false;
			}
			return true;
		}
		if (field.name == "teleno") {
			var v_teleno = Management_branchManage_dataset.getValue("teleno");
			var validChar = "0123456789-";
			for (var i = 0; i < v_teleno.length; i++) {
				var c = v_teleno.charAt(i);
				if (validChar.indexOf(c) == -1) {
					alert("字段【联系电话】只能包含-和数字");
					Management_branchManage_dataset.setValue2("teleno", "");
					return false;
				}
			}
		}
	}

	function btAdd_onClick(button) {
		Management_branchManage_dataset.insertRecord("end");

		Management_branchManage_dataset.setValue("brno", "");
		Management_branchManage_dataset.setValue("brname", "");
		Management_branchManage_dataset.setValue("address", "");
		Management_branchManage_dataset.setValue("postno", "");
		Management_branchManage_dataset.setValue("teleno", "");
		Management_branchManage_dataset.setValue("brclass", "");
		Management_branchManage_dataset.setValue("blnUpBrcode", "");
		Management_branchManage_dataset.setValue("blnManageBrcode", "");
		Management_branchManage_dataset.setValue("brattr", "");
		Management_branchManage_dataset.setValue("otherAreaFlag", "");

		var v_brcode = Management_branchManage_dataset.getValue("brcode");
		//数据库中的记录。
		if (v_brcode != "") {
			Management_branchManage_dataset.setFieldReadOnly("brno", true);
			Management_branchManage_dataset.setFieldReadOnly("brname", false);
		} else {
			Management_branchManage_dataset.setFieldReadOnly("brno", false);
			Management_branchManage_dataset.setFieldReadOnly("brname", false);
		}

		Management_branchManage_dataset.setFieldReadOnly("address", false);
		Management_branchManage_dataset.setFieldReadOnly("postno", false);
		Management_branchManage_dataset.setFieldReadOnly("teleno", false);
		Management_branchManage_dataset.setFieldReadOnly("brclass", false);
		Management_branchManage_dataset.setFieldReadOnly("blnUpBrcode", false);
		Management_branchManage_dataset.setFieldReadOnly("blnManageBrcode",
				false);
		Management_branchManage_dataset.setFieldReadOnly("brattr", false);
		Management_branchManage_dataset
				.setFieldReadOnly("otherAreaFlag", false);
		subwindow_signWindow.show();
	}

	//function btAdd_onClickCheck(button) {
	//	return checkValue();
	//}

	function btSave_postSubmit(button) {
		button.url = "#";
		alert("保存成功");
		subwindow_signWindow.close();
		flushCurrentPage();
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		if (Management_branchManage_dataset.getValue("blnUpBrcode") == ""
				&& Management_branchManage_dataset.getValue("brclass") != "1") {
			alert("字段[上级机构]不应为空。");
			return false;
		}

		if (Management_branchManage_dataset.getValue("brclass") == "") {
			alert("字段[机构级别]不应为空。");
			return false;
		}
		return true;
	}

	/* function brclass_DropDown_onSelect(dropDown, record, editor) {
		var brclass = record.getValue("data").trim();
		var length = Management_branchManage_dataset.length;
		var flag = true;
		if (length > 1 && brclass == "1") {
			alert("只能有一个总行!");
			flag = false;
		}
		if (!flag) {
			return false;
		}

		return true;
	} */

	//去掉页面“归属分行”字段，但当选中“上级机构”字段时，自动给“归属分行”赋值
	function blnUpBrcode_DropDown_onSelect(dropDown, record, editor) {
		var blnUpBrcode = record.getValue("brcode").trim();
		Management_branchManage_dataset.setValue2("blnBranchBrcode",
				blnUpBrcode);
		return true;

	}

	function signWindow_floatWindow_beforeClose(subwindow) {
		Management_branchManage_dataset.cancelRecord();
		return true;
	}

	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}

	function Management_branchManage_dataset_afterInsert(dataset, mode) {
		Management_branchManage_dataset.setValue2("status", "1");
	}

	//刷新当前页
	function flushCurrentPage() {
		Management_branchManage_dataset
				.flushData(Management_branchManage_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>