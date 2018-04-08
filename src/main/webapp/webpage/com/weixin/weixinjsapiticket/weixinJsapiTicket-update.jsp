<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信临时票据</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinJsapiTicketController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${weixinJsapiTicketPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 临时票据:
				</label></td>
				<td class="value"><input id="jsapiTicket" name="jsapiTicket"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinJsapiTicketPage.jsapiTicket}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">临时票据</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						票据有效时间: </label></td>
				<td class="value"><input id="expiresIn" name="expiresIn"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinJsapiTicketPage.expiresIn}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">票据有效时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${weixinJsapiTicketPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinjsapiticket/weixinJsapiTicket.js"></script>