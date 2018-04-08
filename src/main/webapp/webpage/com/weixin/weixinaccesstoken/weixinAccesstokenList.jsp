<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="weixinAccesstokenList" checkbox="true"
			fitColumns="false" title="微信凭证"
			actionUrl="weixinAccesstokenController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="凭证" field="accessToken" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="凭证有效时间" field="expiresIn" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="weixinAccesstokenController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="weixinAccesstokenController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="weixinAccesstokenController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="weixinAccesstokenController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="weixinAccesstokenController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/weixin/weixinaccesstoken/weixinAccesstokenList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#weixinAccesstokenListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccesstokenListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinAccesstokenController.do?upload', "weixinAccesstokenList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinAccesstokenController.do?exportXls","weixinAccesstokenList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinAccesstokenController.do?exportXlsByT","weixinAccesstokenList");
}
 </script>