<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>大数据生成项</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbOrderBigdataRecordController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbOrderBigdataRecordPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbOrderBigdataRecordPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbOrderBigdataRecordPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbOrderBigdataRecordPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbOrderBigdataRecordPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbOrderBigdataRecordPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbOrderBigdataRecordPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 订单号:
				</label></td>
				<td class="value"><input id="orderno" name="orderno"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">订单号</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否执行数据更新: </label></td>
				<td class="value"><input id="isExecute" name="isExecute"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否执行数据更新</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/tcsborderbigdatarecord/tcsbOrderBigdataRecord.js"></script>