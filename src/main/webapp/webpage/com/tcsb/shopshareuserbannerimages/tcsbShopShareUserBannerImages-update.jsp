<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户分享横幅图片管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table"
		action="tcsbShopShareUserBannerImagesController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbShopShareUserBannerImagesPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 所属用户:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.userId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属用户</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 原图: </label>
				</td>
				<td class="value"><input id="fPath" name="fPath" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.fPath}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">原图</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 缩略图:
				</label></td>
				<td class="value"><input id="fThumbPath" name="fThumbPath"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.fThumbPath}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">缩略图</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.updateName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbShopShareUserBannerImagesPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">更新时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.updateBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.createName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbShopShareUserBannerImagesPage.createBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbShopShareUserBannerImagesPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/shopshareuserbannerimages/tcsbShopShareUserBannerImages.js"></script>