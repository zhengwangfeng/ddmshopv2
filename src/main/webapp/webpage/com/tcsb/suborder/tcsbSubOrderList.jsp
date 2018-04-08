<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbSubOrderList" checkbox="true" fitColumns="false"
			title="用户子订单" actionUrl="tcsbSubOrderController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="订单编号" field="orderNo" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="下单方式" field="method" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="所属店铺ID" field="shopId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="deskId" field="deskId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="下单时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="订单状态" field="status" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="实际消费" field="totalPrice" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="线上价格" field="onlinePrice" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="线下价格" field="offlinePrice" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="支付状态" field="payStatus" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="支付方式" field="payMethod" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用餐时间" field="eatTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用餐人数" field="people" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="特殊说明" field="note" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否包间" field="isRoom" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="口味偏好" field="taste" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="平台优惠价" field="platformDiscountPrice"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="专用券优惠价" field="specialCouponPrice" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="通用券优惠价" field="universalCouponPrice"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否已接单" field="orderIstake" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属用户" field="userId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbSubOrderController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbSubOrderController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbSubOrderController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbSubOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbSubOrderController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/suborder/tcsbSubOrderList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbSubOrderListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='eatTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='eatTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSubOrderListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbSubOrderController.do?upload', "tcsbSubOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbSubOrderController.do?exportXls","tcsbSubOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbSubOrderController.do?exportXlsByT","tcsbSubOrderList");
}
 </script>