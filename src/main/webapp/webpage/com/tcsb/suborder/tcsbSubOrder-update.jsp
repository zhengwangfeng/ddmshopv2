<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>子订单管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
</head>
<body style="overflow-x: hidden;">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" tiptype="1" action="tcsbSubOrderController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${tcsbSubOrderPage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbSubOrderPage.shopId }">
		<input id="note" name="note" type="hidden"
			value="${tcsbSubOrderPage.note }">
		<input id="deskId" name="deskId" type="hidden"
			value="${tcsbSubOrderPage.deskId }">
		<input id="taste" name="taste" type="hidden"
			value="${tcsbSubOrderPage.taste }">
		<input id="platformDiscountPrice" name="platformDiscountPrice"
			type="hidden" value="${tcsbSubOrderPage.platformDiscountPrice }">
		<input id="specialCouponPrice" name="specialCouponPrice" type="hidden"
			value="${tcsbSubOrderPage.specialCouponPrice }">
		<input id="universalCouponPrice" name="universalCouponPrice"
			type="hidden" value="${tcsbSubOrderPage.universalCouponPrice }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbSubOrderPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbSubOrderPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbSubOrderPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbSubOrderPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbSubOrderPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbSubOrderPage.createDate }">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">订单编号:</label>
				</td>
				<td class="value"><input id="orderNo" name="orderNo"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbSubOrderPage.orderNo}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">订单编号</label></td>
				<td align="right"><label class="Validform_label">下单方式:</label>
				</td>
				<td class="value"><t:dictSelect field="method" type="select"
						typeGroupCode="orderForm" defaultVal="${tcsbSubOrderPage.method}"
						hasLabel="false" title="下单方式"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">下单方式</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">下单时间:</label>
				</td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbSubOrderPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">下单时间</label></td>
				<td align="right" style="display: none;"><label
					class="Validform_label">订单状态:</label></td>
				<td class="value" style="display: none;"><t:dictSelect
						field="status" type="select" typeGroupCode="orderSta"
						defaultVal="${tcsbSubOrderPage.status}" hasLabel="false"
						title="订单状态"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">订单状态</label>
				</td>
			</tr>
			<tr>
				<td align="right" style="display: none;"><label
					class="Validform_label">线上价格:</label></td>
				<td class="value" style="display: none;"><input
					id="onlinePrice" name="onlinePrice" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSubOrderPage.onlinePrice}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">线上收款</label></td>

				<td align="right" style="display: none;"><label
					class="Validform_label">支付状态:</label></td>
				<td class="value" style="display: none;"><t:dictSelect
						field="payStatus" type="select" typeGroupCode="payStatus"
						defaultVal="${tcsbSubOrderPage.payStatus}" hasLabel="false"
						title="支付状态"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">支付状态</label>
				</td>
			</tr>
			<tr>
				<td align="right" style="display: none;"><label
					class="Validform_label">支付方式:</label></td>
				<td class="value" style="display: none;"><t:dictSelect
						field="payMethod" type="select" typeGroupCode="payMethod"
						defaultVal="${tcsbSubOrderPage.payMethod}" hasLabel="false"
						title="支付方式"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">支付方式</label>
				</td>

				<td align="right" style="display: none;"><label
					class="Validform_label">用餐时间:</label></td>
				<td class="value" style="display: none;"><input id="eatTime"
					name="eatTime" type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbSubOrderPage.eatTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">用餐时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用餐人数:</label>
				</td>
				<td class="value"><input id="people" name="people" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSubOrderPage.people}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用餐人数</label></td>

				<td align="right"><label class="Validform_label">特殊说明:</label>
				</td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbSubOrderPage.note}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">特殊说明</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">实际消费:</label>
				</td>
				<td class="value"><input id="totalPrice" name="totalPrice"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbSubOrderPage.totalPrice}' readonly="true"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">实际消费</label></td>

				<td align="right"><label class="Validform_label">是否包间:</label>
				</td>
				<td class="value"><t:dictSelect field="isRoom" type="select"
						typeGroupCode="sf_yn" defaultVal="${tcsbSubOrderPage.isRoom}"
						hasLabel="false" title="是否包间"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否包间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"
					style="display: none;">线下收款:</label></td>
				<td class="value" style="display: none;"><input
					id="offlinePrice" name="offlinePrice" type="text"
					style="width: 150px" class="inputxt"
					value="${tcsbSubOrderPage.offlinePrice}"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">实际收款:</label> <input type="button"
					style="width: 150px" class="button" value="确认收款"></td>

				<td align="right"></td>
				<td class="value"></td>
			</tr>
		</table>
		<div style="width: auto; height: 200px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
			<div style="width: 800px; height: 1px;"></div>
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tcsbSubOrderController.do?tcsbSubOrderItemList&id=${tcsbSubOrderPage.id}"
					icon="icon-search" title="订单项管理" id="tcsbSubOrderItem"></t:tab>
			</t:tabs>
		</div>
	</t:formvalid>
	<!-- 添加 附表明细 模版 -->
	<table style="display: none">
		<tbody id="add_tcsbSubOrderItem_table_template">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh"></div></td>
				<td align="center"><input style="width: 20px;" type="checkbox"
					name="ck" /></td>
				<td align="left">
					<%-- <t:dictSelect field="tcsbSubOrderItemList[#index#].foodId" type="checkbox"
										typeGroupCode="" defaultVal="" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
					<select id="foodId" name="tcsbSubOrderItemList[#index#].foodId"
					datatype="*" onchange="changePrice(this)">
						<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
							<option value="${tcsbFoodEntitie.id }">${tcsbFoodEntitie.name }</option>
						</c:forEach>
				</select> <label class="Validform_label" style="display: none;">食物</label>
				</td>
				<td align="left"><input
					name="tcsbSubOrderItemList[#index#].count" maxlength="10"
					type="text" class="inputxt" style="width: 120px;">
					<label class="Validform_label" style="display: none;">数量</label></td>
				<td align="left"><input
					name="tcsbSubOrderItemList[#index#].price" maxlength="22"
					type="text" class="inputxt" style="width: 120px;">
					<label class="Validform_label" style="display: none;">单价</label></td>
				<%--  <td align="left">
							<t:dictSelect field="tcsbSubOrderItemList[#index#].foodTypeId" type="checkbox"
										typeGroupCode="" defaultVal="" hasLabel="false"  title="菜品ID"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">菜品ID</label>
				  </td> --%>
				<!--  <td align="left">
							<input name="tcsbSubOrderItemList[#index#].createDate" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					  		 >
					  <label class="Validform_label" style="display: none;">创建时间</label>
				  </td> -->
			</tr>
		</tbody>
	</table>
</body>
<script type="text/javascript">
  function changePrice(obj){
	  var foodId = $(obj).val();
	  var trObj = $(obj).parent().parent();
	  var url = "tcsbFoodController.do?getFood";
	  $.ajax({
         url:url,
         type:"POST",
         data:{
       	  foodId:foodId 
         },
         dataType:"JSON",
         async: false,
         success:function(data){
             if(data.success){
           	  trObj.find('td:last input').val(data.obj.price)
             }
         }
     });
} 
 
   $(function(){
	  var foodId = $("#foodId").val();
	  var trObj = $("#foodId").parent().parent();
	  var url = "tcsbFoodController.do?getFood";
	  $.ajax({
          url:url,
          type:"POST",
          data:{
        	  foodId:foodId 
          },
          dataType:"JSON",
          async: false,
          success:function(data){
              if(data.success){
            	  trObj.find('td:last input').val( data.obj.price);
              }
          }
      });
	  
	  
  }); 
 </script>
<script src="webpage/com/tcsb/suborder/tcsbSubOrder.js"></script>