<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbShopEvaluateList" checkbox="true"
			fitColumns="false" title="店铺评价"
			actionUrl="tcsbShopEvaluateController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopName"
				queryMode="group" width="130"></t:dgCol>
			<t:dgCol title="内容" align="center" field="content" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="用户ID" align="center" field="userId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户名" align="center" field="userName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="菜品评价" align="center" field="dishesEvaluation"
				replace="非常差_0,差_1,一般_2,满意_3,好_4,非常好_5" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="就餐环境" align="center" field="diningEnvironment"
				replace="非常差_0,差_1,一般_2,满意_3,好_4,非常好_5" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="评价时间" align="center" field="createTime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="图片" align="center" field="img" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbShopEvaluateController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tcsbShopEvaluateController.do?goAdd" funname="add"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbShopEvaluateController.do?goUpdate" funname="update"></t:dgToolBar> --%>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbShopEvaluateController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbShopEvaluateController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/shopevaluate/tcsbShopEvaluateList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopEvaluateListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopEvaluateListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopEvaluateController.do?upload', "tcsbShopEvaluateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopEvaluateController.do?exportXls","tcsbShopEvaluateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopEvaluateController.do?exportXlsByT","tcsbShopEvaluateList");
}
 </script>