<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>退菜信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbRetreatFoodController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbRetreatFoodPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属订单:
				</label></td>
				<td class="value"><input id="orderId" name="orderId"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属订单</label></td>
				<td align="right"><label class="Validform_label"> 退单理由:
				</label></td>
				<td class="value"><input id="chargebackReason"
					name="chargebackReason" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">退单理由</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
				<td align="right"><label class="Validform_label">
						退单状态0取消1已退: </label></td>
				<td class="value"><input id="status" name="status" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">退单状态0取消1已退</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 食物ID:
				</label></td>
				<td class="value"><input id="foodId" name="foodId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">食物ID</label></td>
				<td align="right"><label class="Validform_label"> 退菜份数:
				</label></td>
				<td class="value"><input id="num" name="num" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">退菜份数</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">更新时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">创建时间</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbretreatfood/tcsbRetreatFood.js"></script>