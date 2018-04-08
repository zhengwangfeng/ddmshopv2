<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>平台优惠券模板</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPlatformFullcutTemplateController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPlatformFullcutTemplatePage.id }">

		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满额: </label>
				</td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">满额</label></td>
				<td align="right"><label class="Validform_label"> 立减: </label>
				</td>
				<td class="value"><input id="discount" name="discount"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">立减</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 权重: </label>
				</td>
				<td class="value"><input id="weight" name="weight" type="text"
					style="width: 150px" class="inputxt" value="1"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">权重</label></td>
				<td align="right"><label class="Validform_label">
						优惠券是否投入使用: </label></td>
				<td class="value"><t:dictSelect field="isUse" type="list"
						typeGroupCode="sf_10" defaultVal="1" hasLabel="false"
						title="优惠券是否投入使用"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">优惠券是否投入使用</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否新用户随机领取券: </label></td>
				<td class="value"><t:dictSelect field="isNewuserRandom"
						type="list" typeGroupCode="sf_10" defaultVal="1" hasLabel="false"
						title="是否新用户随机领取券"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否新用户随机领取券</label></td>
				<td align="right"><label class="Validform_label"> 使用期限:
				</label></td>
				<td class="value"><input id="usePeriod" name="usePeriod"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<t:dictSelect field="dateUnit" type="select" datatype="*"
						typeGroupCode="dateUnit" hasLabel="false" title=""></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">日期单位</label></td>
			</tr>

		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/platformfullcuttemplate/tcsbPlatformFullcutTemplate.js"></script>