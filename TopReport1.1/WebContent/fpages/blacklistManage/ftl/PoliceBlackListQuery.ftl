<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="��������������ѯ">
<@CommonQueryMacro.CommonQuery id="PoliceBlackListQuery" init="true" submitMode="current"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4/>
		</td>
	</tr>
  	<tr>
  		<td><@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true"  pageCache="false"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1" paginationbar="" 
				fieldStr="id[160],accountType,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],lastModifyOperator"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDetail" /></td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">

	//��λһ�м�¼
	function locate(id) {
		var record = PoliceBlackListQuery_dataset.find([ "id" ], [ id ]);
		if (record) {
			PoliceBlackListQuery_dataset.setRecord(record);
		}
	}

	//չʾ�Աȹ��ܵ�js
	function datatable1_id_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">"
					+ value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	function showDetail(id) {
		locate(id);
		btDetail.click();
	}
</script>
</@CommonQueryMacro.page>
