<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>桌位类型</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDeskTypeController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tcsbDeskTypePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbDeskTypePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbDeskTypePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbDeskTypePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbDeskTypePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbDeskTypePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbDeskTypePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbDeskTypePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 座位人数:
				</label></td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">座位人数</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/desktype/tcsbDeskType.js"></script>