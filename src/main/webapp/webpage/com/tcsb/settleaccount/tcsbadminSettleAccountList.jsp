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
	margin-left: 10px;
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
<div class="easyui-layout" fit="true">

	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbSettleAccountList" checkbox="true"
			fitColumns="false" title="账单结算"
			actionUrl="tcsbSettleAccountController.do?admindatagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="店铺名称" align="center" field="shopName" width="180"></t:dgCol>
			<t:dgCol title="店铺ID" align="center" hidden="true" field="shopId"
				queryMode="group" width="220"></t:dgCol>
			<t:dgCol title="待结算订单日期" align="center" field="waitSettleData"
				formatter="yyyy-MM-dd" queryMode="group" width="140"></t:dgCol>
			<t:dgCol title="结算日期" align="center" field="settleData"
				formatter="yyyy-MM-dd" query="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="待结算金额" align="center" field="total" width="80"></t:dgCol>
			<t:dgCol title="实际结算金额" align="center" field="actualSettleTotal"
				width="80"></t:dgCol>
			<t:dgCol title="是否已结算" align="center" field="isSettle" query="true"
				defaultVal="N" dictionary="sf_yn" width="80"></t:dgCol>
			<t:dgCol title="备注" align="center" field="settleWay"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="80"></t:dgCol>
			<t:dgFunOpt funname="myhandle(id)" title="结算"
				urlclass="delbtnstyleclass" />
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
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<%--  <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>

		<%--     <div id="tcsbSettleAccountList" style="padding:1px;border:1px; height:auto">
		<div name="searchColums" style=" height:70px;line-height:70px;overflow:hidden;border:1px">
				<table class="gridtable">
				<tr>
					<th>待结算金额</th><th>手续费3%</th><th>实际待结算金额</th><th>下次结算时间</th>
				</tr>
				<tr>
					<td>${thisWaitAllMoney }</td><td>${poundage }</td><td>${waitSettleTotal }</td><td>${nextSettleData }<a href="javascript:;" style="margin-left: 10px;color: blue;cursor: pointer;" onclick="myhandle('${taileTime}')">查看详情</a></td>
				</tr>
				</table>
		</div>
	</div> --%>


	</div>
</div>
<script src="webpage/com/tcsb/settleaccount/tcsbSettleAccountList.js"></script>
<script type="text/javascript">
 
 function myhandle(id){  
	    $.dialog({
	        content: 'url:'+"tcsbSettleAccountController.do?adminupdatesettle&id="+id,  
	        lock : true,  
	        width:600,  
	        height:200,  
	        zIndex: getzIndex(),
	        title:'账单结算备注',  
	        opacity : 0.3,  
	        cache:false,  
	        okVal: '确定结算',  
	        ok: function(){  
	            iframe = this.iframe.contentWindow;  
	            saveObj();  
	            return false;  
	        },  
	        cancelVal: '关闭',  
	        cancel: true /*为true等价于function(){}*/  
	    });  
	} 

 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbSettleAccountListtb").find("input[name='settleData_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbSettleAccountListtb").find("input[name='settleData_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
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
 </script>