<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>tcsb_user_footer</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbUserFooterController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbUserFooterPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						用户的唯一标识: </label></td>
				<td class="value"><input id="openid" name="openid" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserFooterPage.openid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户的唯一标识</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						createTime: </label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbUserFooterPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">createTime</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 商家ID:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbUserFooterPage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">商家ID</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbuserfooter/tcsbUserFooter.js"></script>