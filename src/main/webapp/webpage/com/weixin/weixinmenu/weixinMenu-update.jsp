<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信菜单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinMenuController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${weixinMenuPage.id }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinMenuPage.accountid }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinMenuPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinMenuPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinMenuPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinMenuPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinMenuPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinMenuPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 菜单标识:
				</label></td>
				<td class="value"><input id="menukey" name="menukey"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinMenuPage.menukey}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 菜单标识</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 消息类型:
				</label></td>
				<td class="value"><input id="msgtype" name="msgtype"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinMenuPage.msgtype}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">消息类型</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" value='${weixinMenuPage.name}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">名称</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 顺序: </label>
				</td>
				<td class="value"><input id="orders" name="orders" type="text"
					style="width: 150px" class="inputxt"
					value='${weixinMenuPage.orders}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">顺序</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						消息模版ID : </label></td>
				<td class="value"><input id="templateid" name="templateid"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinMenuPage.templateid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">消息模版ID </label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 动作设置:
				</label></td>
				<td class="value"><input id="type" name="type" type="text"
					style="width: 150px" class="inputxt" value='${weixinMenuPage.type}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">动作设置</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 链接地址:
				</label></td>
				<td class="value"><input id="url" name="url" type="text"
					style="width: 150px" class="inputxt" value='${weixinMenuPage.url}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">链接地址</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 父ID:
				</label></td>
				<td class="value"><input id="fatherid" name="fatherid"
					type="text" style="width: 150px" class="inputxt"
					value='${weixinMenuPage.fatherid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">父ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinmenu/weixinMenu.js"></script>