<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<@CommonQueryMacro.page title="报送表信息查询">
<@CommonQueryMacro.CommonQuery id="dataManageJgbb" init="false" submitMode="current" navigate="false">
<table align="left" width="800px">
   <tr>
      	<td colspan="2">
      	   <@CommonQueryMacro.Interface id="interface" label="报送报表查询" />
      	</td>
    </tr>
	<tr>
		<td><@CommonQueryMacro.PagePilot id="pagePilot1" maxpagelink="9" showArrow="true" pageCache="false"/></td>
	</tr>
   <tr>
		<td colspan="2">
			<@CommonQueryMacro.DataTable id="datatable1"  fieldStr="id,eastTableName,opr"  width="100%" hasFrame="true"/>
		</td>
	</tr>
</table>
</@CommonQueryMacro.CommonQuery>
<script language="javascript">
//定位一条记录
	function locate(id) {
		var record = dataManageJgbb_dataset.find(["id"],[id]);
		if (record) {
			dataManageJgbb_dataset.setRecord(record); 
		}
	}

	function datatable1_opr_onRefresh(cell, value, record)
	{
	
		if (record) {
		    var id = record.getValue("id");
		   cell.innerHTML="<center><a href=\"JavaScript:openData('"+id+"')\">详细</a></center>";
		   //  cell.innerHTML="<center><a href=\"${contextPath}\/fpages\/datamodify\/ftl\/DataManageJgxxb.ftl\">详细</a></center>";
			//cell.innerHTML="<center>详细</center>";
		} else {//当不存在记录时
		 cell.innerHTML="&nbsp;";
		}	
	}
	
	function openData(id){
		if( id != null){
		   if (id == "EAST_YGB"){
		      window.location.href = "${contextPath}/fpages/datamodify/ftl/DataManageYgb.ftl";
		   } else if (id == "EAST_JGXXB"){
		      window.location.href = "${contextPath}/fpages/datamodify/ftl/DataManageJgxxb.ftl";
		   } else{
		   }
		} else{
		}
		
	}
	
</script>
</@CommonQueryMacro.page>

