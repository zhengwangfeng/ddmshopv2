<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户消费记录</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserCostController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbUserCostPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbUserCostPage.shopId }">
		<input id="oppenid" name="oppenid" type="hidden"
			value="${tcsbUserCostPage.oppenid }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbUserCostPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 用户消费:
				</label></td>
				<td class="value"><input id="cost" name="cost" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserCostPage.cost}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户消费</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 桌位: </label>
				</td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserCostPage.deskId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">桌位</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/usercost/tcsbUserCost.js"></script>