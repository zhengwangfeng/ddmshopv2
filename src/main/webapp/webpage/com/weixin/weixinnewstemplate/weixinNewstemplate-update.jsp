<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信图文消息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinNewstemplateController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${weixinNewstemplatePage.id }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinNewstemplatePage.accountid }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinNewstemplatePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinNewstemplatePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinNewstemplatePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinNewstemplatePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinNewstemplatePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinNewstemplatePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="templatename" name="templatename"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinNewstemplatePage.templatename}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 类型: </label>
				</td>
				<td class="value">
					<%-- <input id="type" name="type" type="text" style="width: 150px" class="inputxt"  value='${weixinNewstemplatePage.type}'> --%>
					<t:dictSelect field="type" typeGroupCode="WXTW" hasLabel="false"
						defaultVal="${weixinNewstemplatePage.type}"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">类型</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/weixin/weixinnewstemplate/weixinNewstemplate.js"></script>