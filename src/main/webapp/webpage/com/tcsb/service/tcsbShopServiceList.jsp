<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<%--   <t:datagrid name="tcsbServiceList"  treegrid="true"  fitColumns="false" title="系统服务" actionUrl="tcsbServiceController.do?datagrid" idField="id" pagination="false">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    --%>
		<t:datagrid name="tcsbServiceList" treegrid="true" fitColumns="false"
			title="口味偏好" actionUrl="tcsbShopServiceController.do?datagrid"
			idField="id" pagination="false">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120" treefield="id"></t:dgCol>

			<t:dgCol title="服务名称" field="name" queryMode="group" width="120"
				treefield="text"></t:dgCol>
			<t:dgCol title="图标" field="logo" image="true" imageSize="90,50"
				treefield="src"></t:dgCol>
			<t:dgCol title="口味排序" field="stateorder" queryMode="group"
				width="120" treefield="order"></t:dgCol>
			<%--    <t:dgCol title="更新人名字"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否显示数量"  field="disPlay"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbServiceController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbServiceController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbServiceController.do?goUpdate" funname="update"></t:dgToolBar>
			<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbServiceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbServiceController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>  --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/service/tcsbServiceList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbServiceListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbServiceListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbServiceListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbServiceListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbServiceController.do?upload', "tcsbServiceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbServiceController.do?exportXls","tcsbServiceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbServiceController.do?exportXlsByT","tcsbServiceList");
}
 </script>