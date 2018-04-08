<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>餐位费管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbMealFeeController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tcsbMealFeePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbMealFeePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMealFeePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbMealFeePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMealFeePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMealFeePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMealFeePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbMealFeePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						人均餐位费: </label></td>
				<td class="value"><input id="mealFee" name="mealFee"
					type="text" style="width: 150px" class="inputxt"
					datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/"
					errormsg="请输入两位小数"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">人均餐位费</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/mealfee/tcsbMealFee.js"></script>