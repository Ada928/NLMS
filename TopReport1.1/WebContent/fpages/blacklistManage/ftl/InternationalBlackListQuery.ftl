<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"] />
<#assign info = Session["USER_SESSION_INFO"]>
<@CommonQueryMacro.page title="国际黑名单查询">
<@CommonQueryMacro.CommonQuery id="InternationalBlackListQuery" init="true" submitMode="current"  navigate="false">
<table align="center" width="100%">
   	<tr>
      	<td colspan="2" >
			<@CommonQueryMacro.Interface id="intface" label="请输入查询条件" colNm=4/>
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
	<tr>
	    <td colspan="2">
	      	<@CommonQueryMacro.FloatWindow id="signWindow" label="" width="100%" resize="true" defaultZoom="normal" minimize="false"
					maximize="false" closure="true" float="true" exclusive="true" position="center" show="false" >
	      		<div align="center">
	      			<@CommonQueryMacro.Group id="group1" label="国际黑名单详情" width="100%" height="100%"
	        			  fieldStr="id,blacklistType,sanCode,sanName,accountType,certificateType,certificateNumber,"+
	        			 	"clientName,clientEnglishName,nationality,birthday,birthCountry,gender,lastOccupation,"+
							"residenceCountry,politicians,valid,del,validDate,operateState,createDate,lastModifyDate,"+
							"lastModifyOperator,remarks" colNm=4/>
	      		</div>
	     	</@CommonQueryMacro.FloatWindow>	
	  	</td>
  	</tr>
	<tr align="center" style="display:none">
		<td><@CommonQueryMacro.Button id="btDetail" /></td>
	</tr>
</table>

</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
    //定位一行记录
    function locate(id) {
        var record = InternationalBlackListQuery_dataset.find(["id"], [id]);
        if (record) {
            InternationalBlackListQuery_dataset.setRecord(record);
        }
    }

  //展示对比功能的js
	function datatable1_id_onRefresh(cell, value, record) {
		if (record) {
			var id = record.getValue("id");
			cell.innerHTML = "<a href=\"Javascript:showDetail('" + id + "')\">" + value + "</a>";
		} else {
			cell.innerHTML = "";
		}
	}

	/* function showDetail(id) {
		locate(id);
		btDetail.click();
	} */
	function showDetail(id) {
		//locate(id);
		//btDetail.click();
		subwindow_signWindow.show();
	}
	//�ظ�������,�ͷ�dataset
	function signWindow_floatWindow_beforeClose(subwindow) {
		InternationalBlackListQuery_dataset.cancelRecord();
		return true;
	}
	function signWindow_floatWindow_beforeHide(subwindow) {
		return signWindow_floatWindow_beforeClose(subwindow);
	}
</script>
</@CommonQueryMacro.page>
