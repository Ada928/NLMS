<%@page import="java.util.List"%>
<%@page import="com.huateng.report.common.bean.ReportBackErrBean"%>
<%@page import="com.huateng.report.utils.ReportUtils"%>
<%@page import="com.huateng.ebank.framework.util.DataFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html;charset=GBK" pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templets/easyui/themes/blue/easyui.css">
<title>��ִ���</title>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"8123",secure:"8124"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body bgcolor="white" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-26" data-genuitec-path="/TopReport1.1/WebContent/fpages/commonloadpage/jsp/ReportBackErr.jsp">
	<%
	String recId = (String)request.getParameter("id");
	String appType = (String)request.getParameter("appType");
	String currentfile = (String)request.getParameter("currentfile");
	ReportBackErrBean reprotBackErrBean = ReportUtils.getReportBankMsg(recId, appType, currentfile);
%>
	<FIELDSET name='group1' style="padding: 6px;width: 90%" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-26" data-genuitec-path="/TopReport1.1/WebContent/fpages/commonloadpage/jsp/ReportBackErr.jsp">
		<LEGEND>��ִ���</LEGEND>
		<table width="99%" class="grouptable">
			<%
	if (reprotBackErrBean == null) {

%>
			<tr>
				<td class="labeltd" valign=center align="right" style="width: 20%" nowrap>��ִ���</td>
				<td class="datatd" colspan="1" valign=center align="left" style="width: 20%" nowrap>û�в鵽��ִ���</td>
			</tr>
		</table>
		<%} else {%>
		<tr>
			<td class="labeltd" valign=center align="center" style="width: 20%" nowrap>��������</td>
		</tr>
		<tr>
			<%if(reprotBackErrBean.getErrType().equals("01")){ %>
			<td class="datatd" colspan="1" valign=center align="center" style="width: 20%" nowrap>��ʽ����</td>
			<% } else { %>
			<td class="datatd" colspan="1" valign=center align="center" style="width: 20%" nowrap>��¼����</td>
			<%} %>
		</tr>
		</table>
	</FIELDSET>

	<FIELDSET name='group2' style="padding: 6px;width: 90%">
		<LEGEND>������ϸ��Ϣ</LEGEND>
		<%if(reprotBackErrBean.getErrType().equals("01")){ //��ʽ����%>
		<table width="99%" class="grouptable">
			<thead>
				<tr>
					<td class="labeltd" valign=center>���</td>
					<td class="labeltd" valign=center>������Ϣ</td>
				</tr>
			</thead>
			<%
			List errMsgList = reprotBackErrBean.getErrMsg();
			for(int i = 0; i < errMsgList.size(); i++){
		%>
			<tr>
				<td class="datatd" valign=center align="left" style="width: 20%" nowrap><%=i+1 %></td>
				<td class="datatd" valign=center align="left" style="width: 20%" nowrap><%=errMsgList.get(i) %></td>
			</tr>
			<%} %>
		</table>
		<%} else { %>
		<table width="99%" class="grouptable">
			<thead>
				<tr>
					<td class="labeltd" valign=center>�ֶ�Ӣ������</td>
					<td class="labeltd" valign=center>�ֶ���������</td>
					<td class="labeltd" valign=center>������Ϣ</td>
				</tr>
			</thead>
			<%
			Map errFiledMap = reprotBackErrBean.getErrFiledMap();
			Map errFiledContentMap = reprotBackErrBean.getErrFiledContentMap();
			Iterator it = errFiledMap.keySet().iterator();
			while(it.hasNext()){
				String fieldEnName = (String)it.next();
		%>
			<tr>
				<td class="datatd" valign=center align="left" style="width: 20%" nowrap><%=fieldEnName %></td>
				<td class="datatd" colspan="1" valign=center align="left" style="width: 20%" nowrap><%=errFiledMap.get(fieldEnName) %>
				</td>
				<td class="datatd" colspan="1" valign=center align="left" style="width: 20%" nowrap><%=errFiledContentMap.get(fieldEnName) %>
				</td>
			</tr>
			<%} %>
		</table>
		<%}%>
	</FIELDSET>
	<%} %>
</body>

<script type="text/javascript">

</script>
</html>