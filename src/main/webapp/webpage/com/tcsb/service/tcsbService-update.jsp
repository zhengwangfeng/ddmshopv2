<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>系统服务</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbServiceController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbServicePage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbServicePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbServicePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbServicePage.updateBy }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 服务名称:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbServicePage.name}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">服务名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 图标: </label>
				</td>
				<td class="value"><input id="logo" name="logo" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbServicePage.logo}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">图标</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbServicePage.createName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbServicePage.createBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbServicePage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/service/tcsbService.js"></script>