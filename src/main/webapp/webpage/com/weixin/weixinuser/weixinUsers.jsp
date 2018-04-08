<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>微信用户集合</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:datagrid pagination="true" name="weixinUserList" title="微信会员选择"
		actionUrl="weixinUserController.do?datagridWeixinUser" idField="id"
		checkbox="false" showRefresh="false" fit="true" queryMode="group"
		onLoadSuccess="initCheck">
		<t:dgCol title="微信用户ID" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="微信openid" field="openid" hidden="true"></t:dgCol>
		<t:dgCol title="微信昵称 " field="nickname" width="120" query="true"></t:dgCol>
		<t:dgCol title="手机号 " field="mobile" width="120" query="true"></t:dgCol>
	</t:datagrid>
</body>
</html>
<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#weixinUserList").datagrid("selectRecord",idArr[i]);
		}
	}
}
</script>