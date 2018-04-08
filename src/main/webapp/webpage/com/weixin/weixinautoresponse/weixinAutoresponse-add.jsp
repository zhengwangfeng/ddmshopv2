<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信关键字回复</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	$(document).ready(function() {
		var newsHtml = $("#newsId").html();
		var textHtml = $("#textId").html();

		$("#msgtype").change(function() {
			var value = $(this).val();
			if ("text" == value) {
				$("#textId").removeAttr("style");
				$("#textContent").removeAttr("disabled");
				$("#newsId").attr("style", "display:none");
				$("#newsContent").attr("disabled", "disabled");
			} else {
				$("#textId").attr("style", "display:none");
				$("#textContent").attr("disabled", "disabled");
				$("#newsId").removeAttr("style");
				$("#newsContent").removeAttr("disabled");
			}
		});

	});
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinAutoresponseController.do?doAdd">
		<input id="id" name="id" type="hidden"
			value="${weixinAutoresponsePage.id }">
		<input id="templatename" name="templatename" type="hidden"
			value="${weixinAutoresponsePage.templatename }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinAutoresponsePage.accountid }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinAutoresponsePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinAutoresponsePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinAutoresponsePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinAutoresponsePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinAutoresponsePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinAutoresponsePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 关键字:
				</label></td>
				<td class="value"><input id="keyword" name="keyword"
					type="text" style="width: 150px" class="inputxt" datatype="*">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">关键字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 消息类型:
				</label></td>
				<td class="value">
					<!--  <input id="msgtype" name="msgtype" type="text" style="width: 150px" class="inputxt" > -->
					<select name="msgtype" id="msgtype" datatype="*">
						<option value="text" selected="selected">文本消息</option>
						<option value="news">图文消息</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">消息类型</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 消息模版:
				</label></td>
				<td class="value" id="textId"><select name="templateid"
					id="textContent" datatype="*">
						<c:forEach items="${textList}" var="textVal">
							<option value="${textVal.id}" name="textMsg">${textVal.templatename}</option>
						</c:forEach>
				</select></td>
				<td class="value" style="display: none" id="newsId"><select
					name="templateid" id="newsContent" disabled="disabled" datatype="*">
						<c:forEach items="${newsList}" var="newsVal">
							<option value="${newsVal.id}">${newsVal.templatename}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">消息模版</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/weixin/weixinautoresponse/weixinAutoresponse.js"></script>