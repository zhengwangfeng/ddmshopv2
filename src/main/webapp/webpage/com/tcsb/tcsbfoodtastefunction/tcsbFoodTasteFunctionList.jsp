<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="food_taste_function_functionList" class="easyui-layout"
	fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbFoodTasteFunctionList" treegrid="true"
			fitColumns="false" title="口味偏好"
			actionUrl="tcsbFoodTasteFunctionController.do?foodtastegrid"
			idField="id" pagination="false">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120" treefield="id"></t:dgCol>
			<%-- <t:dgCol title="菜单等级"  field="functionlevel"    queryMode="group"  width="120" treefield="functionlevel"></t:dgCol> --%>
			<t:dgCol title="口味名称" field="statename" queryMode="group" width="120"
				treefield="text"></t:dgCol>
			<t:dgCol title="口味排序" field="stateorder" queryMode="group"
				width="120" treefield="order"></t:dgCol>
			<%--  <t:dgCol title="parentid"  field="parentfunctionid"    queryMode="group"  width="120" treefield="stateorder" ></t:dgCol> --%>
			<%--    <t:dgCol title="创建人id"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人id"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateName"    queryMode="group"  width="120"></t:dgCol> --%>
			<%-- <t:dgCol title="shopid"  field="shopid"    queryMode="group"  width="120" ></t:dgCol> --%>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbFoodTasteFunctionController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbFoodTasteFunctionController.do?addorupdate"
				funname="addFun"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbFoodTasteFunctionController.do?addorupdate"
				funname="update"></t:dgToolBar>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbFoodTasteFunctionController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbFoodTasteFunctionController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunctionList.js"></script>
<script type="text/javascript">
 
 function addFun(title,url, id) {
		var rowData = $('#'+id).datagrid('getSelected');
		if (rowData) {
			url += '&TcsbFoodTasteFunctionEntity.id='+rowData.id;
		}
		add(title,url,'tcsbFoodTasteFunctionList',700,480);
	}
 
 function sameMenu(){
		$.ajax({
			url:"tcsbFoodTasteFunctionController.do?sameMenu",
			type:"GET",
			dataType:"JSON",
			success:function(data){
				if(data.success){
					tip(data.msg);
				}
			}
		});
	}
 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbFoodTasteFunctionListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTasteFunctionListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTasteFunctionListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodTasteFunctionListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodTasteFunctionController.do?upload', "tcsbFoodTasteFunctionList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodTasteFunctionController.do?exportXls","tcsbFoodTasteFunctionList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodTasteFunctionController.do?exportXlsByT","tcsbFoodTasteFunctionList");
}
 </script>