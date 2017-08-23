<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="���к���������">
<@CommonQueryMacro.CommonQuery id="BankBlackListShow" init="false" submitMode="current"  navigate="false">
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
			<@CommonQueryMacro.DataTable id="datatable1" 
				fieldStr="id[160],accountCode,certificateType,certificateNumber[160],clientName[280],clientEnglishName[280],blacklistedOperator,blacklistedReason,unblacklistedDate,unblacklistedOperator,unblacklistedReason,lastModifyOperator"  
				width="100%" hasFrame="true"/>
		</td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
    //��λһ�м�¼
    function locate(id) {
        var record = BankBlackList_dataset.find(["id"], [id]);
        if (record) {
            BankBlackListShow_dataset.setRecord(record);
        }
    }

    //չʾ�Աȹ��ܵ�js
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
        loadPageWindows("partWin", "���к�������ϸ��Ϣ", "/fpages/blacklistManage/ftl/BankBlackListDetail.ftl", paramMap, "winZone");
    }
    

    //ˢ�µ�ǰҳ
    function flushCurrentPage() {
        BankBlackListShow_dataset.flushData(BankBlackList_dataset.pageIndex);
    }

</script>
</@CommonQueryMacro.page>
