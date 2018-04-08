<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>食物规格</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table"
		action="tcsbFoodStandardController.do?doAdd&foodId=${foodId }"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbFoodStandardPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFoodStandardPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFoodStandardPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFoodStandardPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFoodStandardPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFoodStandardPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFoodStandardPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 规格名称:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">规格名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 价格: </label>
				</td>
				<td class="value"><input id="price" name="price" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">价格</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/foodstandard/tcsbFoodStandard.js"></script>