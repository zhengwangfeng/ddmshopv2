<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbDeskReservationItemList" checkbox="true"
			fitColumns="false" title="tcsb_desk_reservation_item"
			actionUrl="tcsbDeskReservationItemController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="食物ID" field="foodId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="数量" field="count" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="单价" field="price" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属购物车" field="reservationId" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="foodtastefun" field="foodtastefun" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbDeskReservationItemController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbDeskReservationItemController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbDeskReservationItemController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbDeskReservationItemController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbDeskReservationItemController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/tcsbdeskreservationitem/tcsbDeskReservationItemList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbDeskReservationItemListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskReservationItemListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskReservationItemListtb").find("input[name='updateTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskReservationItemListtb").find("input[name='updateTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbDeskReservationItemController.do?upload', "tcsbDeskReservationItemList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbDeskReservationItemController.do?exportXls","tcsbDeskReservationItemList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbDeskReservationItemController.do?exportXlsByT","tcsbDeskReservationItemList");
}
 </script>