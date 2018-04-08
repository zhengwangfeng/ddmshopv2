<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
.addbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #4ba8f7;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}

.delbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #e60012;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}
</style>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbOrderParentList" checkbox="true"
			fitColumns="false" title="订单父级关联"
			actionUrl="tcsbOrderParentController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="店铺ID" field="shopId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="桌位ID" field="deskId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="订单状态1正在使用0已清台" field="orderStatus" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="已支付" field="payMoney" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="未支付" field="notPayMoney" queryMode="group"
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
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="orderDetail(id)" title="查看详情"
				urlclass="addbtnstyleclass" />
			<%-- <t:dgDelOpt title="删除" url="tcsbOrderParentController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
			<%--  <t:dgToolBar title="录入" icon="icon-add" url="tcsbOrderParentController.do?goAdd" funname="add"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbOrderParentController.do?goUpdate" funname="update"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbOrderParentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="查看" icon="icon-search" url="tcsbOrderParentController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/tcsborderparent/tcsbOrderParentList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbOrderParentListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderParentListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderParentListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderParentListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function orderDetail(id){
	   var url = "tcsbOrderController.do?orderList&orderParentId="+id;
       createdetailwindow("总订单详情", url, 1000, 700);
 }
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbOrderParentController.do?upload', "tcsbOrderParentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbOrderParentController.do?exportXls","tcsbOrderParentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbOrderParentController.do?exportXlsByT","tcsbOrderParentList");
}
 </script>