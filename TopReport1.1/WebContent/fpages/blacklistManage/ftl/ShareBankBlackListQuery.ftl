<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
    <#assign bean=JspTaglibs[ "/WEB-INF/struts-bean.tld"] />
    <@CommonQueryMacro.page title="商行共享黑名单查询">
        <@CommonQueryMacro.CommonQuery id="ShareBankBlackListQuery" init="true" submitMode="current" navigate="false">
            <table align="center" width="100%">
                <tr>
                    <td colspan="2">
                        <@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4/></td>
                </tr>
                <tr>
                    <td>
                        <@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="9" showArrow="true" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <@CommonQueryMacro.DataTable id="datatable1" paginationbar="" fieldStr="id,accountType,certificateType,certificateNumber,clientName," + "clientEnglishName,bankName,share" width="100%" hasFrame="true" /></td>
                </tr>
                <tr align="center" style="display:none">
                    <td>
                        <@CommonQueryMacro.Button id="btDetail" /></td>
                </tr>
            </table>
</@CommonQueryMacro.CommonQuery>
<script language="JavaScript">
	//定位一行记录
	function locate(id) {
		var record = ShareBankBlackListQuery_dataset.find([ "id" ], [ id ]);
		if (record) {
			ShareBankBlackListQuery_dataset.setRecord(record);
		}
	}

	//展示对比功能的js
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
		//BankBlackListEdit_dataset.setParameter("blacklistid", id);
		//btDetail.click();
		window.location = "${contextPath}/fpages/blacklistManage/ftl/BankBlacklistDetail.ftl?op=detail&reType=shareAll&blacklistid="
				+ id;
	}
</script>
</@CommonQueryMacro.page>