<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>账单结算</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbSettleAccountController.do?doBetSettle"
		tiptype="1">
		<input id="id" name="id" type="hidden" value="${id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">

			<tr>
				<td align="right"><label class="Validform_label"> 备注信息:
				</label></td>
				<td class="value"><textarea id="settleWay" name="settleWay"
						rows="10" cols="10" style="width: 300px; height: 100px;"
						class="inputxt"></textarea> <span class="Validform_checktip"></span>
				</td>
			</tr>


		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/settleaccount/tcsbSettleAccount.js"></script>