<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="���л�����Ϣά��">
<@CommonQueryMacro.CommonQuery id="BankInfoEntry" init="true" submitMode="current"  navigate="false">
	<table width="100%" align="left">
		<tr>
   			<td valign="top" colspan="2">
   				<@CommonQueryMacro.Interface id="intface" label="���л�����ѯ" colNm=4 />
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
	
	//��λһ����¼
	function locate(id) {
		var record = BankInfoEntry_dataset.find(["brcode"], [id]);
		if (record) {
			BankInfoEntry_dataset.setRecord(record);
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
				cell.innerHTML = "<center><a href=\"JavaScript:openModifyWindow('" + id
						+ "')\">�޸�</a>" + " <a href=\"JavaScript:doDel('" + id
						+ "')\">ɾ��</a></center>";
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
		var del = BankInfoEntry_dataset.getValue("del");
		if (del == 'false') {
			if (confirm("ȷ��ɾ��������¼��")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("ȷ�ϻָ�������¼��")) {
				return true;
			} else {
				return false;
			}
		}
	}

	function btDel_postSubmit(button) {
		var del = BankInfoEntry_dataset.getValue("del");
		if (del == 'false') {
			alert("ɾ����¼�ɹ� !");
		} else {
			alert("ɾ����¼ʧ�� !");
		}
		button.url = "#";
		//ˢ�µ�ǰҳ
		flushCurrentPage();
	}
	
	//�޸Ĺ���
	function openModifyWindow(id) {
		locate(id);
		btModify.click();
	}

	function btAdd_onClick() {
		locate(id);
		BankInfoEntry_dataset.insertRecord();
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
		locate(id);
		btDetail.click();
	}

	function btStatus_onClickCheck(button) {
		var status = BankInfoEntry_dataset.getValue("status");
		if (status == '0') {
			if (confirm("ȷ�Ͻ��û�������Ϊ��Ч?")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (confirm("ȷ�Ͻ��û�������Ϊ��Ч?")) {
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

	function BankInfoEntry_dataset_afterScroll(dataset) {
		var lock = BankInfoEntry_dataset.getValue("lock");
		if (isTrue(lock)) {
			btStatus.disable(true);
		} else {
			btStatus.disable(false);
		}
	}

	//ˢ�µ�ǰҳ
	function flushCurrentPage() {
		BankInfoEntry_dataset
				.flushData(BankInfoEntry_dataset.pageIndex);
	}
</script>
</@CommonQueryMacro.page>