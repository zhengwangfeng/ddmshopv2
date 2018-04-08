<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>账单结算</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbSettleAccountController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbSettleAccountPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 店铺ID:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSettleAccountPage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">店铺ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 结算日期:
				</label></td>
				<td class="value"><input id="settleData" name="settleData"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbSettleAccountPage.settleData}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">结算日期</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 结算总额:
				</label></td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSettleAccountPage.total}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">结算总额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否已结算: </label></td>
				<td class="value"><input id="isSettle" name="isSettle"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbSettleAccountPage.isSettle}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否已结算</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 结算方式:
				</label></td>
				<td class="value"><input id="settleWay" name="settleWay"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbSettleAccountPage.settleWay}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">结算方式</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/settleaccount/tcsbSettleAccount.js"></script>