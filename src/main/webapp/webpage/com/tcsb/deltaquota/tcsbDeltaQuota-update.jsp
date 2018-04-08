<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>冲值额度设置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDeltaQuotaController.do?doUpdate">
		<input id="id" name="id" type="hidden"
			value="${tcsbDeltaQuotaPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbDeltaQuotaPage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbDeltaQuotaPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbDeltaQuotaPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbDeltaQuotaPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbDeltaQuotaPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbDeltaQuotaPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbDeltaQuotaPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 额度: </label>
				</td>
				<td class="value"><input id="quota" name="quota" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeltaQuotaPage.quota}' datatype="n"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">额度</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否赠送:
				</label></td>
				<td class="value"><t:dictSelect field="isTowards" type="select"
						datatype="*" typeGroupCode="sf_yn" hasLabel="false" title=""></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">额度</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/deltaquota/tcsbDeltaQuota.js"></script>