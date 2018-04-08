<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>店铺关联银行卡</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbShopBankcardController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopBankcardPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						收款方行别代码: </label></td>
				<td class="value"><input id="beneficiaryBankcode"
					name="beneficiaryBankcode" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收款方行别代码</label>
				</td>
				<td align="right"><label class="Validform_label">
						收款方客户账号: </label></td>
				<td class="value"><input id="beneficiaryAccount"
					name="beneficiaryAccount" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收款方客户账号</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						收款方账户名称: </label></td>
				<td class="value"><input id="beneficiaryName"
					name="beneficiaryName" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收款方账户名称</label>
				</td>
				<td align="right"><label class="Validform_label">
						收款方开户行名称: </label></td>
				<td class="value"><input id="beneficiaryOpeningBankName"
					name="beneficiaryOpeningBankName" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收款方开户行名称</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						收款方联行号: </label></td>
				<td class="value"><input id="beneficiaryLineNumber"
					name="beneficiaryLineNumber" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收款方联行号</label>
				</td>
				<td align="right"><label class="Validform_label">
						客户方流水号: </label></td>
				<td class="value"><input id="clientDerialNumber"
					name="clientDerialNumber" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">客户方流水号</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">更新时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">创建时间</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbshopbankcard/tcsbShopBankcard.js"></script>