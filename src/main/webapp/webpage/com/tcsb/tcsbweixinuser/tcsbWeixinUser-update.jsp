<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>tcsb_weixin_user</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbWeixinUserController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbWeixinUserPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						用户的唯一标识: </label></td>
				<td class="value"><input id="openid" name="openid" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.openid}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户的唯一标识</label></td>
				<td align="right"><label class="Validform_label"> 用户昵称:
				</label></td>
				<td class="value"><input id="nickname" name="nickname"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.nickname}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户昵称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户性别:
				</label></td>
				<td class="value"><input id="sex" name="sex" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.sex}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户性别</label></td>
				<td align="right"><label class="Validform_label"> 省份: </label>
				</td>
				<td class="value"><input id="province" name="province"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.province}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">省份</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 城市: </label>
				</td>
				<td class="value"><input id="city" name="city" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.city}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">城市</label></td>
				<td align="right"><label class="Validform_label"> 国家: </label>
				</td>
				<td class="value"><input id="country" name="country"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.country}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">国家</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用户头像:
				</label></td>
				<td class="value"><input id="headimgurl" name="headimgurl"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.headimgurl}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户头像</label></td>
				<td align="right"><label class="Validform_label">
						用户特权信息: </label></td>
				<td class="value"><input id="privilege" name="privilege"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.privilege}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户特权信息</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						createTime: </label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbWeixinUserPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">createTime</label></td>
				<td align="right"><label class="Validform_label"> 绑定手机:
				</label></td>
				<td class="value"><input id="mobile" name="mobile" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.mobile}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">绑定手机</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 商家ID:
				</label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.shopId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">商家ID</label></td>
				<td align="right"><label class="Validform_label">
						smscode: </label></td>
				<td class="value"><input id="smscode" name="smscode"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.smscode}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">smscode</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 发送时间:
				</label></td>
				<td class="value"><input id="sendtime" name="sendtime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbWeixinUserPage.sendtime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">发送时间</label></td>
				<td align="right"><label class="Validform_label">
						用户是否消费: </label></td>
				<td class="value"><input id="issale" name="issale" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbWeixinUserPage.issale}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用户是否消费</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/tcsbweixinuser/tcsbWeixinUser.js"></script>