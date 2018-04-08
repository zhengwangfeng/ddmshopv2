<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>tcsb_desk_reservation</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbDeskReservationController.do?doUpdate"
		tiptype="1">
		<input id="id" name="id" type="hidden"
			value="${tcsbDeskReservationPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 编号: </label>
				</td>
				<td class="value"><input id="no" name="no" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.no}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">编号</label></td>
				<td align="right"><label class="Validform_label"> 顾客姓名:
				</label></td>
				<td class="value"><input id="nickname" name="nickname"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.nickname}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">顾客姓名</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 性别: </label>
				</td>
				<td class="value"><input id="sex" name="sex" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.sex}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">性别</label></td>
				<td align="right"><label class="Validform_label"> 联系电话:
				</label></td>
				<td class="value"><input id="phone" name="phone" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.phone}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">联系电话</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 预订人数:
				</label></td>
				<td class="value"><input id="num" name="num" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.num}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订人数</label></td>
				<td align="right"><label class="Validform_label">
						预订时断(早,中,晚): </label></td>
				<td class="value"><input id="period" name="period" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.period}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订时断(早,中,晚)</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 预订时间:
				</label></td>
				<td class="value"><input id="orderTime" name="orderTime"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.orderTime}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订时间</label></td>
				<td align="right"><label class="Validform_label">
						预订保留时间: </label></td>
				<td class="value"><input id="retainTime" name="retainTime"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.retainTime}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订保留时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 预约备注:
				</label></td>
				<td class="value"><input id="note" name="note" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.note}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预约备注</label></td>
				<td align="right"><label class="Validform_label"> 是否押金:
				</label></td>
				<td class="value"><input id="isDeposit" name="isDeposit"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.isDeposit}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否押金</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否打印:
				</label></td>
				<td class="value"><input id="isPrint" name="isPrint"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.isPrint}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否打印</label></td>
				<td align="right"><label class="Validform_label"> 所属桌位:
				</label></td>
				<td class="value"><input id="deskId" name="deskId" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.deskId}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">所属桌位</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 预订状态:
				</label></td>
				<td class="value"><input id="status" name="status" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.status}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订状态</label></td>
				<td align="right"><label class="Validform_label"> 预订来源:
				</label></td>
				<td class="value"><input id="source" name="source" type="text"
					style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.source}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">预订来源</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						更新人名字: </label></td>
				<td class="value"><input id="updateName" name="updateName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.updateName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人名字</label></td>
				<td align="right"><label class="Validform_label"> 更新时间:
				</label></td>
				<td class="value"><input id="updateDate" name="updateDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbDeskReservationPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">更新时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 更新人:
				</label></td>
				<td class="value"><input id="updateBy" name="updateBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.updateBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">更新人</label></td>
				<td align="right"><label class="Validform_label">
						创建人名字: </label></td>
				<td class="value"><input id="createName" name="createName"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.createName}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人名字</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 创建人:
				</label></td>
				<td class="value"><input id="createBy" name="createBy"
					type="text" style="width: 150px" class="inputxt"
					value='${tcsbDeskReservationPage.createBy}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">创建人</label></td>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input id="createDate" name="createDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker()"
					value='<fmt:formatDate value='${tcsbDeskReservationPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">创建时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script
	src="webpage/com/tcsb/tcsbdeskreservation/tcsbDeskReservation.js"></script>