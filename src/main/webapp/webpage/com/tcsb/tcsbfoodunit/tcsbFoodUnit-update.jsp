<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>食品单位管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFoodUnitController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbFoodUnitPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFoodUnitPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFoodUnitPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFoodUnitPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFoodUnitPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFoodUnitPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFoodUnitPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 单位名称:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbFoodUnitPage.name}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">单位名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否支持小数: </label></td>
				<td class="value"><input id="isfloat" name="isfloat"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodUnitPage.isfloat}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否支持小数</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbfoodunit/tcsbFoodUnit.js"></script>