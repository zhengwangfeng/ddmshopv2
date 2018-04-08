<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信链接素材</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinLinksucaiController.do?doAdd">
		<input id="id" name="id" type="hidden"
			value="${weixinLinksucaiPage.id }">
		<input id="innerLink" name="innerLink" type="hidden"
			value="${weixinLinksucaiPage.innerLink }">
		<input id="transferSign" name="transferSign" type="hidden"
			value="${weixinLinksucaiPage.transferSign }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinLinksucaiPage.accountid }">
		<input id="postCode" name="postCode" type="hidden"
			value="${weixinLinksucaiPage.postCode }">
		<input id="shareStatus" name="shareStatus" type="hidden"
			value="${weixinLinksucaiPage.shareStatus }">
		<input id="isEncrypt" name="isEncrypt" type="hidden"
			value="${weixinLinksucaiPage.isEncrypt }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinLinksucaiPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinLinksucaiPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinLinksucaiPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinLinksucaiPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinLinksucaiPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinLinksucaiPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 链接名称:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">链接名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 外部链接:
				</label></td>
				<td class="value"><textarea style="width: 600px;"
						class="inputxt" rows="6" id="outerLink" name="outerLink"
						datatype="*"></textarea> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">外部链接</label>
				</td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 功能描述:
				</label></td>
				<td class="value"><textarea style="width: 600px;"
						class="inputxt" rows="6" id="content" name="content"></textarea> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">功能描述</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinlinksucai/weixinLinksucai.js"></script>