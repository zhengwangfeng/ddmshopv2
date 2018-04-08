<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>口味偏好</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFoodTasteFunctionController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbFoodTasteFunctionPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 菜单等级:
				</label></td>
				<td class="value"><input id="functionlevel"
					name="functionlevel" type="text" style="width: 150px"
					class="inputxt" value='${tcsbFoodTasteFunctionPage.functionlevel}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">菜单等级</label></td>
				<td align="right"><label class="Validform_label"> 口味名称:
				</label></td>
				<td class="value"><input id="statename" name="statename"
					type="text" style="width: 150px" class="inputxt" datatype="*"
					value='${tcsbFoodTasteFunctionPage.statename}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">口味名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 口味排序:
				</label></td>
				<td class="value"><input id="stateorder" name="stateorder"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.stateorder}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">口味排序</label></td>
				<td align="right"><label class="Validform_label">
						parentid: </label></td>
				<td class="value"><input id="parentfunctionid"
					name="parentfunctionid" type="text" style="width: 150px"
					class="inputxt"
					value='${tcsbFoodTasteFunctionPage.parentfunctionid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">parentid</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						创建人id: </label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.createBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人id</label></td>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.createName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						修改人id: </label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.updateBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">修改人id</label></td>
				<td align="right"><label class="Validform_label"> 修改时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbFoodTasteFunctionPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">修改时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbFoodTasteFunctionPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
				<td align="right"><label class="Validform_label"> 修改人:
				</label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.updateName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">修改人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						shopid: </label></td>
				<td class="value"><input id="shopid" name="shopid" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbFoodTasteFunctionPage.shopid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">shopid</label></td>
				<td align="right"><label class="Validform_label"> </label></td>
				<td class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunction.js"></script>