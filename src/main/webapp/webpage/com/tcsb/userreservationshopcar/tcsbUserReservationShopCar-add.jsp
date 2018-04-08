<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>预约虚拟购物车</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserReservationShopCarController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbUserReservationShopCarPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						所属购物车: </label></td>
				<td class="value"><input id="shopcarId" name="shopcarId"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属购物车</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						用户唯一标识: </label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户唯一标识</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">创建时间</label>
				</td>
			<tr>
				<td align="right"><label class="Validform_label"> note:
				</label></td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">note</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						peopleNum: </label></td>
				<td class="value"><input id="peopleNum" name="peopleNum"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">peopleNum</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						shopId: </label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">shopId</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						phone: </label></td>
				<td class="value"><input id="phone" name="phone" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">phone</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> sex:
				</label></td>
				<td class="value"><input id="sex" name="sex" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">sex</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						nickname: </label></td>
				<td class="value"><input id="nickname" name="nickname"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">nickname</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						eatTime: </label></td>
				<td class="value"><input id="eatTime" name="eatTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">eatTime</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/userreservationshopcar/tcsbUserReservationShopCar.js"></script>