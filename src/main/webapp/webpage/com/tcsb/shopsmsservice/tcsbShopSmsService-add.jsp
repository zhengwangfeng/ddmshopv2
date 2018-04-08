<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商户短信服务</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopSmsServiceController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopSmsServicePage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbShopSmsServicePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbShopSmsServicePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbShopSmsServicePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbShopSmsServicePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbShopSmsServicePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbShopSmsServicePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 短信条数:
				</label></td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">短信条数</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shopsmsservice/tcsbShopSmsService.js"></script>