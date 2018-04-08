<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信菜单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table"
		action="tcsbFoodTasteFunctionController.do?saveFoodTasteFunction">
		<input id="id" name="id" type="hidden"
			value="${tcsbFoodTasteMenuPage.id }">

		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFoodTasteMenuPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFoodTasteMenuPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFoodTasteMenuPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFoodTasteMenuPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFoodTasteMenuPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFoodTasteMenuPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="statename" type="text"
					style="width: 150px" class="inputxt"
					value="${tcsbFoodTasteMenuPage.statename}" datatype="*2-50">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">菜单名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="menu.level" />:
				</label></td>
				<td class="value"><select name="functionlevel" id="menuLevel"
					datatype="*">
						<option value="0"
							<c:if test="${tcsbFoodTasteMenuPage.functionlevel eq 0}">selected="selected"</c:if>>
							<t:mutiLang langKey="main.function" />
						</option>
						<option value="1"
							<c:if test="${tcsbFoodTasteMenuPage.functionlevel>0}"> selected="selected"</c:if>>
							<t:mutiLang langKey="sub.function" />
						</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;"><t:mutiLang
							langKey="menu.level" /></label></td>
			</tr>
			<c:if
				test="${!empty tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.functionlevel}">
				<tr id="pfun">
					<td align="right"><label class="Validform_label"> <t:mutiLang
								langKey="parent.function" />:
					</label></td>
					<td class="value"><input id="cc"
						<c:if test="${tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.functionlevel eq 0}"> value="${tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.statename}"</c:if>
						<c:if test="${tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.functionlevel > 0}"> value="${tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.statename}"</c:if>
						readonly="readonly"> <input
						id="tcsbFoodTasteFunctionEntityId" name="parentfunctionid"
						style="display: none;"
						value="${tcsbFoodTasteMenuPage.tcsbFoodTasteFunctionEntity.id}">
						<span class="Validform_checktip"></span> <label
						class="Validform_label" style="display: none;"><t:mutiLang
								langKey="parent.function" /></label></td>
				</tr>
			</c:if>

			<tr>
				<td align="right"><label class="Validform_label"> 顺序: </label>
				</td>
				<td class="value"><input id="orders" name="stateorder"
					type="text" style="width: 150px" class="inputxt"
					value="${tcsbFoodTasteMenuPage.stateorder}" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">顺序</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinmenu/weixinMenu.js"></script>