<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分享内容管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbShopShareContentController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbShopShareContentPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbShopShareContentPage.shopId }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbShopShareContentPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbShopShareContentPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbShopShareContentPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbShopShareContentPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbShopShareContentPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbShopShareContentPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							分享二维码:
						</label>
					</td>
					<td class="value">
					      		<input id="qrCode" name="qrCode" type="text" style="width: 150px" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享二维码</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分享获取比例:
						</label>
					</td>
					<td class="value">
					     	 <input id="shareProportion" name="shareProportion" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享获取比例</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分享标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="shareTittle" name="shareTittle" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享标题</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分享描述:
						</label>
					</td>
					<td class="value">
					     	 <input id="shareDescript" name="shareDescript" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享描述</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分享logo:
						</label>
					</td>
					<td class="value">
					      		<input id="shareLogo" name="shareLogo" type="text" style="width: 150px" class="inputxt"  
>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享logo</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/shopsharecontent/tcsbShopShareContent.js"></script>		