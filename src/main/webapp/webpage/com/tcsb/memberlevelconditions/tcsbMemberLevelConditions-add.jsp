<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会员级别条件</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbMemberLevelConditionsController.do?doAdd">
		<input id="id" name="id" type="hidden"
			value="${tcsbMemberLevelConditionsPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMemberLevelConditionsPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbMemberLevelConditionsPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMemberLevelConditionsPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMemberLevelConditionsPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMemberLevelConditionsPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbMemberLevelConditionsPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 冲值金额:
				</label></td>
				<td class="value"><input id="charge" name="charge" type="text"
					style="width: 150px" class="inputxt"
					datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/"
					errormsg="请输入正确的充值金额"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">冲值金额</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/memberlevelconditions/tcsbMemberLevelConditions.js"></script>