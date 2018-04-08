<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>支付消息通知</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPayNoticeController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbPayNoticePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属店铺:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPayNoticePage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属桌位:
				</label></td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPayNoticePage.deskId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属桌位</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 支付金额:
				</label></td>
				<td class="value"><input id="payMoney" name="payMoney"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbPayNoticePage.payMoney}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">支付金额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否已读:
				</label></td>
				<td class="value"><input id="isread" name="isread" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbPayNoticePage.isread}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否已读</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 支付时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbPayNoticePage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">支付时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbpaynotice/tcsbPayNotice.js"></script>