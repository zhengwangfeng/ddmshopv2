<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>打印食物菜单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPrinterFoodFunController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPrinterFoodFunPage.id }">
		<input id="foodId" name="foodId" type="hidden"
			value="${tcsbPrinterFoodFunPage.foodId }">
		<input id="printerId" name="printerId" type="hidden"
			value="${tcsbPrinterFoodFunPage.printerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/printerfoodfun/tcsbPrinterFoodFun.js"></script>