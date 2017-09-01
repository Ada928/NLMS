<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="������Ϣά��">
<@CommonQueryMacro.CommonQuery id="Management_branchManage" init="true" submitMode="current">
	<table width="100%" align="left">
		<tr>
   			<td valign="top" colspan="2">
   				<@CommonQueryMacro.Interface id="intface" label="������ѯ" colNm=4 showButton="true" />
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
	      				<@CommonQueryMacro.Group id="group1" label="������Ϣά��"
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
	//��λһ����¼
	function locate(id) {
		var record = Management_branchManage_dataset.find(["brcode"], [id]);
		if (record) {
			Management_branchManage_dataset.setRecord(record);
		}
	}
	
	function datatable1_opr_onRefresh(cell, value, record) {
		if (record) {//�����ڼ�¼ʱ
			var lock = record.getValue("lock");
			var id = record.getValue("brcode");
			if (roleType.indexOf("12") > -1 || roleType.indexOf("13") > -1
					|| roleType.indexOf("14") > -1
					|| roleType.indexOf("15") > -1) {
				cell.innerHTML = "<center><a href=\"Javascript:void(0);\" style=\"color:#666666\" title=\"��¼�����������ܲ���\">�޸�</a></center>";
			} else {
				cell.innerHTML = "<a href=\"JavaScript:openModifyWindow('" + id
						+ "')\">�޸�</a>" + " <a href=\"JavaScript:doDel('" + id
						+ "')\">ɾ��</a>";
			}
		} else {//�������ڼ�¼ʱ
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
			if (confirm("ȷ��ɾ��������¼��")) {
				Management_branchManage_dataset.setParameter("delet", "T");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("ȷ�ϻָ�������¼��")) {
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
			alert("ɾ����¼�ɹ� !");
		} else {
			alert("ɾ����¼ʧ�� !");
		}
		button.url = "#";
		//ˢ�µ�ǰҳ
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

	//չʾ�Աȹ��ܵ�js
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
		loadPageWindows("partWin", "��������",
				"/fpages/regonization/ftl/branchManageDetail.ftl", paramMap,
				"winZone");
	}

	function btStatus_onClickCheck(button) {
		var status = Management_branchManage_dataset.getValue("status");
		if (status == '0') {
			if (confirm("ȷ�Ͻ��û�������Ϊ��Ч?")) {
				Management_branchManage_dataset.setParameter("statu", "1");
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("ȷ�Ͻ��û�������Ϊ��Ч?")) {
				Management_branchManage_dataset.setParameter("statu", "0");
				return true;
			} else {
				return false;
			}
		}
	}

	function btStatus_postSubmit(button) {
		alert("���óɹ�");
		flushCurrentPage();
	}

	function Management_branchManage_dataset_afterScroll(dataset) {
		/*
		var  v_brcode = Management_branchManage_dataset.getValue("brcode");
		var  v_brclass = Management_branchManage_dataset.getValue("brclass");
		 //���ݿ��еļ�¼��
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
				alert("�ֶΡ��������롿����Ϊ����");
				Management_branchManage_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.indexOf('-') != -1) {
				alert("�ֶΡ��������롿����Ϊ����");
				Management_branchManage_dataset.setValue2("postno", "");
				return false;
			} else if (v_postno.length < 6 && v_postno.length != 0) {
				alert("�ֶΡ��������롿����Ϊ6λ");
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
					alert("�ֶΡ���ϵ�绰��ֻ�ܰ���-������");
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
		//���ݿ��еļ�¼��
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
		alert("����ɹ�");
		subwindow_signWindow.close();
		flushCurrentPage();
	}

	function btSave_onClickCheck(button) {
		return checkValue();
	}

	function checkValue() {
		if (Management_branchManage_dataset.getValue("blnUpBrcode") == ""
				&& Management_branchManage_dataset.getValue("brclass") != "1") {
			alert("�ֶ�[�ϼ�����]��ӦΪ�ա�");
			return false;
		}

		if (Management_branchManage_dataset.getValue("brclass") == "") {
			alert("�ֶ�[��������]��ӦΪ�ա�");
			return false;
		}
		return true;
	}

	/* function brclass_DropDown_onSelect(dropDown, record, editor) {
		var brclass = record.getValue("data").trim();
		var length = Management_branchManage_dataset.length;
		var flag = true;
		if (length > 1 && brclass == "1") {
			alert("ֻ����һ������!");
			flag = false;
		}
		if (!flag) {
			return false;
		}

		return true;
	} */

	//ȥ��ҳ�桰�������С��ֶΣ�����ѡ�С��ϼ��������ֶ�ʱ���Զ������������С���ֵ
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

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		Management_branchManage_dataset
				.flushData(Management_branchManage_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>