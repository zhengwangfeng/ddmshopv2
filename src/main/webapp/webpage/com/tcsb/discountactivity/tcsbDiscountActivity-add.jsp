<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>优惠活动</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDiscountActivityController.do?doAdd"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbDiscountActivityPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbDiscountActivityPage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbDiscountActivityPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbDiscountActivityPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbDiscountActivityPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbDiscountActivityPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbDiscountActivityPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbDiscountActivityPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满减模版:
				</label></td>
				<td class="value">
					<!-- <input id="fullcutTemplateId" name="fullcutTemplateId" type="text" style="width: 150px" class="inputxt" > -->
					<select id="fullcutTemplateId" name="fullcutTemplateId">
						<option value="">---请选择---</option>
						<c:forEach items="${tcsbFullcutTemplateEntities}"
							var="tcsbFullcutTemplateEntitie">
							<option value="${tcsbFullcutTemplateEntitie.id }">满${tcsbFullcutTemplateEntitie.total}减${tcsbFullcutTemplateEntitie.discount}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">满减模版</label>
				</td>
			</tr>
			<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
					     	 打<input id="useRebate" name="useRebate" type="text" style="width: 150px" class="inputxt" >折
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">折扣</label>
						</td>
					</tr> -->
			<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							有效期起:
						</label>
					</td>
					<td class="value">
							   <input id="expiryDateBegin" name="expiryDateBegin" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">有效期起</label>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							有效期止:
						</label>
					</td>
					<td class="value">
							   <input id="expiryDateEnd" name="expiryDateEnd" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">有效期止</label>
						</td>
					</tr> -->
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/discountactivity/tcsbDiscountActivity.js"></script>