<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户平台优惠券</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPlatformCouponController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPlatformCouponPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属用户:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属用户</label></td>
				<td align="right"><label class="Validform_label"> 手机: </label>
				</td>
				<td class="value"><input id="mobile" name="mobile" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">手机</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用状态:
				</label></td>
				<td class="value"><input id="useStatus" name="useStatus"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">使用状态</label></td>
				<td align="right"><label class="Validform_label"> 满减模版:
				</label></td>
				<td class="value"><input id="fullcutTemplateId"
					name="fullcutTemplateId" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">满减模版</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用时间:
				</label></td>
				<td class="value"><input id="useTime" name="useTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">使用时间</label>
				</td>
				<td align="right"><label class="Validform_label"> 有效期:
				</label></td>
				<td class="value"><input id="expiryDate" name="expiryDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">有效期</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						是否新用户优惠券: </label></td>
				<td class="value"><input id="isNewuserCouon"
					name="isNewuserCouon" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">是否新用户优惠券</label>
				</td>
				<td align="right"><label class="Validform_label"> 折扣: </label>
				</td>
				<td class="value"><input id="useRebate" name="useRebate"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">折扣</label></td>
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
<script src="webpage/com/tcsb/platformcoupon/tcsbPlatformCoupon.js"></script>