<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信文本消息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinTexttemplateController.do?doAdd">
		<input id="id" name="id" type="hidden"
			value="${weixinTexttemplatePage.id }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinTexttemplatePage.accountid }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinTexttemplatePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinTexttemplatePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinTexttemplatePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinTexttemplatePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinTexttemplatePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinTexttemplatePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="templatename" name="templatename"
					type="text" style="width: 150px" class="inputxt" datetype="*">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 内容: </label>
				</td>
				<td class="value">
					<!--  <input id="content" name="content" type="text" style="width: 150px" class="inputxt" datetype="*"> -->
					<textarea type="text" style="width: 90%;" rows="4" name="content"
						value="" id="content" datetype="*"></textarea> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">内容</label>
				</td>
			</tr>

		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/weixin/weixintexttemplate/weixinTexttemplate.js"></script>