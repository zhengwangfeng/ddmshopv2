<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>购物明细</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopCarItemController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopCarItemPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 食物ID:
				</label></td>
				<td class="value"><input id="foodId" name="foodId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">食物ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 数量: </label>
				</td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">数量</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 单价: </label>
				</td>
				<td class="value"><input id="price" name="price" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">单价</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">创建时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateTime" name="updateTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">更新时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						所属购物车: </label></td>
				<td class="value"><input id="shopCar" name="shopCar"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属购物车</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shopcaritem/tcsbShopCarItem.js"></script>