<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	width: 200px;
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	text-align: center;
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
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
<!-- <div style="margin: 2px;">
	CSS goes in the document HEAD or added to your external stylesheet


Table goes in the document BODY
<table class="gridtable">
<tr>
	<th>待结算金额</th><th>手续费3%</th><th>下次结算时间</th>
</tr>
<tr>
	<td>Text 1A</td><td>Text 1B</td><td>Text 1B</td>
</tr>
</table>
</div> -->
<div class="easyui-layout" fit="true" style="height: 500px;">

	<div region="center" style="padding: 0px; border: 0px;">
		<t:datagrid name="tcsbSettleAccountList" checkbox="false"
			pagination="true" fitColumns="false" title="账单结算"
			actionUrl="tcsbSettleAccountController.do?datedatagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="待结算账单日期" field="waitSettleDatas" query="true"
				align="center" queryMode="group" width="200"></t:dgCol>
			<t:dgCol title="总结算金额" field="alltotal" align="center"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="已结算金额" field="istotal" align="center"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="未结算金额" hidden="true" field="isnottotal"
				align="center" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="未结算金额（扣除3%手续费）" style="background-color: #79FF79;"
				field="needtotal" align="center" queryMode="group" width="160"></t:dgCol>
			<t:dgCol title="盈利" style="background-color: #FFFFAA;" field="profit"
				align="center" queryMode="group" width="160"></t:dgCol>

			<%--     <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
     <t:dgFunOpt funname="myhandle(waitSettleDatas)" title="查看详情" urlclass="addbtnstyleclass"/> --%>
			<%--    <t:dgCol title="待结算金额"  field="settleWay"  query="true" hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="手续费3%"  field="settleWay"   query="true"   hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="下次结算时间"  field="settleWay"   query="true"  hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbSettleAccountController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbSettleAccountController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbSettleAccountController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbSettleAccountController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbSettleAccountController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
			<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>

		<%--    --%>


	</div>

</div>
<%--    <div id="tcsbSettleAccountList" style="padding:1px;border:1px; height:auto">
		<div name="searchColums" style=" height:100px;line-height:100px;overflow:hidden;border:1px">
				<table class="gridtable" style="width: 900px;">
				<tr>
					<th>今日待结算金额</th><th>手续费3%</th><th>实际待结算金额</th><th style="width: 250px;">下次结算时间</th><th>操作</th>
				</tr>
				<tr >
					<td style="background-color: rgba(153,156,158,0.5); font-size: 14px;">${thisWaitAllMoney }</td>
					<td style="background-color: rgba(153,156,158,0.5); font-size: 14px;">${poundage }</td>
					<td style="background-color: rgba(153,156,158,0.5); font-size: 14px;">${waitSettleTotal }</td>
					<td style="background-color: rgba(153,156,158,0.5); font-size: 14px;">${nextSettleData }
					</td>
					<td style="background-color: rgba(153,156,158,0.5); font-size: 14px;">
						<a href="javascript:;" style="margin-left: 10px;" class="delbtnstyleclass" onclick="myhandle('')">查看详情</a>
					</td>
				</tr>
				</table>
		</div>
	</div> --%>
<script src="webpage/com/tcsb/settleaccount/tcsbSettleAccountList.js"></script>
<script type="text/javascript">
 
 function myhandle(settleWeek){  
	    $.dialog({
	        content: 'url:'+"tcsbSettleAccountController.do?income&settleWeek="+settleWeek,  
	        lock : true,  
	        width:600,  
	        height:700,  
	        title:'会员详情',  
	        opacity : 0.3,  
	        cache:false,  
	        cancelVal: '关闭',  
	        cancel: true /*为true等价于function(){}*/  
	    });  
	} 

 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbSettleAccountListtb").find("input[name='waitSettleDatas_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSettleAccountListtb").find("input[name='waitSettleDatas_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbSettleAccountController.do?upload', "tcsbSettleAccountList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbSettleAccountController.do?exportXls","tcsbSettleAccountList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbSettleAccountController.do?exportXlsByT","tcsbSettleAccountList");
}




/* $(document).ready(function(){
	$(".layout-panel-center").css("top",70);
	$(".datagrid-wrap").css("height",700);
}); */



 </script>