<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单管理</title>
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

.addbtnstyleclass {
	height: 40px;
	line-height: 40px;
	width: 60px;
	padding: 10 11px;
	background: #4ba8f7;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 16px;
	outline: none;
	color: white;
	margin-top: 2px;
}

.addbtnstyleclass:hover {
	height: 40px;
	line-height: 40px;
	width: 60px;
	padding: 10 11px;
	background: #389ff7;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 16px;
	outline: none;
	color: yellow;
	margin-top: 2px;
}
</style>
<t:base type="jquery,easyui,layer,tools,DatePicker,Labelauty"></t:base>
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
		layout="table" tiptype="1" action="tcsbOrderController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tcsbOrderPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbOrderPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbOrderPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbOrderPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbOrderPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbOrderPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbOrderPage.createDate }">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">下单方式:</label>
				</td>
				<td class="value"><input type="hidden" name="method" value="2">
					<input type="text" value="手动下单" readonly="readonly"> <%-- <t:dictSelect field="method" type="select"
						typeGroupCode="orderForm"  hasLabel="false"  title="下单方式"></t:dictSelect>     --%>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">下单方式</label></td>
				<td align="right"><label class="Validform_label">订单状态:</label>
				</td>
				<td class="value"><input type="hidden" name="status" value="0">
					<input type="text" value="消费中" readonly="readonly"> <%-- 	<t:dictSelect field="status" type="select"
						typeGroupCode="orderSta"  hasLabel="false"  title="订单状态"></t:dictSelect>   --%>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">订单状态</label></td>
			</tr>
			<tr>

				<td align="right"><label class="Validform_label">线上价格:</label>
				</td>
				<td class="value"><input id="onlinePrice" name="onlinePrice"
					type="text" style="width: 150px" class="inputxt" value="0"
					readonly="readonly"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">线上价格</label>
				</td>
				<td align="right"><label class="Validform_label">支付状态:</label>
				</td>
				<td class="value"><input type="hidden" name="payStatus"
					value="0"> <input type="text" value="未支付"
					readonly="readonly"> <%-- <t:dictSelect field="payStatus" type="select"
						typeGroupCode="payStatus"  hasLabel="false"  title="支付状态"></t:dictSelect>    --%>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">支付状态</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">支付方式:</label>
				</td>
				<td class="value"><input type="hidden" name="payMethod"
					value="1"> <input type="text" value="线下支付"
					readonly="readonly"> <%-- <t:dictSelect field="payMethod" type="select"
						typeGroupCode="payMethod"  hasLabel="false"  title="支付方式"></t:dictSelect>      --%>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">支付方式</label></td>

				<td align="right"><label class="Validform_label">用餐时间:</label>
				</td>
				<td class="value"><input id="eatTime" name="eatTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">用餐时间</label>
				</td>
			</tr>
			<tr>
				<!-- <td align="right">
				<label class="Validform_label">用餐人数:</label>
			</td>
			<td class="value">
		     	 <input id="people" name="people" type="text" style="width: 150px" class="inputxt">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">用餐人数</label>
			</td>
			 -->
				<td align="right"><label class="Validform_label">特殊说明:</label>
				</td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">特殊说明</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">实际消费:</label>
				</td>
				<td class="value"><input id="totalPrice" name="totalPrice"
					type="text" style="width: 150px" class="inputxt" value="0"
					readonly="readonly"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">实际消费</label>
				</td>

				<td align="right"><label class="Validform_label">是否包间:</label>
				</td>
				<td class="value"><t:dictSelect field="isRoom" type="select"
						typeGroupCode="sf_yn" hasLabel="false" title="是否包间"></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">是否包间</label></td>
			</tr>
			<tr>


				<td align="right"><label class="Validform_label">桌位:</label></td>
				<td class="value"><select name="deskId" datatype="/\w{1,33}/i">
						<c:forEach items="${deskList}" var="deskList">
							<option value="${deskList.id }">${deskList.deskName}</option>
						</c:forEach>
				</select></td>

				<td align="right"></td>
				<td class="value"></td>

			</tr>

			<tr>
				<td align="right"><label class="Validform_label">线下收款:</label>
				</td>
				<td class="value"><input id="offlinePrice" name="offlinePrice"
					type="text" style="width: 150px" class="inputxt" value="0">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">实际收款:</label></td>

				<td align="right"></td>
				<td class="value"></td>
			</tr>

		</table>
		<div id="tcsbOrderItemListView" style="width: auto; height: 200px;"
			style="position:fixed; z-index:1;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
			<!-- <div style="width:800px;height:1px;"></div> -->
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tcsbOrderController.do?tcsbOrderItemList&id=${tcsbOrderPage.id}"
					icon="icon-search" title="订单项管理" id="tcsbOrderItem"></t:tab>
			</t:tabs>
		</div>



		<div id="funtasteselect"
			style="position: fixed; z-index: -1; top: 1%; left: 1%; width: 100%; height: 100%; background-color: rgba(23, 12, 12, 0.39);">
			<input type="hidden" id="objhidden" value="">
			<table id="funtastetable"
				style="width: 50%; margin-left: 20%; margin-top: 10%; border: solid #add9c0; border-width: 1px 1px 1px 1px;"
				cellspacing="1" cellpadding="1">

			</table>
			<div style="margin-left: 55%;">
				<input class="addbtnstyleclass" type="button"
					onclick="funtaste_btn()" value="确定"> <input
					class="addbtnstyleclass" type="button"
					onclick="funtaste_cancle_btn()" value="取消">
			</div>
		</div>



	</t:formvalid>
	<!-- 添加 附表明细 模版 -->
	<table style="display: none">
		<tbody id="add_tcsbOrderItem_table_template">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh"></div></td>
				<td align="center"><input style="width: 20px;" type="checkbox"
					name="ck" /></td>
				<td align="left">
					<%-- <t:dictSelect field="tcsbOrderItemList[#index#].foodId" type="checkbox"
										typeGroupCode="" defaultVal="" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
					<select id="foodtypeId"
					name="tcsbOrderItemList[#index#].foodTypeId" style="width: 120px;"
					onchange="foodtypechange(this)">
						<option value="1">--------------</option>
						<c:forEach items="${tcsbFoodTypeEntity}" var="tcsbFoodTypeEntity">
							<option value="${tcsbFoodTypeEntity.id }">${tcsbFoodTypeEntity.name }</option>
						</c:forEach>
				</select> <%-- 	<select id="foodId" name="tcsbOrderItemList[#index#].foodId" datatype="*" onchange="changePrice(this)">
								<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
									<option value="${tcsbFoodEntitie.id }">${tcsbFoodEntitie.name }</option>
								</c:forEach>  
							</select>	 --%> <label class="Validform_label"
					style="display: none;">食物</label>
				</td>
				<td align="left">
					<%-- <t:dictSelect field="tcsbOrderItemList[#index#].foodId" type="checkbox"
										typeGroupCode="" defaultVal="" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
					<select id="foodId" name="tcsbOrderItemList[#index#].foodId"
					datatype="*" onchange="changePrice(this)">
						<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
							<option value="${tcsbFoodEntitie.id }">${tcsbFoodEntitie.name }</option>
						</c:forEach>
				</select> <label class="Validform_label" style="display: none;">食物</label>
				</td>
				<td align="left"><c:if
						test="${tcsbFoodEntities[0].isCurrentPrice=='Y' }">
						<input name="tcsbOrderItemList[#index#].count" maxlength="10"
							type="text" class="inputxt" style="width: 120px;"
							onblur="changNum(this)"
							datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/">
					</c:if> <c:if test="${tcsbFoodEntities[0].isCurrentPrice=='N' }">

						<input name="tcsbOrderItemList[#index#].count" maxlength="10"
							type="text" class="inputxt" style="width: 120px;"
							onblur="changNum(this)" datatype="n">
					</c:if> <%--<input type="text" name="tcsbOrderItemList[#index#].count" value="1">--%>
					<%--<label class="Validform_label" style="display: none;">数量</label>--%>
				</td>
				<td align="left"><input
					name="tcsbOrderItemList[#index#].unitName" maxlength="22"
					type="text" class="inputxt" style="width: 80px;" readonly="true"
					value="${ tcsbFoodEntities[0].unitName}"> <label
					class="Validform_label" style="display: none;">单位</label></td>

				<td align="left"><input name="tcsbOrderItemList[#index#].price"
					maxlength="22" type="text" class="inputxt" style="width: 80px;"
					readonly="true"> <label class="Validform_label"
					style="display: none;">单价</label></td>

				<td align="left"><input
					name="tcsbOrderItemList[#index#].foodTasteFun" maxlength="22"
					type="text" class="inputxt" style="width: 120px; cursor: pointer;"
					title="编辑菜品规格" alt="编辑菜品规格" onclick="getfoodtastefun(this)">
					<label class="Validform_label" style="display: none;">菜品规格</label>
				</td>
				<%--  <td align="left">
							<t:dictSelect field="tcsbOrderItemList[#index#].foodTypeId" type="checkbox"
										typeGroupCode="" defaultVal="" hasLabel="false"  title="菜品ID"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">菜品ID</label>
				  </td> --%>
				<!--  <td align="left">
							<input name="tcsbOrderItemList[#index#].createDate" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					  		 >
					  <label class="Validform_label" style="display: none;">创建时间</label>
				  </td> -->
			</tr>
		</tbody>
	</table>
</body>
<script type="text/javascript">
 	function receiveMoney(){
 		//询问框
 		layer.confirm('你是否要确定收款？', {
 		  btn: ['确定','取消'] //按钮
 		}, function(){
 		  layer.msg('你已收款成功', {icon: 1});
 		$("select[name='status']").val("1");
 		$("select[name='payStatus']").val("1");
 		//线下支付
 		//$("select[name='payMethod']").val("1");
 		  $('#btn_sub').click();
 		}, function(){
 		  layer.msg('你已取消收款', {
 		    time: 5000, //5s后自动关闭
 		    btn: ['明白了', '知道了']
 		  });
 		});
 	}
   function changNum1(obj){
		var oldVal=$(obj).siblings().val();
		var newVal=$(obj).val();
		var trObj = $(obj).parent().parent();
		var price = trObj.find('td:eq(6) input').val();//5-6
		var finalPrice = 0;
		var totalPrice = 0;
		if(oldVal>newVal){
			finalPrice =(oldVal-newVal)*parseFloat(price);
			totalPrice = parseFloat($("#totalPrice").val())-parseFloat(finalPrice);
		}else{
			finalPrice =(newVal-oldVal)*parseFloat(price);
			totalPrice = parseFloat($("#totalPrice").val())+parseFloat(finalPrice);
		}
		 $("#totalPrice").val(toDecimal(totalPrice));
		 $("#offlinePrice").val(toDecimal(totalPrice));
		 $(obj).siblings().val(newVal)
	}
 
 
  function changePrice(obj){
	  var foodId = $(obj).val();
	  var trObj = $(obj).parent().parent();
	  var oldPrice =parseFloat( trObj.find('td:eq(6) input').val());//5-6
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
              trObj.find('td:eq(6) input').val( data.obj.price);//5-6
			trObj.find('td:eq(5) input').val(data.obj.unitName);//4-5
              
              if(data.obj.isFloat == 1){
            	  //3-4
            	  trObj.find('td:eq(4) input').attr("datatype","/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/");
              }else{
            	  //3-4
            	  trObj.find('td:eq(4) input').attr("datatype","n");
              }
              var price = parseFloat(data.obj.price);
     	 	 var totalPrice =parseFloat($("#totalPrice").val())+price-oldPrice;
          	  $("#totalPrice").val(toDecimal(totalPrice));
          	  $("#offlinePrice").val(toDecimal(totalPrice));
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
            	   trObj.find('td:eq(6) input').val( data.obj.price);//5-6
            	   trObj.find('td:eq(4) input').val(1);//3-4
              }
          }
      });
	  
	  
  }); 
   
   
   function check(){
	   var paystatus = $("#paystatus").val();
	   if(paystatus == 1){
		   //alert("已支付的订单不能");
		// dg = new $.dialog();
		// dg.close();
			//$(".ui_close").click();
		   return false;  
	   }else{
		   return true;
	   }
	   
   }
   
   function funtaste_btn(){
	   var serialnumber = $("#objhidden").val()*1-1;
	   var obj = $("#add_tcsbOrderItem_table tr:eq("+serialnumber+")");
	   $("#tcsbOrderItemListView").css("z-index",1);
	   $("#funtasteselect").css("z-index",-1);
		  var rs = "";
		  $("#funtastetable :checked").each(function () {
		    	 rs += $(this).val()+",";
		  });
		 if(rs == ''){
			 alert('选项不能为空'); 
		 }else{
			 obj.find('td:eq(7) input').val(rs);//6-7
		 }
   }
   function funtaste_cancle_btn(){
	   $("#tcsbOrderItemListView").css("z-index",1);
		$("#funtasteselect").css("z-index",-1);
   }
   
   
   function foodtypechange(obj){
	   var foodTypeId = $(obj).val();
	   var trObj = $(obj).parent().parent();
	   var url = "tcsbOrderController.do?getfoodByfoodType";
	   $.ajax({
	         url:url,
	         type:"POST",
	         data:{
	        	 typeid:foodTypeId
	         },
	         dataType:"JSON",
	         async: false,
	         success:function(data){
	        	 trObj.find('td:eq(3) select').html("");//3-3
	        	 trObj.find('td:eq(3) select').append("<option value=>------</option>");
	        	 for(var i=0;i<data.length;i++){
	        		 //3-3
	        		 trObj.find('td:eq(3) select').append("<option value="+data[i].id+">"+data[i].name+"</option>");
	        		 //$("#foodId").append("<option value="+data[i].id+">"+data[i].name+"</option>");
	        	 }
	             
	         }
	     });
   } 
 </script>
<script src="webpage/com/tcsb/order/tcsbOrder.js"></script>
<script>
$(function(){
	$('#funtastetable input').labelauty();
});
</script>