<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>店铺活动满减模版</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopFullcutTemplateController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopFullcutTemplatePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbShopFullcutTemplatePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbShopFullcutTemplatePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbShopFullcutTemplatePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbShopFullcutTemplatePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbShopFullcutTemplatePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbShopFullcutTemplatePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbShopFullcutTemplatePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满额: </label>
				</td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt" datatype="n"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">满额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 立减: </label>
				</td>
				<td class="value"><input id="discount" name="discount"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">立减</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/shopfullcuttemplate/tcsbShopFullcutTemplate.js"></script>