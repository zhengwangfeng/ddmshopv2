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
		<t:datagrid name="tcsbSaleStatisticsList" checkbox="true"
			fitColumns="false" title="商品销量统计"
			actionUrl="tcsbSaleStatisticsController.do?datagridnew" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="商品ID" align="center" field="foodId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="商品名称" align="center" field="foodName" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="总销量" align="center" field="allSaleCount"
				sortable="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="上架时间" align="center" field="onSaleTime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会员复购数" align="center" field="vipSaleCount"
				hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgFunOpt funname="myhandle(foodId)" title="查看详情"
				urlclass="addbtnstyleclass" />
			<%-- <t:dgToolBar title="发送营销短信" icon="icon-add" url="tcsbSaleStatisticsController.do?goAdd" funname="sendSaleSMS"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbSaleStatisticsController.do?goUpdate" funname="myhandle"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbSaleStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbSaleStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/salestatistics/tcsbSaleStatisticsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			//$("#tcsbSaleStatisticsListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbSaleStatisticsListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbSaleStatisticsController.do?upload', "tcsbSaleStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXls","tcsbSaleStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXlsByT","tcsbSaleStatisticsList");
}



/**
 * 自定义处理事件
 * @param url
 * @param title
 * @param okVal
 */
function myhandle(foodId){  
//	alert(foodId);
    $.dialog({
        content: 'url:'+"tcsbSaleStatisticsController.do?newdetaillist&foodid="+foodId,  
        lock : false,  
        width:650,  
        height:700,  
        title:'销售详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true /*为true等价于function(){}*/  
    });  
} 

 </script>