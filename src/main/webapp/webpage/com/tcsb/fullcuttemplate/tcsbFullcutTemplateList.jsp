<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbFullcutTemplateList" checkbox="true"
			fitColumns="false" title="满减模版"
			actionUrl="tcsbFullcutTemplateController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopId" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>

			<t:dgCol title="满额" field="total" align="center" queryMode="group"
				width="70"></t:dgCol>
			<t:dgCol title="立减" field="discount" align="center" queryMode="group"
				width="70"></t:dgCol>
			<t:dgCol title="是否分享" field="couponType" replace="否_0,是_1"
				align="center" queryMode="group" width="70"></t:dgCol>
			<%-- <t:dgCol title="分享类型"  field="shareType" replace="固定分享金_1,比例分享金_2" align="center"  hidden="true" queryMode="group"  width="70"></t:dgCol> --%>
			<%-- <t:dgCol title="分享金"  field="shareDescript" align="center"   queryMode="group"  width="70"></t:dgCol> --%>

			<t:dgCol title="使用期限" field="usePeriod" align="center"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="日期单位" field="dateUnit" align="center"
				replace="日_day,月_month,年_year" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" hidden="true" align="center"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbFullcutTemplateController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbFullcutTemplateController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbFullcutTemplateController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbFullcutTemplateController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbFullcutTemplateController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--  <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/fullcuttemplate/tcsbFullcutTemplateList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbFullcutTemplateListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFullcutTemplateListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFullcutTemplateListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFullcutTemplateListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFullcutTemplateController.do?upload', "tcsbFullcutTemplateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFullcutTemplateController.do?exportXls","tcsbFullcutTemplateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFullcutTemplateController.do?exportXlsByT","tcsbFullcutTemplateList");
}
 </script>