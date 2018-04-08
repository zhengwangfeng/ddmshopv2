<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbMemberLevelConditionsList" checkbox="true"
			fitColumns="false" title="会员级别条件"
			actionUrl="tcsbMemberLevelConditionsController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="冲值金额" field="charge" align="center" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopId" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" align="center"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" align="center"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbMemberLevelConditionsController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbMemberLevelConditionsController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbMemberLevelConditionsController.do?goUpdate"
				funname="update"></t:dgToolBar>
			<%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbMemberLevelConditionsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbMemberLevelConditionsController.do?goUpdate"
				funname="detail"></t:dgToolBar>
			<%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/memberlevelconditions/tcsbMemberLevelConditionsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbMemberLevelConditionsListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelConditionsListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelConditionsListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelConditionsListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbMemberLevelConditionsController.do?upload', "tcsbMemberLevelConditionsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbMemberLevelConditionsController.do?exportXls","tcsbMemberLevelConditionsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbMemberLevelConditionsController.do?exportXlsByT","tcsbMemberLevelConditionsList");
}
 </script>