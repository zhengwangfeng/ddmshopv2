<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>呼叫服务</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbCallServiceController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbCallServicePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属桌位:
				</label></td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.deskId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属桌位</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						选择的服务: </label></td>
				<td class="value"><input id="shopServiceId"
					name="shopServiceId" type="text" style="width: 150px"
					class="inputxt" value='${tcsbCallServicePage.shopServiceId}'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">选择的服务</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 数量: </label>
				</td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.count}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">数量</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否已读:
				</label></td>
				<td class="value"><input id="isread" name="isread" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.isread}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否已读</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否播放:
				</label></td>
				<td class="value"><input id="isplay" name="isplay" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.isplay}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否播放</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户ID:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbCallServicePage.userId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 呼叫时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbCallServicePage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">呼叫时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/callService/tcsbCallService.js"></script>