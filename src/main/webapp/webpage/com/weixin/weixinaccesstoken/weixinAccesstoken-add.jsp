<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信凭证</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinAccesstokenController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${weixinAccesstokenPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 凭证: </label>
				</td>
				<td class="value"><input id="accessToken" name="accessToken"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">凭证</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						凭证有效时间: </label></td>
				<td class="value"><input id="expiresIn" name="expiresIn"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">凭证有效时间</label></td>
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
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinaccesstoken/weixinAccesstoken.js"></script>