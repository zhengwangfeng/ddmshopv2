<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>食物分类</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFoodTypeController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${tcsbFoodTypePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbFoodTypePage.shopId }">
		<input id="isDel" name="isDel" type="hidden"
			value="${tcsbFoodTypePage.isDel }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFoodTypePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFoodTypePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFoodTypePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFoodTypePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFoodTypePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFoodTypePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 分类名称:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbFoodTypePage.name}' datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">分类名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 分类图标:
				</label></td>
				<td class="value">
					<%-- <input id="typeImg" name="typeImg" type="text" style="width: 150px" class="inputxt"  value='${tcsbFoodTypePage.typeImg}'> --%>
					<t:ckfinder name="typeImg" uploadType="Images"
						value="${tcsbFoodTypePage.typeImg}" width="100" height="60"
						buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">分类图标</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 菜品顺序:
				</label></td>
				<td class="value"><input id="orders" name="orders" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbFoodTypePage.orders}' datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">菜品顺序</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/foodtype/tcsbFoodType.js"></script>