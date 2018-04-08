<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户子订单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbSubOrderController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbSubOrderPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 订单编号:
				</label></td>
				<td class="value"><input id="orderNo" name="orderNo"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">订单编号</label></td>
				<td align="right"><label class="Validform_label"> 下单方式:
				</label></td>
				<td class="value"><input id="method" name="method" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">下单方式</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						所属店铺ID: </label></td>
				<td class="value"><input id="shopId" name="shopId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属店铺ID</label></td>
				<td align="right"><label class="Validform_label">
						deskId: </label></td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">deskId</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 下单时间:
				</label></td>
				<td class="value"><input id="createTime" name="createTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">下单时间</label>
				</td>
				<td align="right"><label class="Validform_label"> 订单状态:
				</label></td>
				<td class="value"><input id="status" name="status" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">订单状态</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 实际消费:
				</label></td>
				<td class="value"><input id="totalPrice" name="totalPrice"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">实际消费</label></td>
				<td align="right"><label class="Validform_label"> 线上价格:
				</label></td>
				<td class="value"><input id="onlinePrice" name="onlinePrice"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">线上价格</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 线下价格:
				</label></td>
				<td class="value"><input id="offlinePrice" name="offlinePrice"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">线下价格</label></td>
				<td align="right"><label class="Validform_label"> 支付状态:
				</label></td>
				<td class="value"><input id="payStatus" name="payStatus"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">支付状态</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 支付方式:
				</label></td>
				<td class="value"><input id="payMethod" name="payMethod"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">支付方式</label></td>
				<td align="right"><label class="Validform_label"> 用餐时间:
				</label></td>
				<td class="value"><input id="eatTime" name="eatTime"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">用餐时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 用餐人数:
				</label></td>
				<td class="value"><input id="people" name="people" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">用餐人数</label></td>
				<td align="right"><label class="Validform_label"> 特殊说明:
				</label></td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">特殊说明</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否包间:
				</label></td>
				<td class="value"><input id="isRoom" name="isRoom" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否包间</label></td>
				<td align="right"><label class="Validform_label"> 口味偏好:
				</label></td>
				<td class="value"><input id="taste" name="taste" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">口味偏好</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						平台优惠价: </label></td>
				<td class="value"><input id="platformDiscountPrice"
					name="platformDiscountPrice" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">平台优惠价</label>
				</td>
				<td align="right"><label class="Validform_label">
						专用券优惠价: </label></td>
				<td class="value"><input id="specialCouponPrice"
					name="specialCouponPrice" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">专用券优惠价</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						通用券优惠价: </label></td>
				<td class="value"><input id="universalCouponPrice"
					name="universalCouponPrice" type="text" style="width: 150px"
					class="inputxt"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">通用券优惠价</label>
				</td>
				<td align="right"><label class="Validform_label">
						是否已接单: </label></td>
				<td class="value"><input id="orderIstake" name="orderIstake"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否已接单</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">更新时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">创建时间</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属用户:
				</label></td>
				<td class="value"><input id="userId" name="userId" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属用户</label></td>
				<td align="right"><label class="Validform_label"> </label></td>
				<td class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/suborder/tcsbSubOrder.js"></script>