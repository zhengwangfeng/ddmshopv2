<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会员信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
        //编写自定义JS代码
    </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbMemberUserController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbMemberUserPage.id }">
		<input id="balance" name="balance" type="hidden"
			value="${tcsbMemberUserPage.balance }">
		<input id="openid" name="openid" type="hidden"
			value="${tcsbMemberUserPage.openid }">
		<input id="province" name="province" type="hidden"
			value="${tcsbMemberUserPage.province }">
		<input id="city" name="city" type="hidden"
			value="${tcsbMemberUserPage.city }">
		<input id="country" name="country" type="hidden"
			value="${tcsbMemberUserPage.country }">
		<input id="headimgurl" name="headimgurl" type="hidden"
			value="${tcsbMemberUserPage.headimgurl }">
		<input id="privilege" name="privilege" type="hidden"
			value="${tcsbMemberUserPage.privilege }">
		<input id="createTime" name="createTime" type="hidden"
			value="${tcsbMemberUserPage.createTime }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMemberUserPage.updateName }">
		<input id="updateTime" name="updateTime" type="hidden"
			value="${tcsbMemberUserPage.updateTime }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMemberUserPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMemberUserPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMemberUserPage.createBy }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 卡号: </label>
				</td>
				<td class="value"><input id="cardNo" name="cardNo" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberUserPage.cardNo}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">卡号</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 会员级别:
				</label></td>
				<td class="value">
					<%--<t:dictSelect field="membershipLevelId" type="list"--%> <%--typeGroupCode="" defaultVal="${tcsbMemberUserPage.membershipLevelId}" hasLabel="false"  title="会员级别ID"></t:dictSelect>--%>
					<select id="membershipLevelId" name="membershipLevelId"
					datatype="*">
						<option value="">---请选择---</option>
						<c:forEach items="${membershipLevelEntitiess}"
							var="tcsbMemberLevelEntity">
							<option value="${tcsbMemberLevelEntity.id}"
								<c:if test="${tcsbMemberUserPage.membershipLevelId==tcsbMemberLevelEntity.id}">selected="selected"</c:if>>${tcsbMemberLevelEntity.name}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">会员级别</label>
				</td>
			</tr>
			<%--<tr>--%>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--用户昵称:--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--<input id="nickname" name="nickname" type="text" style="width: 150px" class="inputxt"  value='${tcsbMemberUserPage.nickname}'>--%>
			<%--<span class="Validform_checktip"></span>--%>
			<%--<label class="Validform_label" style="display: none;">用户昵称</label>--%>
			<%--</td>--%>
			<tr>
				<td align="right"><label class="Validform_label"> 用户名:
				</label></td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberUserPage.name}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户名</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户性别:
				</label></td>
				<td class="value"><t:dictSelect field="sex" type="radio"
						typeGroupCode="sex" defaultVal="${tcsbMemberUserPage.sex}"
						hasLabel="false" title="用户性别"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户性别</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 出生日期:
				</label></td>
				<td class="value"><input id="birthOfDate" name="birthOfDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbMemberUserPage.birthOfDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">出生日期</label></td>
			</tr>
			<%--<tr>--%>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--商家ID:--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--<input id="shopId" name="shopId" type="text" style="width: 150px" class="inputxt"  value='${tcsbMemberUserPage.shopId}'>--%>
			<%--<span class="Validform_checktip"></span>--%>
			<%--<label class="Validform_label" style="display: none;">商家ID</label>--%>
			<%--</td>--%>
			<tr>
				<td align="right"><label class="Validform_label"> 联系方式:
				</label></td>
				<td class="value"><input id="mobile" name="mobile" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberUserPage.mobile}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">联系方式</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label>
				</td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbMemberUserPage.note}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">备注</label></td>
			</tr>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--</td>--%>
			<%--</tr>--%>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/memberuser/tcsbMemberUser.js"></script>