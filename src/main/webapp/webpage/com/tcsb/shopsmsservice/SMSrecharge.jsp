<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商户短信服务</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopSmsServiceController.do?doAdd"
		tiptype="1">
		<input type="hidden" name="shopId" value="${shopId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value">${shopName } <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">所属店铺</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						充值短信条数: </label></td>
				<td class="value"><input id="count" name="count" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">短信条数</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shopsmsservice/tcsbShopSmsService.js"></script>