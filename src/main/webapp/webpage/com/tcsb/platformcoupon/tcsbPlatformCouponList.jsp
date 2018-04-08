<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbPlatformCouponList" checkbox="true"
			fitColumns="false" title="用户平台优惠券"
			actionUrl="tcsbPlatformCouponController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属用户" field="userId" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="手机" field="mobile" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="使用状态" field="useStatus" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="满减模版" field="fullcutTemplateId" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="使用时间" field="useTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="有效期" field="expiryDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否新用户优惠券" field="isNewuserCouon" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="折扣" field="useRebate" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbPlatformCouponController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbPlatformCouponController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbPlatformCouponController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="tcsbPlatformCouponController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbPlatformCouponController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/platformcoupon/tcsbPlatformCouponList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbPlatformCouponListtb").find("input[name='useTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='useTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='expiryDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='expiryDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbPlatformCouponListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbPlatformCouponController.do?upload', "tcsbPlatformCouponList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbPlatformCouponController.do?exportXls","tcsbPlatformCouponList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbPlatformCouponController.do?exportXlsByT","tcsbPlatformCouponList");
}
 </script>