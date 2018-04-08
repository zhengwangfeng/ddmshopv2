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
	background: #e60012;
	border: 1px #26bbdb solid;
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
		<t:datagrid name="tcsbAssociatorBigdataList" checkbox="true"
			fitColumns="false" title="会员信息统计"
			actionUrl="tcsbAssociatorBigdataController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="会员id" hidden="true" field="userId" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="会员昵称" field="userNickname" query="true" width="120"></t:dgCol>
			<t:dgCol title="总消费次数" field="saleCount" query="true" width="120"></t:dgCol>
			<t:dgCol title="消费总额" field="saleTotal" query="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="平均消费额" field="saleAvgTotal" query="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="最近消费时间" field="lastSaleTime" query="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="手机号" field="userMobile" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所在城市" field="userCity" query="true" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" hidden="true" field="updateName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" hidden="true" field="updateDate"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" hidden="true" field="updateBy" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" hidden="true" field="createName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" hidden="true" field="createBy" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" hidden="true" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="店铺id" hidden="true" field="shopId" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="200"></t:dgCol>
			<t:dgFunOpt funname="myhandle(userId)" title="查看消费详情"
				urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="foodhandle(userId)" title="商品消费分析"
				urlclass="delbtnstyleclass" />
			<t:dgToolBar title="发送优惠券" icon="icon-add"
				url="tcsbCouponController.do?goSendCoupon" funname="updateMultiple"></t:dgToolBar>
			<%-- <t:dgDelOpt title="删除" url="tcsbAssociatorBigdataController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tcsbAssociatorBigdataController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbAssociatorBigdataController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbAssociatorBigdataController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbAssociatorBigdataController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/tcsbassociatorbigdata/tcsbAssociatorBigdataList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbAssociatorBigdataListtb").find("input[name='lastSaleTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorBigdataListtb").find("input[name='lastSaleTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorBigdataListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorBigdataListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorBigdataListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorBigdataListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbAssociatorBigdataController.do?upload', "tcsbAssociatorBigdataList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbAssociatorBigdataController.do?exportXls","tcsbAssociatorBigdataList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbAssociatorBigdataController.do?exportXlsByT","tcsbAssociatorBigdataList");
}


function myhandle(openid){  
	var url = "tcsbAssociatorStatisticsController.do?detaillist&openid="+openid
	createwindow("消费详情", url,480,500);
   /*  $.dialog({
        content: 'url:'+"tcsbAssociatorStatisticsController.do?detaillist&openid="+openid,  
        lock : false,  
        width:460,  
        height:600, 
        zIndex: getzIndex(),
        title:'会员详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true 为true等价于function(){}
    }); */ 
} 

function foodhandle(openid){  
    $.dialog({
        content: 'url:'+"tcsbAssociatorStatisticsController.do?userfooddetaillist&openid="+openid,  
        lock : false,  
        width:550,  
        height:600,  
        title:'会员详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true /*为true等价于function(){}*/  
    });  
} 

 </script>