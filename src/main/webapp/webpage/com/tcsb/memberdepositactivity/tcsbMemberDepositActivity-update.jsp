<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>tcsb_member_deposit_activity</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table"
		action="tcsbMemberDepositActivityController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbMemberDepositActivityPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMemberDepositActivityPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbMemberDepositActivityPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMemberDepositActivityPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMemberDepositActivityPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMemberDepositActivityPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbMemberDepositActivityPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						充值优惠活动名称: </label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberDepositActivityPage.name}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">充值优惠活动名称</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberDepositActivityPage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 充值金额:
				</label></td>
				<td class="value"><input id="amount" name="amount" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberDepositActivityPage.amount}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">充值金额</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 赠送金额:
				</label></td>
				<td class="value"><input id="presentAmount"
					name="presentAmount" type="text" style="width: 150px"
					class="inputxt"
					value='${tcsbMemberDepositActivityPage.presentAmount}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">赠送金额</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/memberDepositActivity/tcsbMemberDepositActivity.js"></script>