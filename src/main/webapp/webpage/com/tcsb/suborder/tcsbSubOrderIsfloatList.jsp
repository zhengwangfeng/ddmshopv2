<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单管理</title>
<t:base type="jquery,easyui,layer,tools,DatePicker,Labelauty"></t:base>

</head>
<body style="overflow-x: hidden;">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" tiptype="1"
		action="tcsbSubOrderController.do?doisfloatAdd">

		<table border="0" cellpadding="2" cellspacing="0"
			id="tcsbSubOrderItem_table">
			<tr bgcolor="#E6E6E6">
				<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
				<td align="left" bgcolor="#EEEEEE" style="width: 126px;">食物</td>
				<td align="left" bgcolor="#EEEEEE" style="width: 126px;">数量</td>
				<td align="left" bgcolor="#EEEEEE" style="width: 126px;">单价</td>
			</tr>
			<tbody id="add_tcsbSubOrderItem_table">
				<c:if test="${fn:length(tcsbOrderItemList)  > 0 }">
					<c:forEach items="${tcsbOrderItemList}" var="poVal"
						varStatus="stuts">
						<tr>
							<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
							<input name="tcsbOrderItemList[${stuts.index }].id" type="hidden"
								value="${poVal.id }" />
							<input name="tcsbOrderItemList[${stuts.index }].orderId"
								type="hidden" value="${poVal.orderId }" />
							<input name="tcsbOrderItemList[${stuts.index }].updateName"
								type="hidden" value="${poVal.updateName }" />
							<input name="tcsbOrderItemList[${stuts.index }].updateDate"
								type="hidden" value="${poVal.updateDate }" />
							<input name="tcsbOrderItemList[${stuts.index }].updateBy"
								type="hidden" value="${poVal.updateBy }" />
							<input name="tcsbOrderItemList[${stuts.index }].createName"
								type="hidden" value="${poVal.createName }" />
							<input name="tcsbOrderItemList[${stuts.index }].createBy"
								type="hidden" value="${poVal.createBy }" />
							<td align="left">
								<%-- <input name="tcsbOrderItemList[${stuts.index }].count" maxlength="10" 
					  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.foodId }"> --%>
								<%-- <t:dictSelect field="tcsbOrderItemList[${stuts.index }].foodId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodId }" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
								<input name="tcsbOrderItemList[${stuts.index }].foodId"
								type="hidden" value="${poVal.foodId }"> <select
								id="foodId" disabled="disabled">
									<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
										<option value="${tcsbFoodEntitie.id }"
											<c:if test="${poVal.foodId==tcsbFoodEntitie.id}">selected="selected"</c:if>>${tcsbFoodEntitie.name }</option>
									</c:forEach>
							</select> <label class="Validform_label" style="display: none;">食物</label>
							</td>
							<td align="left"><input
								name="tcsbOrderItemList[${stuts.index }].count" maxlength="10"
								type="text" class="inputxt" style="width: 120px;"
								value="${poVal.count }"> <label class="Validform_label"
								style="display: none;">数量</label></td>

							<td align="left"><input
								name="tcsbOrderItemList[${stuts.index }].price" maxlength="22"
								type="text" class="inputxt" style="width: 120px;"
								value="${poVal.price }" readonly="readonly"> <label
								class="Validform_label" style="display: none;">单价</label></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</t:formvalid>

</body>
<script src="webpage/com/tcsb/order/tcsbOrder.js"></script>