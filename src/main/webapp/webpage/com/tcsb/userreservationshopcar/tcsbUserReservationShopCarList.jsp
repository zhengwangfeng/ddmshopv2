<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbUserReservationShopCarList" checkbox="true"
			fitColumns="false" title="预约虚拟购物车"
			actionUrl="tcsbUserReservationShopCarController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属购物车" field="shopcarId" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="用户唯一标识" field="userId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="note" field="note" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="peopleNum" field="peopleNum" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="shopId" field="shopId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="phone" field="phone" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="sex" field="sex" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="nickname" field="nickname" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="eatTime" field="eatTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbUserReservationShopCarController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbUserReservationShopCarController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbUserReservationShopCarController.do?goUpdate"
				funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbUserReservationShopCarController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbUserReservationShopCarController.do?goUpdate"
				funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/userreservationshopcar/tcsbUserReservationShopCarList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbUserReservationShopCarListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbUserReservationShopCarListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbUserReservationShopCarListtb").find("input[name='eatTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbUserReservationShopCarListtb").find("input[name='eatTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbUserReservationShopCarController.do?upload', "tcsbUserReservationShopCarList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbUserReservationShopCarController.do?exportXls","tcsbUserReservationShopCarList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbUserReservationShopCarController.do?exportXlsByT","tcsbUserReservationShopCarList");
}
 </script>