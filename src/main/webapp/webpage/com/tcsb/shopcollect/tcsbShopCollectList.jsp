<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbShopCollectList" checkbox="true"
			fitColumns="false" title="店铺收藏"
			actionUrl="tcsbShopCollectController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="130"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="收藏者" align="center" field="userId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="收藏者" align="center" field="userName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="收藏时间" align="center" field="createTime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbShopCollectController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<%--   <t:dgToolBar title="录入" icon="icon-add" url="tcsbShopCollectController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbShopCollectController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopCollectController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbShopCollectController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/shopcollect/tcsbShopCollectList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopCollectListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopCollectListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopCollectController.do?upload', "tcsbShopCollectList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopCollectController.do?exportXls","tcsbShopCollectList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopCollectController.do?exportXlsByT","tcsbShopCollectList");
}
 </script>