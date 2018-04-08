<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>满减模版</title>
<style>
ul {
	list-style-type: none;
}

li {
	display: inline-block;
}

li {
	margin: 10px 0;
}

input.labelauty+label {
	font: 12px "Microsoft Yahei";
}
</style>
<t:base type="jquery,easyui,tools,DatePicker,Labelauty"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFullcutTemplateController.do?doAdd">
		<input id="id" name="id" type="hidden"
			value="${tcsbFullcutTemplatePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbFullcutTemplatePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFullcutTemplatePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFullcutTemplatePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFullcutTemplatePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFullcutTemplatePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFullcutTemplatePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFullcutTemplatePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满额: </label>
				</td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt" datatype="n"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">满额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 立减: </label>
				</td>
				<td class="value"><input id="discount" name="discount"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">立减</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用期限:
				</label></td>
				<td class="value"><input id="usePeriod" name="usePeriod"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<t:dictSelect field="dateUnit" type="select" datatype="*"
						typeGroupCode="dateUnit" hasLabel="false" title=""></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">日期单位</label></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
						是否参与分享: </label></td>
				<td class="value"><t:dictSelect field="couponType"
						typeGroupCode="sf_10" hasLabel="false" defaultVal="0"></t:dictSelect>
				</td>
			</tr>
			<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							分享金:
						</label>
					</td>
					<td class="value">
					     	 <input id="shareDescript" name="shareDescript" type="text" style="width: 150px" class="inputxt" datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分享金</label>
						</td>
				</tr> -->


			<tr>
				<td align="right"><label class="Validform_label"> 发送模式:
				</label></td>
				<td class="value">
					<ul class="dowebok">
						<li><input type="radio" name="isShow" value="1"
							data-labelauty="保存并发送" checked="checked"></li>
						<li><input type="radio" name="isShow" value="0"
							data-labelauty="只发送 "></li>
						<li><input type="radio" name="isShow" value="2"
							data-labelauty="不发送只保存到模板库"></li>
					</ul>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>

<script src="webpage/com/tcsb/fullcuttemplate/tcsbFullcutTemplate.js"></script>
<script type="text/javascript">
	   $(function(){
		   $(':input').labelauty();
		   $("#formobj").find("select[name='dateUnit']").css("width","100");
	   });
	  //编写自定义JS代码
  </script>