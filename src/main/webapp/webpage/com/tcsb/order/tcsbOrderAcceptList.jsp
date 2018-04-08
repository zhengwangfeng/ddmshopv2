<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbOrderList" checkbox="true" fitColumns="false"
			title="订单管理" actionUrl="tcsbOrderController.do?datagridAccept"
			idField="id" fit="true" queryMode="group" sortName="createDate"
			sortOrder="asc">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="订单编号" field="orderNo" query="true" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="下单方式" field="method" replace="扫码下单_0,线上下单_1,手动下单_2"
				query="true" queryMode="single" width="120"></t:dgCol>
			<%--  <t:dgCol title="用户ID"  field="userId"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="所属店铺ID"  field="shopId"   query="true" queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="下单时间" field="createTime" formatter="yyyy-MM-dd"
				query="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="订单状态" field="status" replace="未消费_0,已消费_1"
				query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="实际消费" field="totalPrice" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="优惠价" field="disPrice" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="优惠方式" field="dMethod" replace="平台优惠_0,专用券_1,通用券_2"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="支付状态" field="payStatus" replace="未支付_0,已支付_1"
				query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="支付方式" field="payMethod" replace="线上支付_0,线下支付_1"
				query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="用餐时间" field="eatTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用餐人数" field="people" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="特殊说明" field="note" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否包间" field="isRoom" replace="是_Y,否_N" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<%-- <t:dgDelOpt title="删除" url="tcsbOrderController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/> --%>
			<t:dgConfOpt title="接单"
				url="tcsbOrderController.do?receiveOrder&id={id}"
				urlclass="ace_button" urlfont="fa-toggle-on" message="确认接单" />
			<%-- <t:dgConfOpt title="取消接单" url="cgformFtlController.do?cancleActive&id={id}&formId=${formid}"  urlclass="ace_button"  urlfont="fa-toggle-off"  exp="ftlStatus#eq#1" message="确认取消激活"/> --%>
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbOrderController.do?goAdd" funname="add" width="100%"
				height="100%"></t:dgToolBar>
			<t:dgToolBar title="打印" icon="icon-print"
				url="tcsbOrderController.do?print" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbOrderController.do?goUpdate" funname="update" width="100%"
				height="100%"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbOrderController.do?goUpdate" funname="detail" width="100%"
				height="100%"></t:dgToolBar>

			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/order/tcsbOrderList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbOrderListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='eatTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='eatTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbOrderController.do?upload', "tcsbOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbOrderController.do?exportXls","tcsbOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbOrderController.do?exportXlsByT","tcsbOrderList");
}
 </script>