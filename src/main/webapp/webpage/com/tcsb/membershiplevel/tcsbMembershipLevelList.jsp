<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbMembershipLevelList" checkbox="true"
			fitColumns="false" title="会员级别"
			actionUrl="tcsbMembershipLevelController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="会员级别名称" field="name" align="center" queryMode="group"
				width="120"></t:dgCol>

			<%--<t:dgCol title="会员头衔ID"  field="memberTitleId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
			<%--<t:dgCol title="会员头衔"  field="memberTitleName"    queryMode="group"  width="120"></t:dgCol>--%>
			<t:dgCol title="会员级别条件" field="memberLevelConditionsId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会员级别条件" field="memberLevelConditionsName"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会员级别权益id" field="memberLevelEquityId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会员级别权益" field="memberLevelEquityName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopId" queryMode="group" hidden="true"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" queryMode="group"
				align="center" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbMembershipLevelController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbMembershipLevelController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbMembershipLevelController.do?goUpdate" funname="update"></t:dgToolBar>
			<%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbMembershipLevelController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbMembershipLevelController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/membershiplevel/tcsbMembershipLevelList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbMembershipLevelListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMembershipLevelListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMembershipLevelListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMembershipLevelListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbMembershipLevelController.do?upload', "tcsbMembershipLevelList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbMembershipLevelController.do?exportXls","tcsbMembershipLevelList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbMembershipLevelController.do?exportXlsByT","tcsbMembershipLevelList");
}
 </script>