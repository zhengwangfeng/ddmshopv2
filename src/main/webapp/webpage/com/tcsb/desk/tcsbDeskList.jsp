<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbDeskList" checkbox="true" fitColumns="false"
			title="桌位管理" actionUrl="tcsbDeskController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="店铺ID" field="shopId" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="桌位编号" field="number" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="二维码" field="qrcode" image="true" imageSize="90,50"></t:dgCol>
			<t:dgCol title="小程序二维码" field="appletQrcode" image="true"
				imageSize="90,50"></t:dgCol>
			<t:dgCol title="是否被预约" align="center" field="isOrder"
				replace="是_1,否_0" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="桌位名称" align="center" field="deskName" query="true"
				width="100"></t:dgCol>
			<t:dgCol title="桌位类别" align="center" field="desktypeId"
				replace="${deskTypeReplace }" query="true" width="100"></t:dgCol>
			<t:dgCol title="更新人名字" align="center" field="updateName"
				hidden="true" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" align="center" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbDeskController.do?doDel&id={id}"
				urlclass="ace_button" operationCode="deskCUD" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbDeskController.do?goAdd" funname="add"
				operationCode="deskCUD"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbDeskController.do?goUpdate" funname="update"
				operationCode="deskCUD"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbDeskController.do?doBatchDel" funname="deleteALLSelect"
				operationCode="deskCUD"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbDeskController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--    <t:dgToolBar operationCode="print" title="打印" icon="icon-print" url="tcsbDeskController.do?print" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/desk/tcsbDeskList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbDeskListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbDeskController.do?upload', "tcsbDeskList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbDeskController.do?exportXls","tcsbDeskList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbDeskController.do?exportXlsByT","tcsbDeskList");
}
 </script>