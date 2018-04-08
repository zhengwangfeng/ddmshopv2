<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbCouponList" checkbox="true" fitColumns="false"
			title="优惠卷" actionUrl="tcsbCouponController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopId" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="指定用户" field="userId" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="满减模版" field="fullcutTemplateId" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="指定用户" field="userName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="满减模版" field="fullcutTemplateIdName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="折扣" field="useRebate" hidden="true" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="使用状态" field="useStatus" query="true" align="center"
				queryMode="single" replace="未使用_0,已使用_1,已过期_2" width="120"></t:dgCol>
			<t:dgCol title="使用时间" field="useTime" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" align="center" field="updateDate" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" align="center" field="updateBy" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" align="center" field="createName"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" align="center" field="createBy" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="生成时间" align="center" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="有效期" field="expiryDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除" url="tcsbCouponController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbCouponController.do?goAdd" funname="add"></t:dgToolBar>
			<%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbCouponController.do?goUpdate" funname="update"></t:dgToolBar> --%>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbCouponController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbCouponController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/coupon/tcsbCouponList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbCouponListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbCouponListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbCouponListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbCouponListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbCouponController.do?upload', "tcsbCouponList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbCouponController.do?exportXls","tcsbCouponList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbCouponController.do?exportXlsByT","tcsbCouponList");
}
 </script>