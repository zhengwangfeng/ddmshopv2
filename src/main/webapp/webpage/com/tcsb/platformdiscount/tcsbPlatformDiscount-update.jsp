<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>平台优惠</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPlatformDiscountController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPlatformDiscountPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbPlatformDiscountPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbPlatformDiscountPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbPlatformDiscountPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbPlatformDiscountPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbPlatformDiscountPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbPlatformDiscountPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 标题: </label>
				</td>
				<td class="value"><input id="title" name="title" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPlatformDiscountPage.title}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">标题</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 抵用面额:
				</label></td>
				<td class="value"><input id="cash" name="cash" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPlatformDiscountPage.cash}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">抵用面额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用条件:
				</label></td>
				<td class="value"><input id="useCondition" name="useCondition"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformDiscountPage.useCondition}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">使用条件</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 使用范围:
				</label></td>
				<td class="value"><input id="useRange" name="useRange"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformDiscountPage.useRange}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">使用范围</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						发放时间起: </label></td>
				<td class="value"><input id="releaseTimeBegin"
					name="releaseTimeBegin" type="text" style="width: 150px"
					class="Wdate" onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformDiscountPage.releaseTimeBegin}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">发放时间起</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						发放时间止: </label></td>
				<td class="value"><input id="releaseTimeEnd"
					name="releaseTimeEnd" type="text" style="width: 150px"
					class="Wdate" onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformDiscountPage.releaseTimeEnd}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">发放时间止</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						使用有效期起: </label></td>
				<td class="value"><input id="expiryDateBegin"
					name="expiryDateBegin" type="text" style="width: 150px"
					class="Wdate" onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformDiscountPage.expiryDateBegin}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">使用有效期起</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						使用有效期止: </label></td>
				<td class="value"><input id="expiryDateEnd"
					name="expiryDateEnd" type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformDiscountPage.expiryDateEnd}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">使用有效期止</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 发放数量:
				</label></td>
				<td class="value"><input id="number" name="number" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPlatformDiscountPage.number}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">发放数量</label></td>
				<td align="right"><label class="Validform_label"> </label></td>
				<td class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/platformdiscount/tcsbPlatformDiscount.js"></script>