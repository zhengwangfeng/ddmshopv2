<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>预约冲值</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDepositDeltaController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbDepositDeltaPage.id }">
		<input id="openid" name="openid" type="hidden"
			value="${tcsbDepositDeltaPage.openid }">
		<input id="orderNo" name="orderNo" type="hidden"
			value="${tcsbDepositDeltaPage.orderNo }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbDepositDeltaPage.shopId }">
		<input id="status" name="status" type="hidden"
			value="${tcsbDepositDeltaPage.status }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbDepositDeltaPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbDepositDeltaPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbDepositDeltaPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbDepositDeltaPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbDepositDeltaPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbDepositDeltaPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 预约押金:
				</label></td>
				<td class="value"><input id="reserveDeposit"
					name="reserveDeposit" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">预约押金</label>
				</td>
			<tr>
				<td align="right"><label class="Validform_label"> 冲值方式:
				</label></td>
				<td class="value"><input id="payMethod" name="payMethod"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">冲值方式</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 冲值来源:
				</label></td>
				<td class="value"><input id="paySource" name="paySource"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">冲值来源</label></td>
				<td align="right"><label class="Validform_label"> </label></td>
				<td class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/depositdelta/tcsbDepositDelta.js"></script>