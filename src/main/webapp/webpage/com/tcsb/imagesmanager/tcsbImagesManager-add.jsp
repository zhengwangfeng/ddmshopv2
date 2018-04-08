<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>图片管理器</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbImagesManagerController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbImagesManagerPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbImagesManagerPage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbImagesManagerPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbImagesManagerPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbImagesManagerPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbImagesManagerPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbImagesManagerPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbImagesManagerPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 原图: </label>
				</td>
				<td class="value"><input id="fPath" name="fPath" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">原图</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 缩略图:
				</label></td>
				<td class="value"><input id="fThumbPath" name="fThumbPath"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">缩略图</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/imagesmanager/tcsbImagesManager.js"></script>