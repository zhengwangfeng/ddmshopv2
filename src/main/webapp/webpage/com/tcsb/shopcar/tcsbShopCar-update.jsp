<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>购物车</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopCarController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbShopCarPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						createTime: </label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbShopCarPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">createTime</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 桌位ID:
				</label></td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbShopCarPage.deskId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">桌位ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shopcar/tcsbShopCar.js"></script>