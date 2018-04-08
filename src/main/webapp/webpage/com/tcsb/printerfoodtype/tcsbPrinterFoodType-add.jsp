<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分类打印权限</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPrinterFoodTypeController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPrinterFoodTypePage.id }">
		<input id="foodTypeId" name="foodTypeId" type="hidden"
			value="${tcsbPrinterFoodTypePage.foodTypeId }">
		<input id="printerId" name="printerId" type="hidden"
			value="${tcsbPrinterFoodTypePage.printerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/printerfoodtype/tcsbPrinterFoodType.js"></script>