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
		layout="table"
		action="tcsbPlatformFullcutTemplateController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPlatformFullcutTemplatePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满额: </label>
				</td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.total}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">满额</label></td>
				<td align="right"><label class="Validform_label"> 立减: </label>
				</td>
				<td class="value"><input id="discount" name="discount"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.discount}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">立减</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 权重: </label>
				</td>
				<td class="value"><input id="weight" name="weight" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.weight}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">权重</label></td>
				<td align="right"><label class="Validform_label">
						优惠券是否投入使用: </label></td>
				<td class="value"><t:dictSelect field="isUse" type="list"
						typeGroupCode=""
						defaultVal="${tcsbPlatformFullcutTemplatePage.isUse}"
						hasLabel="false" title="优惠券是否投入使用"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">优惠券是否投入使用</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否新用户随机领取券: </label></td>
				<td class="value"><t:dictSelect field="isNewuserRandom"
						type="list" typeGroupCode=""
						defaultVal="${tcsbPlatformFullcutTemplatePage.isNewuserRandom}"
						hasLabel="false" title="是否新用户随机领取券"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否新用户随机领取券</label></td>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.updateName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformFullcutTemplatePage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">更新时间</label></td>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.updateBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.createName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPlatformFullcutTemplatePage.createBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPlatformFullcutTemplatePage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
				<td align="right"><label class="Validform_label"> </label></td>
				<td class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/platformfullcuttemplate/tcsbPlatformFullcutTemplate.js"></script>