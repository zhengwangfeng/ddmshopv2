<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>桌位管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDeskController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tcsbDeskPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbDeskPage.shopId }">
		<input id="number" name="number" type="hidden"
			value="${tcsbDeskPage.number }">
		<input id="qrcode" name="qrcode" type="hidden"
			value="${tcsbDeskPage.qrcode }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbDeskPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbDeskPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbDeskPage.updateBy }">
		<input id="status" name="status" type="hidden" value="0">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbDeskPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbDeskPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbDeskPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 桌位名称:
				</label></td>
				<td class="value"><input id="deskName" name="deskName"
					type="text" style="width: 150px" class="inputxt" datatype="*">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">桌位名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 桌位类型:
				</label></td>
				<td class="value"><select id="desktypeId" name="desktypeId"
					datatype="*">
						<c:forEach items="${tcsbDeskTypeEntities}"
							var="tcsbDeskTypeEntitie">
							<option value="${tcsbDeskTypeEntitie.id }">${tcsbDeskTypeEntitie.name}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">桌位类型</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否被预约: </label></td>
				<td class="value">
					<!-- <input id="isOrder" name="isOrder" type="text" style="width: 150px" class="inputxt" > -->
					<select id="isOrder" name="isOrder" datatype="*">
						<option value="0" selected="selected">否</option>
						<option value="1">是</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">是否被预约</label>
				</td>
			</tr>

		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/desk/tcsbDesk.js"></script>