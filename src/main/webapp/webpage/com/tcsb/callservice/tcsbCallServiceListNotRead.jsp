<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbCallServiceList" checkbox="true"
			fitColumns="false" title="呼叫服务"
			actionUrl="tcsbCallServiceController.do?datagridNotRead" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<%-- <t:dgCol title="所属店铺"  field="shopId"    queryMode="group"  width="160"></t:dgCol> --%>
			<t:dgCol title="所属桌位" field="deskId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="选择的服务" field="serviceName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="数量" field="count" queryMode="group" width="120"></t:dgCol>
			<%--    <t:dgCol title="是否已读"  field="isread"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否播放"  field="isplay"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="用户ID"  field="userId"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="呼叫时间" field="createTime"
				formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="160"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="myhandle(id)" title="设置为已读"
				urlclass="addbtnstyleclass" />
			<%--  <t:dgToolBar title="录入" icon="icon-add" url="tcsbCallServiceController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbCallServiceController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbCallServiceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbCallServiceController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/callservice/tcsbCallServiceList.js"></script>
<script type="text/javascript">
 
 
 /**
  * 自定义处理事件
  * @param url
  * @param title
  * @param okVal
  */
 function myhandle(id){  
	 var url = "tcsbCallServiceController.do?isread";
		$.post(
					url,
					{ID:id},
					function(data){
						 $('#tcsbCallServiceList').datagrid('reload');
					}
		);
 } 
 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbCallServiceListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbCallServiceListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbCallServiceController.do?upload', "tcsbCallServiceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbCallServiceController.do?exportXls","tcsbCallServiceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbCallServiceController.do?exportXlsByT","tcsbCallServiceList");
}
 </script>