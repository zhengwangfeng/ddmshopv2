<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>店铺评价</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopEvaluateController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopEvaluatePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbShopEvaluatePage.shopId }">
		<input id="createTime" name="createTime" type="hidden"
			value="${tcsbShopEvaluatePage.createTime }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 内容: </label>
				</td>
				<td class="value"><input id="content" name="content"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">内容</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户ID:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 菜品评价:
				</label></td>
				<td class="value"><input id="dishesEvaluation"
					name="dishesEvaluation" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">菜品评价</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 就餐环境:
				</label></td>
				<td class="value"><input id="diningEnvironment"
					name="diningEnvironment" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">就餐环境</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 图片: </label>
				</td>
				<td class="value"><input id="img" name="img" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">图片</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shopevaluate/tcsbShopEvaluate.js"></script>