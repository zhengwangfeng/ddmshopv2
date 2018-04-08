<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbDiscountActivityList" checkbox="true"
			fitColumns="false" title="优惠活动"
			actionUrl="tcsbDiscountActivityController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="满减模版" align="center" field="fullcutTemplateId"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属店铺" align="center" field="shopName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="满减模版" align="center" field="fullcutTemplateIdName"
				queryMode="group" width="120"></t:dgCol>
			<%--  <t:dgCol title="折扣"  field="useRebate"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="有效期起"  field="expiryDateBegin" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="有效期止"  field="expiryDateEnd" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="创建时间" align="center" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbDiscountActivityController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbDiscountActivityController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbDiscountActivityController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbDiscountActivityController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbDiscountActivityController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/discountactivity/tcsbDiscountActivityList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbDiscountActivityListtb").find("input[name='expiryDateBegin_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='expiryDateBegin_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='expiryDateEnd_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='expiryDateEnd_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDiscountActivityListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbDiscountActivityController.do?upload', "tcsbDiscountActivityList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbDiscountActivityController.do?exportXls","tcsbDiscountActivityList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbDiscountActivityController.do?exportXlsByT","tcsbDiscountActivityList");
}
 </script>