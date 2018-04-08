<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbFoodTypeList" checkbox="true" fitColumns="false"
			title="食物分类" actionUrl="tcsbFoodTypeController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="分类名称" field="name" align="center" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="分类图标" field="typeImg" align="center" image="true"
				imageSize="90,50"></t:dgCol>
			<t:dgCol title="菜品顺序" field="orders" align="center" query="true"
				width="120"></t:dgCol>
			<c:if test="${isAdmin==true }">
				<t:dgCol title="所属店铺" field="shopId" replace="${shopsReplace }"
					align="center" query="true" width="120"></t:dgCol>
			</c:if>
			<t:dgCol title="是否删除" field="isDel" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbFoodTypeController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" operationCode="hideCud" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbFoodTypeController.do?goAdd" funname="add"
				operationCode="hideCUD"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbFoodTypeController.do?goUpdate" funname="update"
				operationCode="hideCUD"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbFoodTypeController.do?doBatchDel" funname="deleteALLSelect"
				operationCode="hideCUD"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbFoodTypeController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/foodtype/tcsbFoodTypeList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbFoodTypeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTypeListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTypeListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTypeListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodTypeController.do?upload', "tcsbFoodTypeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodTypeController.do?exportXls","tcsbFoodTypeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodTypeController.do?exportXlsByT","tcsbFoodTypeList");
}
 </script>