<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>套餐关联表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFoodMealFunController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbFoodMealFunPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						foodTypeId: </label></td>
				<td class="value"><input id="foodId" name="foodId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">foodTypeId</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						printerId: </label></td>
				<td class="value"><input id="parentId" name="parentId"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">printerId</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/foodmealfun/tcsbFoodMealFun.js"></script>