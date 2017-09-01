<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="机构信息维护">
<@CommonQueryMacro.CommonQuery id="BranchEntry" init="true" submitMode="current">
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
  		<tr align="center">
			<td><@CommonQueryMacro.Button id= "btDel" /></td>
			<td><@CommonQueryMacro.Button id= "btModify" /></td>
		</tr>
   </table>
</@CommonQueryMacro.CommonQuery>

<script language="javascript">
	var roleType = "${info.roleTypeList}";

	BranchEntry_dataset.setParameter("statu", "T");
	//定位一条记录
	function locate(id) {
		var record = BranchEntry_dataset.find(["brcode"], [id]);
		if (record) {
			BranchEntry_dataset.setRecord(record);
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
		var del = BranchEntry_dataset.getValue("del");
		if (del == 'false') {
			if (confirm("确认删除该条记录？")) {
				BranchEntry_dataset.setParameter("delet", "T");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认恢复该条记录？")) {
				BranchEntry_dataset.setParameter("delet", "F");
				return true;
			} else {
				return false;
			}
		}
	}

	function btDel_postSubmit(button) {
		var del = BranchEntry_dataset.getValue("del");
		if (del == 'false') {
			alert("删除记录成功 !");
		} else {
			alert("删除记录失败 !");
		}
		button.url = "#";
		//刷新当前页
		flushCurrentPage();
	}

	/* function openModifyWindow(id) {
		locate(id);
		BranchEntry_dataset.setFieldReadOnly("brno", true);
		BranchEntry_dataset.setFieldReadOnly("brname", false);
		BranchEntry_dataset.setFieldReadOnly("address", false);
		BranchEntry_dataset.setFieldReadOnly("postno", false);
		BranchEntry_dataset.setFieldReadOnly("teleno", false);
		BranchEntry_dataset.setFieldReadOnly("brclass", false);
		BranchEntry_dataset.setFieldReadOnly("blnUpBrcode", false);
		BranchEntry_dataset.setFieldReadOnly("blnManageBrcode",
				false);
		BranchEntry_dataset.setFieldReadOnly("brattr", false);
		BranchEntry_dataset
				.setFieldReadOnly("otherAreaFlag", false);

		subwindow_signWindow.show();
	} */
	
	//修改功能
	function openModifyWindow(id) {
		locate(id);
		BranchEntry_dataset.setParameter("brcode", id);
		window.location.href = "${contextPath}/fpages/regonization/ftl/BranchManage.ftl?opType=edit&brcode="+id;
	}

	function btAdd_onClick(button) {
		BranchEntry_dataset.insertRecord();
		window.location.href = "${contextPath}/fpages/regonization/ftl/BranchManage.ftl?opType=add";
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
		var status = BranchEntry_dataset.getValue("status");
		if (status == '0') {
			if (confirm("确认将该机构设置为有效?")) {
				BranchEntry_dataset.setParameter("statu", "1");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("确认将该机构设置为无效?")) {
				BranchEntry_dataset.setParameter("statu", "0");
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

	function BranchEntry_dataset_afterScroll(dataset) {
		/*
		var  v_brcode = BranchEntry_dataset.getValue("brcode");
		var  v_brclass = BranchEntry_dataset.getValue("brclass");
		 //数据库中的记录。
		 if ( v_brcode!="" ){
		   BranchEntry_dataset.setFieldReadOnly("brno",true);
		   BranchEntry_dataset.setFieldReadOnly("brname",false);
		 }else{
		   BranchEntry_dataset.setFieldReadOnly("brno",false);
		   BranchEntry_dataset.setFieldReadOnly("brname",false);
		 }
		 if ( v_brclass =="1" ){
		 	BranchEntry_dataset.setFieldReadOnly("blnUpBrcode",true);
		 }else{
		 	BranchEntry_dataset.setFieldReadOnly("blnUpBrcode",false);
		 }
		 return true;
		 */
		var lock = BranchEntry_dataset.getValue("lock");
		if (isTrue(lock)) {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}

	function BranchEntry_dataset_afterChange(dataset, field) {
		if (field.name == "postno") {
			v_postno = BranchEntry_dataset.getValue("postno");
			if (isNaN(v_postno)) {
				alert("字段【邮政编码】必须为数字");
				BranchEntry_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.indexOf('-') != -1) {
				alert("字段【邮政编码】必须为数字");
				BranchEntry_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.length < 6 && v_postno.length != 0) {
				alert("字段【邮政编码】必须为6位");
				BranchEntry_dataset.setValue2("postno", "");
				return false;
			}
			return true;
		}
		if (field.name == "teleno") {
			var v_teleno = BranchEntry_dataset.getValue("teleno");
			var validChar = "0123456789-";
			for (var i = 0; i < v_teleno.length; i++) {
				var c = v_teleno.charAt(i);
				if (validChar.indexOf(c) == -1) {
					alert("字段【联系电话】只能包含-和数字");
					BranchEntry_dataset.setValue2("teleno", "");
					return false;
				}
			}
		}
	}

	/* function btAdd_onClick(button) {
		BranchEntry_dataset.insertRecord("end");

		BranchEntry_dataset.setValue("brno", "");
		BranchEntry_dataset.setValue("brname", "");
		BranchEntry_dataset.setValue("address", "");
		BranchEntry_dataset.setValue("postno", "");
		BranchEntry_dataset.setValue("teleno", "");
		BranchEntry_dataset.setValue("brclass", "");
		BranchEntry_dataset.setValue("blnUpBrcode", "");
		BranchEntry_dataset.setValue("blnManageBrcode", "");
		BranchEntry_dataset.setValue("brattr", "");
		BranchEntry_dataset.setValue("otherAreaFlag", "");

		var v_brcode = BranchEntry_dataset.getValue("brcode");
		//数据库中的记录。
		if (v_brcode != "") {
			BranchEntry_dataset.setFieldReadOnly("brno", true);
			BranchEntry_dataset.setFieldReadOnly("brname", false);
		} else {
			BranchEntry_dataset.setFieldReadOnly("brno", false);
			BranchEntry_dataset.setFieldReadOnly("brname", false);
		}

		BranchEntry_dataset.setFieldReadOnly("address", false);
		BranchEntry_dataset.setFieldReadOnly("postno", false);
		BranchEntry_dataset.setFieldReadOnly("teleno", false);
		BranchEntry_dataset.setFieldReadOnly("brclass", false);
		BranchEntry_dataset.setFieldReadOnly("blnUpBrcode", false);
		BranchEntry_dataset.setFieldReadOnly("blnManageBrcode",
				false);
		BranchEntry_dataset.setFieldReadOnly("brattr", false);
		BranchEntry_dataset
				.setFieldReadOnly("otherAreaFlag", false);
		subwindow_signWindow.show();
	} */

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
		if (BranchEntry_dataset.getValue("blnUpBrcode") == ""
				&& BranchEntry_dataset.getValue("brclass") != "1") {
			alert("字段[上级机构]不应为空。");
			return false;
		}

		if (BranchEntry_dataset.getValue("brclass") == "") {
			alert("字段[机构级别]不应为空。");
			return false;
		}
		return true;
	}

	/* function brclass_DropDown_onSelect(dropDown, record, editor) {
		var brclass = record.getValue("data").trim();
		var length = BranchEntry_dataset.length;
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
		BranchEntry_dataset.setValue2("blnBranchBrcode",
				blnUpBrcode);
		return true;

	}

	function signWindow_floatWindow_beforeClose(subwindow) {
		BranchEntry_dataset.cancelRecord();
		return true;
	}

	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}

	function BranchEntry_dataset_afterInsert(dataset, mode) {
		BranchEntry_dataset.setValue2("status", "1");
	}

	//刷新当前页
	function flushCurrentPage() {
		BranchEntry_dataset
				.flushData(BranchEntry_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>