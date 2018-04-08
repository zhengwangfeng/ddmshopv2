<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商品销量统计</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbSaleStatisticsController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbSaleStatisticsPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 食物ID:
				</label></td>
				<td class="value"><input id="foodId" name="foodId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSaleStatisticsPage.foodId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">食物ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 销量: </label>
				</td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSaleStatisticsPage.count}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">销量</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSaleStatisticsPage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value='<fmt:formatDate value='${tcsbSaleStatisticsPage.createTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/salestatistics/tcsbSaleStatistics.js"></script>