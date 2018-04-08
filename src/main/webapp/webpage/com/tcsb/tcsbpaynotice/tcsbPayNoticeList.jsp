<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbPayNoticeList" checkbox="true"
			fitColumns="false" title="支付消息通知"
			actionUrl="tcsbPayNoticeController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属桌位" field="deskId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="支付金额" field="payMoney" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否已读" field="isread" queryMode="group"
				dictionary="sf_10" width="120"></t:dgCol>
			<t:dgCol title="支付时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbPayNoticeController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbPayNoticeController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbPayNoticeController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbPayNoticeController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbPayNoticeController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/tcsbpaynotice/tcsbPayNoticeList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbPayNoticeListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPayNoticeListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbPayNoticeController.do?upload', "tcsbPayNoticeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbPayNoticeController.do?exportXls","tcsbPayNoticeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbPayNoticeController.do?exportXlsByT","tcsbPayNoticeList");
}
 </script>