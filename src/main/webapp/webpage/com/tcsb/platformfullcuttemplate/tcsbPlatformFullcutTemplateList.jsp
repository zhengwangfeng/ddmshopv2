<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbPlatformFullcutTemplateList" checkbox="true"
			fitColumns="false" title="平台优惠券模板"
			actionUrl="tcsbPlatformFullcutTemplateController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="满额" field="total" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="立减" field="discount" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="权重" field="weight" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="优惠券是否投入使用" field="isUse" replace="是_1,否_0"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否新用户随机领取券" field="isNewuserRandom" replace="是_1,否_0"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="使用期限" field="usePeriod" align="center"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="日期单位" field="dateUnit" align="center"
				replace="日_day,月_month,年_year" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbPlatformFullcutTemplateController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbPlatformFullcutTemplateController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbPlatformFullcutTemplateController.do?goUpdate"
				funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbPlatformFullcutTemplateController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbPlatformFullcutTemplateController.do?goUpdate"
				funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/platformfullcuttemplate/tcsbPlatformFullcutTemplateList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbPlatformFullcutTemplateListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformFullcutTemplateListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformFullcutTemplateListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformFullcutTemplateListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbPlatformFullcutTemplateController.do?upload', "tcsbPlatformFullcutTemplateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbPlatformFullcutTemplateController.do?exportXls","tcsbPlatformFullcutTemplateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbPlatformFullcutTemplateController.do?exportXlsByT","tcsbPlatformFullcutTemplateList");
}
 </script>