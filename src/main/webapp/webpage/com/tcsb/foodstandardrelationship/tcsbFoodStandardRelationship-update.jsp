<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>食物规格关联表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table"
		action="tcsbFoodStandardRelationshipController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbFoodStandardRelationshipPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						食物规格ID: </label></td>
				<td class="value"><input id="foodStandardId"
					name="foodStandardId" type="text" style="width: 150px"
					class="inputxt"
					value='${tcsbFoodStandardRelationshipPage.foodStandardId}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">食物规格ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 食物ID:
				</label></td>
				<td class="value"><input id="foodId" name="foodId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbFoodStandardRelationshipPage.foodId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">食物ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/foodstandardrelationship/tcsbFoodStandardRelationship.js"></script>