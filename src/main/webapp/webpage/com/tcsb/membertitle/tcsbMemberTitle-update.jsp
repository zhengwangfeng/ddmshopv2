<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会员头衔</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbMemberTitleController.do?doUpdate">
		<input id="id" name="id" type="hidden"
			value="${tcsbMemberTitlePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbMemberTitlePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMemberTitlePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbMemberTitlePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMemberTitlePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMemberTitlePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMemberTitlePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbMemberTitlePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberTitlePage.name}' datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 图标: </label>
				</td>
				<td class="value">
					<!-- <input id="typeImg" name="typeImg" type="text" style="width: 150px" class="inputxt" > -->
					<t:ckfinder name="img" uploadType="Images" width="100" height="60"
						value="${tcsbMemberTitlePage.img}" buttonClass="ui-button"
						buttonValue="上传图片"></t:ckfinder> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">图标</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/membertitle/tcsbMemberTitle.js"></script>