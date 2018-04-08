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
		<t:datagrid name="tcsbAssociatorStatisticsList" checkbox="true"
			fitColumns="false" title="会员统计已消费"
			actionUrl="tcsbAssociatorStatisticsController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="openid" field="openid" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="输入关键字查询" field="querykey" query="true" hidden="true"
				width="120"></t:dgCol>
			<t:dgCol title="会员昵称" align="center" field="nickname" width="120"></t:dgCol>
			<t:dgCol title="消费次数" align="center" field="saleCount"
				sortable="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="均消费金额" align="center" field="avgSaleMoney"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="上次消费时间" align="center" field="lastTime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="手机号" align="center" field="mobile" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所在城市" align="center" field="city" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="200" align="center"></t:dgCol>
			<t:dgFunOpt funname="myhandle(openid)" title="查看消费详情"
				urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="foodhandle(openid)" title="商品消费分析"
				urlclass="delbtnstyleclass" />
			<t:dgToolBar title="发送优惠券" icon="icon-add"
				url="tcsbCouponController.do?goSendCoupon" funname="updateMultiple"></t:dgToolBar>
			<%--  <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbAssociatorStatisticsController.do?goUpdate" funname="myhandle"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbAssociatorStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbAssociatorStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/tcsb/associatorstatistics/tcsbAssociatorStatisticsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbAssociatorStatisticsListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorStatisticsListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbAssociatorStatisticsController.do?upload', "tcsbAssociatorStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbAssociatorStatisticsController.do?exportXls","tcsbAssociatorStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbAssociatorStatisticsController.do?exportXlsByT","tcsbAssociatorStatisticsList");
}



/**
 * 自定义处理事件
 * @param url
 * @param title
 * @param okVal
 */
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