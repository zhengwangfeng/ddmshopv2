<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>多人购物</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserCarController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbUserCarPage.id }">
		<input id="createTime" name="createTime" type="hidden"
			value="${tcsbUserCarPage.createTime }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						购物车ID: </label></td>
				<td class="value"><input id="carId" name="carId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserCarPage.carId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">购物车ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户ID:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserCarPage.userId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/usercar/tcsbUserCar.js"></script>