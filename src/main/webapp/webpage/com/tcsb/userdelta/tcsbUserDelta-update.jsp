<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户充值记录</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserDeltaController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbUserDeltaPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbUserDeltaPage.shopId }">
		<input id="oppenid" name="oppenid" type="hidden"
			value="${tcsbUserDeltaPage.oppenid }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbUserDeltaPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 冲值金额:
				</label></td>
				<td class="value"><input id="amount" name="amount" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserDeltaPage.amount}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">冲值金额</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/userdelta/tcsbUserDelta.js"></script>