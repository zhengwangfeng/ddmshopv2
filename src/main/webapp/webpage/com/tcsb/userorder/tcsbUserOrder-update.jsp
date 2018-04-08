<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>多用户订单关联</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserOrderController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbUserOrderPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbUserOrderPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbUserOrderPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbUserOrderPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbUserOrderPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbUserOrderPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbUserOrderPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 用户ID:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserOrderPage.userId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 订单ID:
				</label></td>
				<td class="value"><input id="orderId" name="orderId"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbUserOrderPage.orderId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">订单ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/userorder/tcsbUserOrder.js"></script>