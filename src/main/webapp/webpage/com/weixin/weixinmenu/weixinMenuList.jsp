<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_function_functionList" class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="weixinMenuList" title="微信菜单"
			actionUrl="weixinMenuController.do?weixinMenugrid" treegrid="true"
			idField="id" pagination="false">
			<t:dgCol title="ID" field="id" hidden="true" treefield="id"></t:dgCol>
			<%-- <t:dgCol title=" 菜单标识"  field="menukey"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="消息类型"  field="msgtype"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="名称" field="name" treefield="text" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="顺序" field="orders" treefield="order"
				queryMode="group" width="120"></t:dgCol>
			<%--   <t:dgCol title="消息模版ID "  field="templateid"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="动作设置" field="type" treefield="functionType"
				replace="消息触发类_click,网页链接类_view" width="120"></t:dgCol>
			<t:dgCol title="链接地址" field="url" treefield="src" queryMode="group"
				width="120"></t:dgCol>
			<%--  <t:dgCol title="父ID"  field="fatherid"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="公众号ID   "  field="accountid"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
			<%--   <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="weixinMenuController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="微信菜单录入" icon="icon-add"
				url="weixinMenuController.do?addorupdate" funname="addFun"></t:dgToolBar>
			<t:dgToolBar title="微信菜单编辑" icon="icon-edit"
				url="weixinMenuController.do?addorupdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="菜单同步到微信" icon="icon-edit"
				url="menuManagerController.do?sameMenu" funname="sameMenu()"></t:dgToolBar>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="weixinMenuController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="weixinMenuController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/weixin/weixinmenu/weixinMenuList.js"></script>
<script type="text/javascript">
 function addFun(title,url, id) {
		var rowData = $('#'+id).datagrid('getSelected');
		if (rowData) {
			url += '&WeixinMenuEntity.id='+rowData.id;
		}
		add(title,url,'weixinMenuList',700,480);
	}
 function sameMenu(){
		$.ajax({
			url:"weixinMenuController.do?sameMenu",
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
 			$("#weixinMenuListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinMenuListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinMenuListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinMenuListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinMenuController.do?upload', "weixinMenuList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinMenuController.do?exportXls","weixinMenuList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinMenuController.do?exportXlsByT","weixinMenuList");
}
 </script>