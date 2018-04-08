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
	background: #9D9D9D;
	border: 1px #9D9D9D solid;
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
		<t:datagrid name="tcsbPrinterList" checkbox="true" fitColumns="false"
			title="打印机设置" actionUrl="tcsbPrinterController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="打印机索引" field="printIndex" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="打印机名称" field="name" queryMode="group" width="120"></t:dgCol>
			<%-- <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="创建人名字" field="createName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否打印子单" field="printChild" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="是否自动打单" field="autoPrint" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="打印份数" field="printNum" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="250"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbPrinterController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgFunOpt funname="setFoodFunbyPrint(id,name)"
				title="permission.set" urlclass="ace_button" urlfont="fa-cog"></t:dgFunOpt>
			<t:dgFunOpt funname="printChild(id,printChild)" title="直打"
				exp="printChild#eq#否" urlclass="delbtnstyleclass" />
			<t:dgFunOpt funname="printChild(id,printChild)" title="套打"
				exp="printChild#eq#是" urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="autoPrint(id,autoPrint)" title="手动"
				exp="autoPrint#eq#否" urlclass="delbtnstyleclass" />
			<t:dgFunOpt funname="autoPrint(id,autoPrint)" title="自动"
				exp="autoPrint#eq#是" urlclass="addbtnstyleclass" />
			<t:dgToolBar title="初始化打印机" icon="icon-add"
				url="tcsbPrinterController.do?goAdd" funname="add"></t:dgToolBar>
			<%--<t:dgToolBar title="录入" icon="icon-add" url="tcsbPrinterController.do?goAdd" funname="add"></t:dgToolBar>--%>
			<t:dgToolBar title="编辑打印份数" icon="icon-edit"
				url="tcsbPrinterController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbPrinterController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<%--<t:dgToolBar title="查看" icon="icon-search" url="tcsbPrinterController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<div region="east" style="width: 600px;" split="true">
	<div tools="#tt" class="easyui-panel"
		title='<t:mutiLang langKey="permission.set"/>' style="padding: 10px;"
		fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>
<script src="webpage/com/tcsb/printer/tcsbPrinterList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbPrinterListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPrinterListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPrinterListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPrinterListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function setFoodFunbyPrint(id,name) {
		$("#function-panel").panel(
			{
				title :name+ ':' + '<t:mutiLang langKey="current.permission"/>',
				href:"tcsbPrinterController.do?foodFun&printerId=" + id
			}
		);
		//$('#function-panel').panel("refresh" );
	}
 function autoPrint(Id,autoPrint,index){
		var url = "tcsbPrinterController.do?autoPrint";
		$.post(
				url,
				{
					id:Id,
					autoPrint:autoPrint
				},
				function(data){
					$('#tcsbPrinterList').datagrid('reload');	
				}
		);
		
	}
 function printChild(Id,printChild,index){
		var url = "tcsbPrinterController.do?princtChild";
		$.post(
				url,
				{
					id:Id,
					printChild:printChild
				},
				function(data){
					$('#tcsbPrinterList').datagrid('reload');	
				}
		);
		
	}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbPrinterController.do?upload', "tcsbPrinterList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbPrinterController.do?exportXls","tcsbPrinterList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbPrinterController.do?exportXlsByT","tcsbPrinterList");
}
 </script>