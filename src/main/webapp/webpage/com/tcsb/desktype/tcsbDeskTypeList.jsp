<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbDeskTypeList" checkbox="true" fitColumns="false"
			title="桌位类型" actionUrl="tcsbDeskTypeController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="名称" field="name" align="center" queryMode="single"
				query="true" width="120"></t:dgCol>
			<c:if test="${isAdmin==true }">
				<t:dgCol title="所属店铺" align="center" field="shopId"
					replace="${ shopsReplace}" query="true" width="120"></t:dgCol>
			</c:if>
			<t:dgCol title="座位人数" align="center" field="count" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新人名字" align="center" field="updateName"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" align="center" field="updateDate" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" align="center" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbDeskTypeController.do?doDel&id={id}"
				urlclass="ace_button" operationCode="desktypeCUD"
				urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbDeskTypeController.do?goAdd" funname="add"
				operationCode="desktypeCUD"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbDeskTypeController.do?goUpdate" funname="update"
				operationCode="desktypeCUD"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbDeskTypeController.do?doBatchDel" funname="deleteALLSelect"
				operationCode="desktypeCUD"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbDeskTypeController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/desktype/tcsbDeskTypeList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbDeskTypeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskTypeListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskTypeListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDeskTypeListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbDeskTypeController.do?upload', "tcsbDeskTypeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbDeskTypeController.do?exportXls","tcsbDeskTypeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbDeskTypeController.do?exportXlsByT","tcsbDeskTypeList");
}
 </script>